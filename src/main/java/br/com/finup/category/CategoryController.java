package br.com.finup.category;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.finup.category.dto.RequestCategory;
import br.com.finup.category.dto.ResponseCategory;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("category")
@AllArgsConstructor
public class CategoryController {

  private final CategoryService categorytegoryService;

  @PostMapping
  public ResponseEntity<ResponseCategory> createTransaction(@RequestBody RequestCategory request,
      @AuthenticationPrincipal UserDetails userDetails) {
    ResponseCategory response = categorytegoryService.create(request, userDetails);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping
  public ResponseEntity<List<ResponseCategory>> listAllCategory(@AuthenticationPrincipal UserDetails userDetails) {
    List<ResponseCategory> response = categorytegoryService.listAll(userDetails);
    return ResponseEntity.ok(response);
  }
}
