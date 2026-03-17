package br.com.finup.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.finup.auth.dto.LoginRequest;
import br.com.finup.auth.dto.LoginResponse;
import br.com.finup.auth.dto.SignupRequest;
import br.com.finup.auth.service.AuthService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthController {

  private final AuthService service;

  @PostMapping("signup")
  @ResponseStatus(HttpStatus.CREATED)
  public void signup(@RequestBody SignupRequest request) {
    service.signup(request);
  }

  @PostMapping("login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
    LoginResponse response = service.login(request);

    return ResponseEntity.ok(response);
  }
}
