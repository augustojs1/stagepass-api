package br.com.augustojsdev.stagepass.domain.users;

import br.com.augustojsdev.stagepass.domain.auth.dtos.SignUpDTO;
import br.com.augustojsdev.stagepass.domain.users.entities.User;
import br.com.augustojsdev.stagepass.domain.users.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User create(SignUpDTO signUpDTO) {
        User user = this.userMapper.fromSignUpDto(signUpDTO);

        this.userRepository.save(user);

        log.info("Successfully created user!");

        return user;
    }
}
