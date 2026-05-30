package br.com.augustojsdev.stagepass.domain.categories;

import br.com.augustojsdev.stagepass.domain.categories.dto.CategoryDTO;
import br.com.augustojsdev.stagepass.domain.categories.entities.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;

    public Category create(CategoryDTO categoryDTO) {
        var existentCategory = this.categoriesRepository.findByName(categoryDTO.getName());

        if (existentCategory.isPresent()) {
            log.error("Category with name {} already exists!", categoryDTO.getName());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Resource with this name already exists!");
        }

        Category newCategory = Category.builder()
                .name(categoryDTO.getName())
                .build();

        Category category = this.categoriesRepository.save(newCategory);

        log.info("Successfully created new category:: {}", category);

        return  category;
    }

    public List<Category> findAll() {
        return this.categoriesRepository.findAll();
    }

    public Category update(UUID id, CategoryDTO categoryDTO) {
        Category existentCategory = this.categoriesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource with this id does not exists!"));

        var existentNameCategory = this.categoriesRepository.findByName(categoryDTO.getName());

        if (existentNameCategory.isPresent()) {
            log.error("Category with name {} already exists!", categoryDTO.getName());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Resource with this name already exists!");
        }

        existentCategory.setName(categoryDTO.getName());

        Category category = this.categoriesRepository.save(existentCategory);

        log.info("Successfully updated category:: {}", category);

        return category;
    }

    public void delete(UUID id) {
        Category existentCategory = this.categoriesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource with this id does not exists!"));

        this.categoriesRepository.delete(existentCategory);

        log.info("Successfully deleted category:: {}", existentCategory);
    }
}
