package br.com.augustojsdev.stagepass.domain.users.dtos;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class UserDTO {
    private String firstName;
    private String lastName;
    private String email;
}
