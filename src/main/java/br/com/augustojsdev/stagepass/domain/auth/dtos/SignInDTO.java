package br.com.augustojsdev.stagepass.domain.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class SignInDTO {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
