package br.com.augustojsdev.stagepass.domain.organizations;

import br.com.augustojsdev.stagepass.domain.organizations.entities.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, UUID> {
}
