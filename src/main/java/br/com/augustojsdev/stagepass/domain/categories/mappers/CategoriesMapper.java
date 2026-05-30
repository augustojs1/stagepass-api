package br.com.augustojsdev.stagepass.domain.categories.mappers;

import br.com.augustojsdev.stagepass.domain.categories.dto.CategoryDTO;
import br.com.augustojsdev.stagepass.domain.categories.dto.CategoryResponseDTO;
import br.com.augustojsdev.stagepass.domain.categories.entities.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoriesMapper {

    public CategoryResponseDTO toCategoryResponseDTO(Category category) {
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public Category toEntity(CategoryDTO categoryDTO) {
        return Category.builder()
                .name(categoryDTO.getName())
                .build();
    }

    public List<CategoryResponseDTO> toCategoryResponseDTO(List<Category> categories) {
        return categories.stream().map(this::toCategoryResponseDTO).toList();
    }
}
