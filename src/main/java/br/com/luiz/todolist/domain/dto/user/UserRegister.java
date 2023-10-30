package br.com.luiz.todolist.domain.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegister(

    @NotBlank
    @Email
    String login,

    @NotBlank
    @Size(min = 8)
    String password,
    
    @NotBlank
    @Size(min = 3)
    String username) {}
