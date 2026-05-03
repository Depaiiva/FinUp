package br.com.finup.auth;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.finup.user.User;
import br.com.finup.user.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    return new org.springframework.security.core.userdetails.User(
        user.getEmail(),
        user.getPassword(),
        List.of(new SimpleGrantedAuthority("ROLE_USER")));
  }
}
