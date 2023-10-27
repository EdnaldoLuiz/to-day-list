package br.com.luiz.todolist.domain.dto.task;

import java.time.LocalDateTime;

import br.com.luiz.todolist.domain.model.Priority;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskRequestData(

    @NotBlank
    String title,

    String description,

    @Future
    LocalDateTime startAt,

    @Future
    LocalDateTime endAt,

    @NotNull
    Priority priority,

    @NotBlank
    String userLogin) {}
