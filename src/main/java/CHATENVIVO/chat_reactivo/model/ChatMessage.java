package CHATENVIVO.chat_reactivo.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Table("message.message")
public class ChatMessage {

    @Id
    @Column("id_message")
    private Integer id;

    @Column("sender")
    private Integer senderId;

    @Column("addressee")
    private Integer addresseeId;

    @Column("message")
    private String content;

    @Column("timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    private String senderName;

    public ChatMessage() {
        this.timestamp = LocalDateTime.now();
    }

    public ChatMessage(Integer senderId, String content) {
        this.senderId = senderId;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getSenderId() { return senderId; }
    public void setSenderId(Integer senderId) { this.senderId = senderId; }
    public Integer getAddresseeId() { return addresseeId; }
    public void setAddresseeId(Integer addresseeId) { this.addresseeId = addresseeId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public String getSenderName() { return senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }

    @Override
    public String toString() {
        return "ChatMessage{id=" + id + ", senderId=" + senderId + ", content='" + content + "'}";
    }
}
