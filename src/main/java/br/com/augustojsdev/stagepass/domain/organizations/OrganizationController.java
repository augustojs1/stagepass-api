package br.com.augustojsdev.stagepass.domain.organizations;

import br.com.augustojsdev.stagepass.domain.organizations.dtos.OrganizationDTO;
import br.com.augustojsdev.stagepass.domain.organizations.dtos.OrganizationResponseDTO;
import br.com.augustojsdev.stagepass.domain.users.entities.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
