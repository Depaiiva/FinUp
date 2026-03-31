package br.com.finup.finance.account;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.finup.finance.account.dto.CreateAccount;
import br.com.finup.finance.account.dto.ResponseAccount;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("account")
@AllArgsConstructor
public class AccountController {

  private final AccountService accountService;

  @PostMapping
  public ResponseEntity<ResponseAccount> createAccount(@RequestBody CreateAccount request,
      @AuthenticationPrincipal UserDetails userDetails) {
    ResponseAccount response = accountService.create(request, userDetails);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping
  public ResponseEntity<List<ResponseAccount>> toList() {
    List<ResponseAccount> list = accountService.toListAccount();
    return ResponseEntity.ok(list);
  }

  @DeleteMapping()
  public ResponseEntity<String> deleteAccount(@RequestParam(name = "id", required = true) String id) {
    String msg = accountService.delete(id);
    return ResponseEntity.status(HttpStatus.OK).body(msg);
  }
}
