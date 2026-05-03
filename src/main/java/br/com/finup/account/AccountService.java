package br.com.finup.account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.finup.core.exceptions.AccountNotFound;
import br.com.finup.core.exceptions.UserNotFound;
import br.com.finup.account.dto.CreateAccount;
import br.com.finup.account.dto.RequestAccount;
import br.com.finup.account.dto.ResponseAccount;
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

    return new ResponseAccount(
        account.getName(),
        account.getBalance(),
        account.getType(),
        account.getId());
  }

  public String delete(String id, UserDetails userDetails) {
    User user = loadUser(userDetails);
    accountRepository.deleteByIdAndUser(UUID.fromString(id), user);
    return "Account deleted successfully";
  }

  public List<ResponseAccount> toListAccount(UserDetails userDetails) {
    User user = loadUser(userDetails);

    List<Account> list = accountRepository.findAllByUser(user);
    List<ResponseAccount> listAccount = list.stream().map(u -> new ResponseAccount(
        u.getName(),
        u.getBalance(),
        u.getType(),
        u.getId())).collect(Collectors.toList());
    return listAccount;
  }

  public ResponseAccount update(RequestAccount request, String id, UserDetails userDetails) throws RuntimeException {
    User user = loadUser(userDetails);
    Optional<Account> account = accountRepository.findByIdAndUser(UUID.fromString(id), user);

    if (account.isEmpty()) {
      throw new AccountNotFound();
    }

    Account accountFind = account.get();
    accountFind.setName(request.name());
    accountFind.setType(request.type());
    accountFind.setBalance(request.balance());

    accountRepository.save(accountFind);

    return new ResponseAccount(
        accountFind.getName(),
        accountFind.getBalance(),
        accountFind.getType(),
        accountFind.getId());
  }

  private User loadUser(UserDetails userDetails) throws UsernameNotFoundException {

    Optional<User> user = userRepository.findByEmail(userDetails.getUsername());

    if (user.isEmpty()) {
      throw new UserNotFound();
    }

    return user.get();
  }
}
