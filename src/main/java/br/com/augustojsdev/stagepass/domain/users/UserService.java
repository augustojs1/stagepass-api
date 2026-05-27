package br.com.augustojsdev.stagepass.domain.users;

import br.com.augustojsdev.stagepass.domain.auth.dtos.SignUpDTO;
import br.com.augustojsdev.stagepass.domain.users.entities.User;
import br.com.augustojsdev.stagepass.domain.users.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User create(SignUpDTO signUpDTO) {
        User user = this.userMapper.fromSignUpDto(signUpDTO);

        this.userRepository.save(user);

        log.info("Successfully created user!");

        return user;
    }

    public User findById(UUID id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this id not found!"));
    }

    public User findUserByEmail(String email) {
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect email or password!"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }
}
