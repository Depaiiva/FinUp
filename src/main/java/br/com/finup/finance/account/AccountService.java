package br.com.finup.finance.account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.finup.finance.account.dto.CreateAccount;
import br.com.finup.finance.account.dto.ResponseAccount;
import br.com.finup.user.User;
import br.com.finup.user.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountService {

  private final AccountRepository accountRepository;
  private final UserRepository userRepository;

  public ResponseAccount create(CreateAccount request, UserDetails userDetails)
      throws UsernameNotFoundException {

    User user = loadUser(userDetails);

    Account account = new Account();
    account.setName(request.name());
    account.setType(request.type());
    account.setUser(user);
    account.setBalance(BigDecimal.ZERO);
    accountRepository.save(account);

    return new ResponseAccount("Account created successfully.",
        account.getName(),
        account.getBalance(),
        account.getType(),
        account.getId());
  }

  public String delete(String id) {
    accountRepository.deleteById(UUID.fromString(id));
    return "Account deleted successfully";
  }

  public List<ResponseAccount> toListAccount() {

    List<Account> list = accountRepository.findAll();
    List<ResponseAccount> listAccount = list.stream().map(u -> new ResponseAccount(
        "list",
        u.getName(),
        u.getBalance(),
        u.getType(),
        u.getId())).collect(Collectors.toList());
    return listAccount;
  }

  private User loadUser(UserDetails userDetails) throws UsernameNotFoundException {

    Optional<User> user = userRepository.findByEmail(userDetails.getUsername());

    if (user.isEmpty()) {
      throw new UsernameNotFoundException("User not found");
    }

    return user.get();
  }
}
