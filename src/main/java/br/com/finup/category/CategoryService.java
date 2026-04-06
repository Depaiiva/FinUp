package br.com.finup.category;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import br.com.finup.category.dto.RequestCategory;
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
        category.getUser());
  }

  public List<ResponseCategory> listAll(UserDetails userDetails) {
    User user = loadUser(userDetails);

    List<Category> list = categoryRepository.findAllByUser(user.getId());

    List<ResponseCategory> listCategory = list.stream()
        .map(category -> new ResponseCategory(
            category.getName(),
            category.getDescription(),
            category.getUser()))
        .collect(Collectors.toList());

    return listCategory;
  }

  private User loadUser(UserDetails userDetails) throws RuntimeException {

    Optional<User> user = userRepository.findByEmail(userDetails.getUsername());

    if (user.isEmpty()) {
      throw new RuntimeException("User not found");
    }

    return user.get();
  }
}
