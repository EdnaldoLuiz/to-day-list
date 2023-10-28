package br.com.luiz.todolist.domain.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.luiz.todolist.domain.dto.task.TaskRequestData;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
  private LocalDateTime startAt;
  private LocalDateTime endAt;
  private Priority priority;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "user_login", insertable = false, updatable = false)
  private UserModel user;

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
}
