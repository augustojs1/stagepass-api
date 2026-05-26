package br.com.augustojsdev.stagepass.domain.auth.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponseDTO {
    private String accessToken;
}
