package br.com.augustojsdev.stagepass.domain.users.mappers;

import br.com.augustojsdev.stagepass.domain.auth.dtos.SignUpDTO;
import br.com.augustojsdev.stagepass.domain.auth.dtos.SignUpResponseDTO;
import br.com.augustojsdev.stagepass.domain.users.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User fromSignUpDto(SignUpDTO signUpDTO) {
        return User.builder()
                .firstName(signUpDTO.getFirstName())
                .lastName(signUpDTO.getLastName())
                .email(signUpDTO.getEmail())
                .password(signUpDTO.getPassword())
                .isAdmin(false)
                .build();
    }

    public SignUpResponseDTO toSignUpResponseDTO(User user) {
        return SignUpResponseDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}
