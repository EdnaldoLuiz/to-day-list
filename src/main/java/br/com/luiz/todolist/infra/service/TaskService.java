 package br.com.luiz.todolist.infra.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.luiz.todolist.domain.dto.task.TaskRequestData;
import br.com.luiz.todolist.domain.model.TaskModel;
import br.com.luiz.todolist.domain.repository.ITaskRepository;

@Service
public class TaskService {

    @Autowired
    private ITaskRepository taskRepository;

    public ResponseEntity<?> createTask(TaskRequestData task) {

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

}