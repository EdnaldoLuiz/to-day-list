package br.com.luiz.todolist.domain.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserRegister(

    @NotBlank
    String login,

    @NotBlank
    String password) {}
