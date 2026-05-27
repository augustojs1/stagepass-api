package br.com.augustojsdev.stagepass.domain.organizations.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
public class OrganizationResponseDTO {
    private UUID id;
    private String name;
    private String description;
    private String contactEmail;
    private String websiteUrl;
    private OffsetDateTime createdAt;
}
