package br.com.augustojsdev.stagepass.domain.organizations.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
public class OrganizationDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String contactEmail;

    private String websiteUrl;
}
