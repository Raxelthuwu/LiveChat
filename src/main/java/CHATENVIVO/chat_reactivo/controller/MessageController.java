package CHATENVIVO.chat_reactivo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CHATENVIVO.chat_reactivo.model.Message;
import CHATENVIVO.chat_reactivo.service.MessageService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/message")
@CrossOrigin(origins = "*")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public Mono<Message> sendMessage(@RequestBody Message message) {
        return messageService.insertMessage(
            message.getSender(),
            message.getAddressee(),
            message.getMessage()
        ).doOnNext(savedMessage -> {
            WebSocketController.broadcastSink.tryEmitNext(savedMessage);
        });
    }
}
