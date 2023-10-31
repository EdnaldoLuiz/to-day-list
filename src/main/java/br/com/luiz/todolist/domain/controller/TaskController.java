package br.com.luiz.todolist.domain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.luiz.todolist.domain.dto.task.TaskRequestData;
import br.com.luiz.todolist.domain.dto.task.TaskResponseData;
import br.com.luiz.todolist.domain.dto.task.TaskUpdateData;
import br.com.luiz.todolist.domain.model.TaskModel;
import br.com.luiz.todolist.domain.repository.ITaskRepository;
import br.com.luiz.todolist.infra.exception.TaskNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/create")
    public ResponseEntity<TaskResponseData> create(@RequestBody @Valid TaskRequestData task) {
        var taskModel = new TaskModel(task);
        var createdTask = taskRepository.save(taskModel);
        return ResponseEntity.ok(new TaskResponseData(createdTask));
    }

    @GetMapping("/list/{login}")
    public ResponseEntity<List<TaskModel>> list(@PathVariable String login) {
        var tasks = taskRepository.findByUserLogin(login);
        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<TaskResponseData> update(@RequestBody @Valid TaskUpdateData task) {
        var taskModel = taskRepository.getReferenceById(task.id());
        taskModel.updateTask(task);
        return ResponseEntity.ok(new TaskResponseData(taskModel));
    }

    @DeleteMapping("/{taskId}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long taskId) {
        TaskModel task = taskRepository.findById(taskId)
        .orElseThrow(() -> new TaskNotFoundException("Task não encontrada"));
        taskRepository.delete(task);
        return ResponseEntity.noContent().build();
    }
}