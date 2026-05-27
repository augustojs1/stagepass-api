package br.com.augustojsdev.stagepass.domain.organizations.dtos;

import lombok.*;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
public class UpdateOrganizationDTO {
    private String name;
    private String description;
    private String contactEmail;
    private String websiteUrl;
}