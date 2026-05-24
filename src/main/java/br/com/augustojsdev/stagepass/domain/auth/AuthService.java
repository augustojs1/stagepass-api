package br.com.augustojsdev.stagepass.domain.auth;

import br.com.augustojsdev.stagepass.domain.auth.dtos.SignUpDTO;
import br.com.augustojsdev.stagepass.domain.auth.dtos.SignUpResponseDTO;
import br.com.augustojsdev.stagepass.domain.users.UserService;
import br.com.augustojsdev.stagepass.domain.users.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthService {

    private final UserService userService;

    public SignUpResponseDTO signUp(SignUpDTO signUpDTO) {
        User user = this.userService.create(signUpDTO);

        return SignUpResponseDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}
