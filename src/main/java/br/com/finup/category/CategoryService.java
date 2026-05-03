package br.com.finup.category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import br.com.finup.category.dto.RequestCategory;
import br.com.finup.category.dto.ResponseCategory;
import br.com.finup.exceptions.CategoryNotFound;
import br.com.finup.exceptions.UserNotFound;
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

  public String delete(UserDetails userDetails, String request) throws UserNotFound {
    User user = loadUser(userDetails);

    if (!categoryRepository.existsByIdAndUser(UUID.fromString(request), user.getId()))
      throw new UserNotFound();

    categoryRepository.deleteById(UUID.fromString(request));
    return "Category deleted sucessfully";
  }

  public ResponseCategory update(UserDetails userDetails, RequestCategory request, String id) {

    Category category = categoryRepository.findById(UUID.fromString(id))
        .orElseThrow(() -> new CategoryNotFound());

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

  public List<ResponseCategory> findByName(UserDetails userDetails, String name) throws RuntimeException {
    User user = loadUser(userDetails);

    List<Category> resultSearch = categoryRepository.findByNameAndUser(name, user.getId());

    if (resultSearch.isEmpty())
      throw new CategoryNotFound();

    return resultSearch.stream().map(category -> new ResponseCategory(
        category.getName(),
        category.getDescription(),
        category.getId(),
        category.getUser())).collect(Collectors.toList());
  }

  private User loadUser(UserDetails userDetails) throws UserNotFound {

    Optional<User> user = userRepository.findByEmail(userDetails.getUsername());

    if (user.isEmpty()) {
      throw new UserNotFound();
    }

    return user.get();
  }
}
