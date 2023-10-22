package br.com.luiz.todolist.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.luiz.todolist.domain.model.TaskModel;

public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {
  List<TaskModel> findByIdUser(UUID idUser);

  TaskModel deleteByTitle(String title);
}
