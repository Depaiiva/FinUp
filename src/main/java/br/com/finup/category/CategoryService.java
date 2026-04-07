package br.com.finup.category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import br.com.finup.category.dto.RequestCategory;
import br.com.finup.category.dto.RequestId;
import br.com.finup.category.dto.ResponseCategory;
import br.com.finup.user.User;
import br.com.finup.user.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;
  private final UserRepository userRepository;

  public ResponseCategory create(RequestCategory request, UserDetails userDetails) {
    User user = loadUser(userDetails);

    Category category = Category.builder()
        .name(request.name())
        .description(request.description())
        .user(user.getId())
        .build();

    categoryRepository.save(category);

    return new ResponseCategory(
        category.getName(),
        category.getDescription(),
        category.getId(),
        category.getUser());
  }

  public List<ResponseCategory> listAll(UserDetails userDetails) {
    User user = loadUser(userDetails);

    List<Category> list = categoryRepository.findAllByUser(user.getId());

    List<ResponseCategory> listCategory = list.stream()
        .map(category -> new ResponseCategory(
            category.getName(),
            category.getDescription(),
            category.getId(),
            category.getUser()))
        .collect(Collectors.toList());
    return listCategory;
  }

  public String delete(UserDetails userDetails, RequestId request) throws RuntimeException {
    User user = loadUser(userDetails);

    if (!categoryRepository.existsByIdAndUser(UUID.fromString(request.id()), user.getId())) {
      throw new RuntimeException("Category not found");
    }

    categoryRepository.deleteById(UUID.fromString(request.id()));
    return "Category deleted sucessfully";
  }

  public ResponseCategory update(UserDetails userDetails, RequestCategory request, String id) {
    Category category = categoryRepository.findById(UUID.fromString(id))
        .orElseThrow(() -> new RuntimeException("Category not found"));

    if (request.name() != null)
      category.setName(request.name());
    if (request.description() != null)
      category.setDescription(request.description());

    categoryRepository.save(category);

    return new ResponseCategory(
        category.getName(),
        category.getDescription(),
        category.getId(),
        category.getUser());
  }

  private User loadUser(UserDetails userDetails) throws RuntimeException {

    Optional<User> user = userRepository.findByEmail(userDetails.getUsername());

    if (user.isEmpty()) {
      throw new RuntimeException("User not found");
    }

    return user.get();
  }
}
