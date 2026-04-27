package br.com.finup.category;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

  List<Category> findAllByUser(UUID user);

  boolean existsByIdAndUser(UUID id, UUID user);

  @Query("SELECT c FROM Category c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')) AND c.user = :userId")
  List<Category> findByNameAndUser(@Param("name") String name, @Param("userId") UUID user);
}
