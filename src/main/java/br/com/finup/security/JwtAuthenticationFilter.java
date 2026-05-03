package br.com.finup.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.finup.auth.JwtService;
import br.com.finup.core.RestErroMessage;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String header = request.getHeader("Authorization");

    if (header == null || !header.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      String token = header.substring(7);

      String username = jwtService.extractUsername(token);

      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (jwtService.isTokenValid(token, userDetails)) {
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
              userDetails,
              null,
              userDetails.getAuthorities());
          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      }

      filterChain.doFilter(request, response);
    } catch (ExpiredJwtException e) {
      handleExpiredJwtException(response, e);
    } catch (Exception e) {
      handleJwtException(response, e);
    }

  }

  private void handleExpiredJwtException(HttpServletResponse response, ExpiredJwtException e)
      throws IOException {

    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType("application/json");

    RestErroMessage erroResponse = new RestErroMessage(
        "Session Expired",
        HttpStatus.UNAUTHORIZED,
        "Your session has expired. Please log in again.");

    ObjectMapper mapper = new ObjectMapper();
    response.getWriter().write(mapper.writeValueAsString(erroResponse));
  }

  private void handleJwtException(HttpServletResponse response, Exception e)
      throws IOException {

    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType("application/json");

    RestErroMessage erroResponse = new RestErroMessage(
        "Invalid Token",
        HttpStatus.UNAUTHORIZED,
        "Invalid or malformed token");

    ObjectMapper mapper = new ObjectMapper();
    response.getWriter().write(mapper.writeValueAsString(erroResponse));
  }

}
