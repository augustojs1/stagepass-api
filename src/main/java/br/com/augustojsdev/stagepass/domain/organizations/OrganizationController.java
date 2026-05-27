package br.com.augustojsdev.stagepass.domain.organizations;

import br.com.augustojsdev.stagepass.domain.organizations.dtos.OrganizationDTO;
import br.com.augustojsdev.stagepass.domain.organizations.dtos.OrganizationResponseDTO;
import br.com.augustojsdev.stagepass.domain.organizations.dtos.UpdateOrganizationDTO;
import br.com.augustojsdev.stagepass.domain.users.entities.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @PostMapping
    public ResponseEntity<OrganizationResponseDTO> create(@AuthenticationPrincipal User user,
                                                          @RequestBody @Valid OrganizationDTO organizationDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.organizationService.create(user.getId() ,organizationDTO));
    }

    @GetMapping
    public ResponseEntity<List<OrganizationResponseDTO>> getAllByUserId(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(this.organizationService.findAllByUserId(user.getId()));
    }

    @GetMapping("/{organizationId}")
    public ResponseEntity<OrganizationResponseDTO> getById(@PathVariable UUID organizationId) {
        return ResponseEntity.ok(this.organizationService.findById(organizationId));
    }

    @PatchMapping("/{organizationId}")
    public ResponseEntity<OrganizationResponseDTO> updateById(@PathVariable UUID organizationId,
                                                              @RequestBody @Valid UpdateOrganizationDTO updateOrganizationDTO) {
        return ResponseEntity.ok(this.organizationService.update(organizationId, updateOrganizationDTO));
    }

    @DeleteMapping("/{organizationId}")
    public ResponseEntity<OrganizationResponseDTO> markAsInactive(@PathVariable UUID organizationId) {
        this.organizationService.markAsInactive(organizationId);

        return ResponseEntity.noContent().build();
    }
}
