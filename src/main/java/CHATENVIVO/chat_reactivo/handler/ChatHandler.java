package CHATENVIVO.chat_reactivo.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import CHATENVIVO.chat_reactivo.model.ChatMessage;
import CHATENVIVO.chat_reactivo.service.ChatService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Component
public class ChatHandler implements WebSocketHandler {

    private static final String SERIALIZATION_ERROR = "{\"error\":\"serialization error\"}";

    private final ChatService chatService;
    private final ObjectMapper mapper;
    private final Sinks.Many<ChatMessage> messageSink = Sinks.many().replay().all();

    public ChatHandler(ChatService chatService) {
        this.chatService = chatService;
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
    }

    public Mono<ServerResponse> getMessages(ServerRequest request) {
        return ServerResponse.ok().body(chatService.getAllMessages(), ChatMessage.class);
    }

    public Mono<ServerResponse> saveMessage(ServerRequest request) {
        return request.bodyToMono(ChatMessage.class)
                      .flatMap(chatService::saveMessage)
                      .doOnNext(this.messageSink::tryEmitNext)
                      .flatMap(saved -> ServerResponse.status(201).bodyValue(saved));
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        Flux<WebSocketMessage> outgoing = messageSink.asFlux()
                                                     .map(this::messageToJson)
                                                     .map(session::textMessage);

        session.receive()
               .map(WebSocketMessage::getPayloadAsText)
               .flatMap(this::jsonToMessage)
               .flatMap(chatService::saveMessage)
               .doOnNext(msg -> this.messageSink.tryEmitNext(msg))
               .doOnError(err -> System.err.println("Error WS: " + err.getMessage()))
               .onErrorContinue((err, obj) -> {})
               .subscribe();

        return session.send(outgoing);
    }

    private String messageToJson(ChatMessage message) {
        try { return mapper.writeValueAsString(message); }
        catch (JsonProcessingException e) { return SERIALIZATION_ERROR; }
    }

    private Mono<ChatMessage> jsonToMessage(String json) {
        try { return Mono.just(mapper.readValue(json, ChatMessage.class)); }
        catch (JsonProcessingException e) { return Mono.empty(); }
    }
}
