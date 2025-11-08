package CHATENVIVO.chat_reactivo.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import CHATENVIVO.chat_reactivo.model.Message;
import reactor.core.publisher.Mono;

@Repository
public interface MessageRepository extends R2dbcRepository<Message, Integer> {

    @Query("""
        INSERT INTO message.message (sender, addressee, message)
        VALUES (:senderId, :addresseeId, :message)
        RETURNING sender, addressee, message
    """)
    Mono<Message> insertMessage(Integer senderId, Integer addresseeId, String message);
}
