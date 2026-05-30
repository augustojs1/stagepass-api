package br.com.augustojsdev.stagepass.domain.categories;

import br.com.augustojsdev.stagepass.domain.categories.dto.CategoryDTO;
import br.com.augustojsdev.stagepass.domain.categories.dto.CategoryResponseDTO;
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
    public ResponseEntity<CategoryResponseDTO> create(@RequestBody @Valid CategoryDTO categoryDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.categoriesService.create(categoryDTO));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAll() {
        return ResponseEntity.ok(this.categoriesService.findAll());
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> updateById(@PathVariable UUID categoryId,
                                               @RequestBody @Valid CategoryDTO categoryDTO) {
        return ResponseEntity.ok(this.categoriesService.update(categoryId, categoryDTO));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> updateById(@PathVariable UUID categoryId) {
        this.categoriesService.delete(categoryId);

        return ResponseEntity.noContent().build();
    }
}
