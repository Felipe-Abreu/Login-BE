package com.felipe_abreu.login.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.felipe_abreu.login.model.UserModel;
import com.felipe_abreu.login.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;


    public UserController(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<UserModel>> listarTodos() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PostMapping("/register")
    public ResponseEntity<UserModel> salvar(@RequestBody UserModel user) {
        user.setPassword(encoder.encode(user.getPassword()));

        return ResponseEntity.ok(userRepository.save(user));
    }

    @GetMapping("/validarSenha")
    public ResponseEntity<Boolean> validarSenha(@JsonFormat @RequestParam String login,
                                                @RequestParam String password) {

        UserModel user = userRepository.findByLogin(login);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

        boolean valid = encoder.matches(password, user.getPassword());

        HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(valid);
    }

}
