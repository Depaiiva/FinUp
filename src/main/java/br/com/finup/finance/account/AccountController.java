package br.com.finup.finance.account;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.finup.finance.account.dto.CreateAccount;
import br.com.finup.finance.account.dto.RequestAccount;
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
  public ResponseEntity<List<ResponseAccount>> toList(@AuthenticationPrincipal UserDetails userDetails) {
    List<ResponseAccount> list = accountService.toListAccount(userDetails);
    return ResponseEntity.ok(list);
  }

  @PutMapping
  public ResponseEntity<ResponseAccount> updateAccount(@RequestBody RequestAccount request,
      @RequestParam(name = "id", required = true) String id, @AuthenticationPrincipal UserDetails userDetails) {
    ResponseAccount accountUpdate = accountService.update(request, id, userDetails);
    return ResponseEntity.ok(accountUpdate);

  }

  @DeleteMapping()
  public ResponseEntity<String> deleteAccount(@RequestParam(name = "id", required = true) String id,
      @AuthenticationPrincipal UserDetails userDetails) {
    String msg = accountService.delete(id, userDetails);
    return ResponseEntity.status(HttpStatus.OK).body(msg);
  }
}
