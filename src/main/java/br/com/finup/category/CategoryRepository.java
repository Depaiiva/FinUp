package br.com.finup.category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

  List<Category> findAllByUser(UUID user);

  boolean existsByIdAndUser(UUID id, UUID user);

  Optional<Category> findByNameAndUser(String name, UUID user);
}
