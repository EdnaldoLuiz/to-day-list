package br.com.luiz.todolist.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import br.com.luiz.todolist.domain.dto.task.TaskRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "tb_tasks")
@NoArgsConstructor
public class TaskModel {

  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;
  private String description;

  @Column(length = 50)
  private String title;
  private LocalDateTime startAt;
  private LocalDateTime endAt;
  private Priority priority;

  private UUID idUser;

  @CreationTimestamp
  private LocalDateTime createdAt;

  public TaskModel(TaskRequest request) {
    this.description = request.description();
    this.title = request.title();
    this.startAt = request.startAt();
    this.endAt = request.endAt();
    this.priority = request.priority();
  }
}
