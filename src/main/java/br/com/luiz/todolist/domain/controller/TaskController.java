package br.com.luiz.todolist.domain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.luiz.todolist.domain.dto.task.TaskRequestData;
import br.com.luiz.todolist.domain.model.TaskModel;
import br.com.luiz.todolist.infra.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody TaskRequestData task) {
        return taskService.createTask(task);
    }

    @GetMapping("/get")
    public List<TaskModel> list() {
        return taskService.getAllTasks();
    }
}