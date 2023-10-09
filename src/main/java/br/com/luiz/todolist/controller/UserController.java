package br.com.luiz.todolist.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.luiz.todolist.model.UserModel;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping
    public void create(@RequestBody UserModel user) {
        
    }
    
}
