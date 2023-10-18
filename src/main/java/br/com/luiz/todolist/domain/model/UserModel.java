package br.com.luiz.todolist.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import br.com.luiz.todolist.domain.dto.user.UserRegister;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "tb_users")
@NoArgsConstructor
public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID     id;
        
    @Column(unique = true)
    private String email;

    private String username;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public UserModel(UserRegister register) {
        this.email = register.email();
        this.username = register.username();
        this.password = register.password();
    }
}
