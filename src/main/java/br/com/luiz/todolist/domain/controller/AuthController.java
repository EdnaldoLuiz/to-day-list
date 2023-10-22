package br.com.luiz.todolist.domain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.luiz.todolist.domain.dto.token.TokenData;
import br.com.luiz.todolist.domain.dto.user.UserLogin;
import br.com.luiz.todolist.domain.model.UserModel;
import br.com.luiz.todolist.infra.service.TokenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<TokenData> login(@RequestBody @Valid UserLogin login) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(login.login(), login.password());
        var authentication = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.gerarToken((UserModel) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenData(tokenJWT));
    }

}
