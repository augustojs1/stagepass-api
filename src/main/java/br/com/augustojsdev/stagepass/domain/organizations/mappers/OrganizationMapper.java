package br.com.augustojsdev.stagepass.domain.organizations.mappers;

import br.com.augustojsdev.stagepass.domain.organizations.dtos.OrganizationDTO;
import br.com.augustojsdev.stagepass.domain.organizations.dtos.OrganizationResponseDTO;
import br.com.augustojsdev.stagepass.domain.organizations.entities.Organization;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrganizationMapper {

    public OrganizationResponseDTO fromEntityToOrganizationResponseDTO(Organization organization) {
        return OrganizationResponseDTO.builder()
                .id(organization.getId())
                .name(organization.getName())
                .description(organization.getDescription())
                .contactEmail(organization.getContactEmail())
                .websiteUrl(organization.getWebsiteUrl())
                .createdAt(organization.getCreatedAt())
                .build();

    }

    public Organization fromOrganizationDTOToEntity(UUID userId, OrganizationDTO organizationDTO) {
        return  Organization.builder()
                .userId(userId)
                .name(organizationDTO.getName())
                .description(organizationDTO.getDescription())
                .active(true)
                .contactEmail(organizationDTO.getContactEmail())
                .websiteUrl(organizationDTO.getWebsiteUrl() != null ? organizationDTO.getWebsiteUrl() : null)
                .build();
    }

    public List<OrganizationResponseDTO> fromEntityToOrganizationResponseDTO(List<Organization> organizations) {
        return organizations.stream().map(this::fromEntityToOrganizationResponseDTO).collect(Collectors.toList());
    }
}
