package br.com.luiz.todolist.domain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.luiz.todolist.domain.dto.user.UserRegister;
import br.com.luiz.todolist.infra.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> create(@RequestBody UserRegister user) {
        var registeredUser = userService.register(user);
        return registeredUser;
    }

}
