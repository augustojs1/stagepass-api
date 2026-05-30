package br.com.augustojsdev.stagepass.domain.categories.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
public class CategoryDTO {
    @NotBlank
    private String name;
}
