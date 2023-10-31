package br.com.luiz.todolist.domain.dto.task;

import java.time.LocalTime;

import br.com.luiz.todolist.domain.model.Priority;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskRequestData(

    @NotBlank
    String title,

    String description,

    @Future
    LocalTime startAt,

    @Future
    LocalTime endAt,

    @NotNull
    Priority priority,

    @NotBlank
    String userLogin) {}
