package br.com.luiz.todolist.infra.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.luiz.todolist.domain.model.UserModel;
import br.com.luiz.todolist.domain.repository.IUserRepository;
import br.com.luiz.todolist.domain.dto.user.UserRegister;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    public ResponseEntity<?> register(UserRegister userRegister) {
       

        var passwordHashed = BCrypt.withDefaults().hashToString(12, userRegister.password().toCharArray());

        UserModel userModel = new UserModel();
        userModel.setEmail(userRegister.email());
        userModel.setUsername(userRegister.username());
        userModel.setPassword(passwordHashed.toString());

        var userCreated = userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
