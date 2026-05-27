package br.com.augustojsdev.stagepass.domain.organizations;

import br.com.augustojsdev.stagepass.domain.organizations.dtos.OrganizationDTO;
import br.com.augustojsdev.stagepass.domain.organizations.dtos.OrganizationResponseDTO;
import br.com.augustojsdev.stagepass.domain.organizations.dtos.UpdateOrganizationDTO;
import br.com.augustojsdev.stagepass.domain.organizations.entities.Organization;
import br.com.augustojsdev.stagepass.domain.organizations.mappers.OrganizationMapper;
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
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;

    public OrganizationResponseDTO create(UUID userId, OrganizationDTO organizationDTO) {
        Organization organization = this.organizationMapper.fromOrganizationDTOToEntity(userId, organizationDTO);

        Organization createdOrganization = this.organizationRepository.save(organization);

        log.info("Successfully created organization {}", createdOrganization);

        return this.organizationMapper.fromEntityToOrganizationResponseDTO(createdOrganization);
    }

    public OrganizationResponseDTO findById(UUID id) {
        Organization organization = this.organizationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource with id " + id + " not found!"));

        return this.organizationMapper.fromEntityToOrganizationResponseDTO(organization);
    }

    public List<OrganizationResponseDTO> findAllByUserId(UUID userId) {
        List<Organization> organizations = this.organizationRepository.findAllByUserIdAndActiveTrue(userId);

        return this.organizationMapper.fromEntityToOrganizationResponseDTO(organizations);
    }

    public OrganizationResponseDTO update(UUID orgId, UpdateOrganizationDTO updateOrganizationDTO) {
        Organization organization =this.organizationRepository.findById(orgId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource with id " + orgId + " not found!"));

        if (updateOrganizationDTO.getName() != null) {
            organization.setName(updateOrganizationDTO.getName());
        }

        if (updateOrganizationDTO.getDescription() != null) {
            organization.setDescription(updateOrganizationDTO.getDescription());
        }

        if (updateOrganizationDTO.getContactEmail() != null) {
            organization.setContactEmail(updateOrganizationDTO.getContactEmail());
        }

        if (updateOrganizationDTO.getWebsiteUrl() != null) {
            organization.setWebsiteUrl(updateOrganizationDTO.getWebsiteUrl());
        }

        return this.organizationMapper.fromEntityToOrganizationResponseDTO(this.organizationRepository.save(organization));
    }

    public void markAsInactive(UUID orgId) {
        Organization organization =this.organizationRepository.findById(orgId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource with id " + orgId + " not found!"));

        organization.setActive(false);

        this.organizationRepository.save(organization);
    }
}
