package CHATENVIVO.chat_reactivo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "message.user")
public class User {
    
    @Id
    @Column("id_user")
    private Integer id;
    
    @Column("full_name")
    private String fullName;
    
    public User() {}

    public User(String fullName) { this.fullName = fullName; }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
}