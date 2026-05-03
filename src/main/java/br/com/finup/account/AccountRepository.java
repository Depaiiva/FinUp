package br.com.finup.account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.finup.user.User;

public interface AccountRepository extends JpaRepository<Account, UUID> {

  List<Account> findAllByUser(User user);

  Optional<Account> findByIdAndUser(UUID id, User user);

  void deleteByIdAndUser(UUID id, User user);
}
