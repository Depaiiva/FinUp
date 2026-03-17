package br.com.finup.auth.service;

import java.time.LocalDateTime;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.finup.auth.dto.LoginRequest;
import br.com.finup.auth.dto.LoginResponse;
import br.com.finup.auth.dto.SignupRequest;
import br.com.finup.user.User;
import br.com.finup.user.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final PasswordEncoder encoder;
  private final CustomUserDetailService customUserDetailService;

  public void signup(SignupRequest request) {

    if (userRepository.existsByEmail(request.email())) {
      throw new RuntimeException("Email já cadastrado");
    }

    User user = new User();
    user.setName(request.name());
    user.setEmail(request.email());
    user.setPassword(encoder.encode(request.password()));
    user.setCreatedAt(LocalDateTime.now());
    userRepository.save(user);
  }

  public LoginResponse login(LoginRequest request) {
    UserDetails userDetails = customUserDetailService.loadUserByUsername(request.email());

    String token = jwtService.generateToken(userDetails);

    return new LoginResponse(userDetails.getUsername(), userDetails.getPassword(), token);
  }
}
