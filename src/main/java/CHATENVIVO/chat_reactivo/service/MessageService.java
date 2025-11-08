package CHATENVIVO.chat_reactivo.service;

import org.springframework.stereotype.Service;

import CHATENVIVO.chat_reactivo.model.Message;
import CHATENVIVO.chat_reactivo.repository.MessageRepository;
import reactor.core.publisher.Mono;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    public Mono<Message> insertMessage(Integer senderId, Integer addresseeId, String message) {
        return messageRepository.insertMessage(senderId, addresseeId, message);
    }
}
