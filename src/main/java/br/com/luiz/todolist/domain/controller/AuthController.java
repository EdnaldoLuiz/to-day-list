package br.com.luiz.todolist.domain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.luiz.todolist.domain.dto.token.TokenData;
import br.com.luiz.todolist.domain.dto.user.UserLogin;
import br.com.luiz.todolist.domain.dto.user.UserRegister;
import br.com.luiz.todolist.domain.model.UserModel;
import br.com.luiz.todolist.domain.repository.IUserRepository;
import br.com.luiz.todolist.infra.service.TokenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<UserModel> register(@RequestBody @Valid UserRegister registerData) {
        var user = new UserModel(registerData);
        user.setPassword(passwordEncoder.encode(registerData.password()));
        userRepository.save(user);

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(uri).body(user);
    }

    @Transactional
    @PostMapping("/login")
    public ResponseEntity<TokenData> login(@RequestBody @Valid UserLogin loginData) {
        var user = (UserModel) userRepository.findByLogin(loginData.login());
        if (user != null && passwordEncoder.matches(loginData.password(), user.getPassword())) {
            var tokenJWT = tokenService.gerarToken(user);
            return ResponseEntity.ok(new TokenData(tokenJWT));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
