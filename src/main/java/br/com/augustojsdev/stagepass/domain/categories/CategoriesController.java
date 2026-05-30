package br.com.augustojsdev.stagepass.domain.categories;

import br.com.augustojsdev.stagepass.domain.categories.dto.CategoryDTO;
import br.com.augustojsdev.stagepass.domain.categories.entities.Category;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoriesController {

    private final CategoriesService categoriesService;

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody @Valid CategoryDTO categoryDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.categoriesService.create(categoryDTO));
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity.ok(this.categoriesService.findAll());
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<Category> updateById(@PathVariable UUID categoryId,
                                               @RequestBody @Valid CategoryDTO categoryDTO) {
        return ResponseEntity.ok(this.categoriesService.update(categoryId, categoryDTO));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Category> updateById(@PathVariable UUID categoryId) {
        this.categoriesService.delete(categoryId);

        return ResponseEntity.noContent().build();
    }
}
