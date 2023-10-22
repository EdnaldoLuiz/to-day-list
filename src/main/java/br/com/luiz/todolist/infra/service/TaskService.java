package br.com.luiz.todolist.infra.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.luiz.todolist.domain.dto.task.TaskRequest;
import br.com.luiz.todolist.domain.model.TaskModel;
import br.com.luiz.todolist.domain.repository.ITaskRepository;
import br.com.luiz.todolist.infra.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class TaskService {

    @Autowired
    private ITaskRepository taskRepository;

    public ResponseEntity<?> createTask(TaskRequest task) {

        var currentDate = LocalDateTime.now();

        if (currentDate.isAfter(task.startAt()) || currentDate.isAfter(task.endAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de início/data de término deve ser maior do que a data atual");
        }

        if (task.startAt().isAfter(task.endAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de início deve ser menor que a data de término");
        }

        TaskModel taskModel = new TaskModel(task);
        var createdTask = this.taskRepository.save(taskModel);

        return ResponseEntity.status(HttpStatus.OK).body(createdTask);
    }

    public List<TaskModel> getAllTasks() {
        return taskRepository.findAll();
    }

    public ResponseEntity<?> updateTask(TaskModel taskModel, HttpServletRequest request, UUID id) {
        var task = this.taskRepository.findById(id).orElse(null);

        if (task == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa não encontrada");
        }

        var idUser = request.getAttribute("idUser");

        if (!task.getIdUser().equals(idUser)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Usuário não tem permissão para alterar essa tarefa");
        }

        Utils.copyNonNullProperties(taskModel, task);
        var taskUpdated = this.taskRepository.save(task);
        return ResponseEntity.ok().body(taskUpdated);
    }   

    public ResponseEntity<?> deleteTask(String title) {
        var task = taskRepository.deleteByTitle(title);
        return ResponseEntity.ok().body(task);
    }
}
