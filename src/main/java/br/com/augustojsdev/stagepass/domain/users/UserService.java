package br.com.augustojsdev.stagepass.domain.users;

import br.com.augustojsdev.stagepass.domain.auth.dtos.SignUpDTO;
import br.com.augustojsdev.stagepass.domain.users.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User create(SignUpDTO signUpDTO) {
        User user = User.builder()
                .firstName(signUpDTO.getFirstName())
                .lastName(signUpDTO.getLastName())
                .email(signUpDTO.getEmail())
                .password(signUpDTO.getPassword())
                .isAdmin(false)
                .build();

        return this.userRepository.save(user);
    }
}
