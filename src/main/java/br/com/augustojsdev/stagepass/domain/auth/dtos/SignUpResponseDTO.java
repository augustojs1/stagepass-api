package br.com.augustojsdev.stagepass.domain.auth.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpResponseDTO {
    private String firstName;
    private String lastName;
    private String email;
}
