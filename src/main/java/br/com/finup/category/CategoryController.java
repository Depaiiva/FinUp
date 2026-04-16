package br.com.finup.category;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.finup.category.dto.RequestCategory;
import br.com.finup.category.dto.ResponseCategory;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("category")
@AllArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @PostMapping
  public ResponseEntity<ResponseCategory> createTransaction(@RequestBody RequestCategory request,
      @AuthenticationPrincipal UserDetails userDetails) {
    ResponseCategory response = categoryService.create(request, userDetails);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping
  public ResponseEntity<List<ResponseCategory>> listAllCategory(@AuthenticationPrincipal UserDetails userDetails) {
    List<ResponseCategory> response = categoryService.listAll(userDetails);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(
      @AuthenticationPrincipal UserDetails userDetails,
      @PathVariable String id) {
    String msg = categoryService.delete(userDetails, id);
    return ResponseEntity.ok(msg);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ResponseCategory> update(@AuthenticationPrincipal UserDetails userDetails,
      @RequestBody RequestCategory request, @PathVariable String id) {
    ResponseCategory response = categoryService.update(userDetails, request, id);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/search")
  public ResponseEntity<ResponseCategory> listbyName(
      @AuthenticationPrincipal UserDetails userDetails,
      @RequestParam(name = "name", required = true) String name) {
    ResponseCategory response = categoryService.findByName(userDetails, name);
    return ResponseEntity.ok(response);
  }

}
