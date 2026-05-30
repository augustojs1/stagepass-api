package br.com.augustojsdev.stagepass.domain.categories.dto;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
public class CategoryResponseDTO {
    private UUID id;
    private String name;
}
