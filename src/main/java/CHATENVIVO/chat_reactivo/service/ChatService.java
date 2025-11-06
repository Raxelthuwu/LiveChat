package CHATENVIVO.chat_reactivo.service;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;

import CHATENVIVO.chat_reactivo.model.ChatMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ChatService {

    private final DatabaseClient db;

    public ChatService(DatabaseClient db) {
        this.db = db;
    }

    public Flux<ChatMessage> getAllMessages() {
        return db.sql("SELECT * FROM message.message ORDER BY timestamp ASC")
                 .map((row, meta) -> {
                     ChatMessage msg = new ChatMessage();
                     msg.setId(row.get("id_message", Integer.class));
                     msg.setSenderId(row.get("sender", Integer.class));
                     msg.setAddresseeId(row.get("addressee", Integer.class));
                     msg.setContent(row.get("message", String.class));
                     msg.setTimestamp(row.get("timestamp", java.time.LocalDateTime.class));
                     return msg;
                 })
                 .all();
    }

    public Mono<ChatMessage> saveMessage(ChatMessage message) {
        return db.sql("INSERT INTO message.message (sender, addressee, message, timestamp) VALUES (:sender, :addressee, :message, :timestamp) RETURNING id_message")
                 .bind("sender", message.getSenderId())
                 .bind("addressee", message.getAddresseeId())
                 .bind("message", message.getContent())
                 .bind("timestamp", message.getTimestamp())
                 .map((row, meta) -> {
                     message.setId(row.get("id_message", Integer.class));
                     return message;
                 })
                 .one();
    }
}
