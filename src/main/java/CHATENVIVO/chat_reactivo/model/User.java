package CHATENVIVO.chat_reactivo.model;

public class User {

    private Integer idUser;
    private String fullName;

    public User() {}

    public User(Integer idUser, String fullName) {
        this.idUser = idUser;
        this.fullName = fullName;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
