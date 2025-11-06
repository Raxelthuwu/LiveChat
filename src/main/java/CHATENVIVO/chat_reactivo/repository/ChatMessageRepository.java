package CHATENVIVO.chat_reactivo.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import CHATENVIVO.chat_reactivo.model.ChatMessage;

@Repository

public interface ChatMessageRepository extends ReactiveCrudRepository<ChatMessage, Integer> {}