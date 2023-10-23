package br.com.luiz.todolist.domain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.luiz.todolist.domain.dto.task.TaskRequestData;
import br.com.luiz.todolist.domain.model.TaskModel;
import br.com.luiz.todolist.domain.repository.ITaskRepository;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/create")
    public ResponseEntity<TaskModel> create(@RequestBody TaskRequestData task) {
        TaskModel taskModel = new TaskModel(task);
        var createdTask = taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(createdTask);
    }

    @GetMapping("/get/{login}")
    public ResponseEntity<List<TaskModel>> list(@PathVariable String login) {
        var tasks = taskRepository.findByUserLogin(login);
        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

    @DeleteMapping("/{taskId}")
    public void delete(@PathVariable Long taskId) {
        TaskModel task = taskRepository.findById(taskId)
        .orElseThrow(() -> new RuntimeException("Task não encontrada"));
        taskRepository.delete(task);
    }
}