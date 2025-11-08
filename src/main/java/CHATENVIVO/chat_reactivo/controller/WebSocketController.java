package CHATENVIVO.chat_reactivo.controller;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import CHATENVIVO.chat_reactivo.model.Message;
import CHATENVIVO.chat_reactivo.service.MessageService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Controller
public class WebSocketController implements WebSocketHandler {

    private final MessageService messageService;
    private final ObjectMapper mapper = new ObjectMapper();

    // este estático -> para que MessageController también pueda emitir
    public static Sinks.Many<Message> broadcastSink;

    private final Set<WebSocketSession> connectedSessions = ConcurrentHashMap.newKeySet();

    public WebSocketController(MessageService messageService) {
        this.messageService = messageService;
        broadcastSink = Sinks.many().multicast().onBackpressureBuffer();
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        connectedSessions.add(session);

        Flux<WebSocketMessage> outgoing = broadcastSink.asFlux()
                .map(this::toJson)
                .map(session::textMessage);

        Mono<Void> incoming = session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .flatMap(this::fromJson)
                .flatMap(msg ->
                        messageService.insertMessage(msg.getSender(), msg.getAddressee(), msg.getMessage())
                )
                .doOnNext(savedMessage -> {
                    broadcastSink.tryEmitNext(savedMessage);
                })
                .then();

        return session.send(outgoing)
                .and(incoming)
                .doFinally(signal -> connectedSessions.remove(session));
    }

    private String toJson(Message msg) {
        try { return mapper.writeValueAsString(msg); }
        catch (Exception e) { return "{\"error\":\"serialization error\"}"; }
    }

    private Mono<Message> fromJson(String json) {
        try { return Mono.just(mapper.readValue(json, Message.class)); }
        catch (Exception e) { return Mono.empty(); }
    }
}
