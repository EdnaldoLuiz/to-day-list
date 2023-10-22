package br.com.luiz.todolist.domain.dto.task;

import java.time.LocalDateTime;

import br.com.luiz.todolist.domain.model.Priority;

public record TaskRequest(

    String title,
    String description,
    LocalDateTime startAt,
    LocalDateTime endAt,
    Priority priority
) {
    
}
