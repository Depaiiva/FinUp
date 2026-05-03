package br.com.finup.core;

import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.finup.exceptions.AccountNotFound;
import br.com.finup.exceptions.CategoryNotFound;
import br.com.finup.exceptions.UserNotFound;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(UserNotFound.class)
  private ResponseEntity<RestErroMessage> userNotFound(UserNotFound exception) {
    RestErroMessage erroResponse = new RestErroMessage(exception.getMessage(), HttpStatus.NOT_FOUND,
        exception.getDetail());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroResponse);
  }

  @ExceptionHandler(AccountNotFound.class)
  private ResponseEntity<RestErroMessage> accountNotFound(AccountNotFound exception) {
    RestErroMessage erroResponse = new RestErroMessage(exception.getMessage(), HttpStatus.NOT_FOUND,
        exception.getDetail());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroResponse);
  }

  @ExceptionHandler(CategoryNotFound.class)
  private ResponseEntity<RestErroMessage> categoryNotFound(CategoryNotFound exception) {
    RestErroMessage erroResponse = new RestErroMessage(exception.getMessage(), HttpStatus.NOT_FOUND,
        exception.getDetail());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroResponse);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  private ResponseEntity<RestErroMessage> handleTypeMismatch(MethodArgumentTypeMismatchException exception) {
    String parameterName = exception.getName(); // nome do parâmetro (ex: "id")
    String requiredType = exception.getRequiredType() != null ? exception.getRequiredType().getSimpleName() : "unknown";

    String errorMessage = String.format(
        "Parameter '%s' has invalid format. Expected type: %s, but received: %s",
        parameterName,
        requiredType,
        exception.getValue());

    RestErroMessage erroResponse = new RestErroMessage(
        errorMessage,
        HttpStatus.BAD_REQUEST,
        "Please check the ID format. It should be a valid " + requiredType);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResponse);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {

    String errorMessage = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(error -> error.getDefaultMessage())
        .collect(Collectors.joining(", "));

    RestErroMessage erroResponse = new RestErroMessage(
        errorMessage,
        HttpStatus.BAD_REQUEST,
        null);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResponse);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      Exception ex,
      Object body,
      HttpHeaders headers,
      HttpStatusCode statusCode,
      WebRequest request) {

    String userMessage;
    String detail = null;

    // Trata especificamente o caso de recurso não encontrado (DELETE, PUT, GET sem
    // ID)
    if (ex.getClass().getSimpleName().equals("NoResourceFoundException")) {
      // Extrai a URL da requisição
      String uri = request.getDescription(false).replace("uri=", "");

      userMessage = "Invalid request format";

      // Personaliza a mensagem baseada no método HTTP (se disponível)
      if (uri.contains("/category/") && uri.endsWith("/")) {
        userMessage = "Missing category ID in URL";
        detail = "The correct format is: DELETE /category/{id}";
      } else {
        userMessage = "Resource not found";
        detail = "Please check the URL format";
      }

      RestErroMessage erroResponse = new RestErroMessage(
          userMessage,
          HttpStatus.BAD_REQUEST, // 400 em vez
          detail);

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResponse);
    }

    // Para outras exceções, mantém o comportamento padrão
    System.err.println("=== EXCEÇÃO NÃO TRATADA ESPECIFICAMENTE ===");
    System.err.println("Tipo: " + ex.getClass().getName());
    System.err.println("Mensagem: " + ex.getMessage());
    System.err.println("Status: " + statusCode);
    ex.printStackTrace();

    RestErroMessage erroResponse = new RestErroMessage(
        ex.getMessage(),
        HttpStatus.valueOf(statusCode.value()),
        request.getDescription(false));

    return ResponseEntity.status(statusCode).headers(headers).body(erroResponse);
  }
}
