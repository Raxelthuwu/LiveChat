package CHATENVIVO.chat_reactivo.model;

public class Message {

    private Integer sender;
    private Integer addressee;
    private String message;

    public Message() {}

    public Message(Integer sender, Integer addressee, String message) {
        this.sender = sender;
        this.addressee = addressee;
        this.message = message;
    }

    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    public Integer getAddressee() {
        return addressee;
    }

    public void setAddressee(Integer addressee) {
        this.addressee = addressee;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
