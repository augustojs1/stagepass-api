package br.com.augustojsdev.stagepass.domain.auth;

import br.com.augustojsdev.stagepass.domain.auth.dtos.SignUpDTO;
import br.com.augustojsdev.stagepass.domain.users.UserService;
import br.com.augustojsdev.stagepass.domain.users.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    public User signUp(SignUpDTO signUpDTO) {
        return this.userService.create(signUpDTO);
    }
}
