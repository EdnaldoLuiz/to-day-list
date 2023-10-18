package br.com.luiz.todolist.domain.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserRegister(

    @NotBlank
    String username,

    @NotBlank
    String email,

    @NotBlank
    String password) {}
