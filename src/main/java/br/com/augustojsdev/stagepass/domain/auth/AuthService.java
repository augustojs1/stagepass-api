package br.com.augustojsdev.stagepass.domain.auth;

import br.com.augustojsdev.stagepass.domain.auth.dtos.SignInDTO;
import br.com.augustojsdev.stagepass.domain.auth.dtos.SignInResponseDTO;
import br.com.augustojsdev.stagepass.domain.auth.dtos.SignUpDTO;
import br.com.augustojsdev.stagepass.domain.auth.dtos.SignUpResponseDTO;
import br.com.augustojsdev.stagepass.domain.auth.providers.JwtProvider;
import br.com.augustojsdev.stagepass.domain.users.UserService;
import br.com.augustojsdev.stagepass.domain.users.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthService {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    private final PasswordEncoder passwordEncoder;

    public SignUpResponseDTO signUp(SignUpDTO signUpDTO) {
        log.info("Signing up -> {}", signUpDTO);

        signUpDTO.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));

        User user = this.userService.create(signUpDTO);

        return SignUpResponseDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

    public SignInResponseDTO signIn(SignInDTO signInDTO) {
        try {
            log.info("Signing in -> {}", signInDTO);

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signInDTO.getEmail(),
                            signInDTO.getPassword()
                    )
            );

            this.userService.findUserByEmail(authentication.getName());

            return this.jwtProvider.createAccessToken(
                    authentication.getName()
            );
        } catch (BadCredentialsException e) {
            log.error("Signing in error.: {}", e.getMessage());

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid email or password!"
            );
        }
    }

    private String generateHashedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
