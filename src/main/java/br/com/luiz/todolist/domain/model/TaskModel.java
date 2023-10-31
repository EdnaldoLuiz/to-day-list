package br.com.luiz.todolist.domain.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.luiz.todolist.domain.dto.task.TaskRequestData;
import br.com.luiz.todolist.domain.dto.task.TaskUpdateData;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "tb_tasks")
@NoArgsConstructor
public class TaskModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 50)
  private String title;
  private String description;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
  private LocalTime startAt;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
  private LocalTime endAt;
  private Priority priority;

  @Column(name = "user_login")
  private String userLogin;

  @CreationTimestamp
  private LocalDateTime createdAt;

  public TaskModel(TaskRequestData request) {
    this.description = request.description();
    this.title = request.title();
    this.startAt = request.startAt();
    this.endAt = request.endAt();
    this.priority = request.priority();
    this.userLogin = request.userLogin();
  }

  public void updateTask(@Valid TaskUpdateData data) {
    if (data.title() != null) {
      this.title = data.title();
    }
    if (data.description() != null) {
      this.description = data.description();
    }
    if (data.startAt() != null) {
      this.startAt = data.startAt();
    }
    if (data.endAt() != null) {
      this.endAt = data.endAt();
    }
    if (data.priority() != null) {
      this.priority = data.priority();
    }
  }
}
