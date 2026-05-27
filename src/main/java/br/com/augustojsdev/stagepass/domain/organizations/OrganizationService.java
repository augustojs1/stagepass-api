package br.com.augustojsdev.stagepass.domain.organizations;

import br.com.augustojsdev.stagepass.domain.organizations.dtos.OrganizationDTO;
import br.com.augustojsdev.stagepass.domain.organizations.dtos.OrganizationResponseDTO;
import br.com.augustojsdev.stagepass.domain.organizations.entities.Organization;
import br.com.augustojsdev.stagepass.domain.organizations.mappers.OrganizationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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
}
