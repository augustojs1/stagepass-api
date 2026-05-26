package br.com.augustojsdev.stagepass.domain.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class SignInDTO {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
