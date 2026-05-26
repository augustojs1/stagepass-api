package br.com.augustojsdev.stagepass.domain.auth.providers;

import br.com.augustojsdev.stagepass.domain.auth.dtos.SignInResponseDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length}")
    private long validityInMilliseconds;

    private final UserDetailsService userDetailsService;

    private Algorithm algorithm;

    @PostConstruct
    protected void init() {
        this.algorithm = Algorithm.HMAC256(this.secretKey);
    }

    public SignInResponseDTO createAccessToken(String email) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        Date now = Date.from(Instant.now());
        Date validity = new Date(now.getTime() + this.validityInMilliseconds);

        String accessToken = this.getAccessToken(email, roles, now, validity);

        return SignInResponseDTO.builder()
                .accessToken(accessToken)
                .build();
    }

    private String getAccessToken(String email, List<String> roles, Date now, Date validity) {
        String issuerUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .build()
                .toUriString();

        return JWT.create()
                .withSubject(email)
                .withClaim("roles", roles)
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withIssuer(issuerUrl)
                .sign(this.algorithm);
    }

    public DecodedJWT decodedToken(String token) {
        JWTVerifier jwtVerifier = JWT.require(this.algorithm).build();

        return jwtVerifier.verify(token);
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }

        return null;
    }

    public boolean validateToken(String token) {
        try {
            DecodedJWT decodedJWT = this.decodedToken(token);

            return decodedJWT.getExpiresAt().after(new Date());
        } catch (RuntimeException exception) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        DecodedJWT decodedJWT = this.decodedToken(token);

        UserDetails userDetails = this.userDetailsService
                .loadUserByUsername(decodedJWT.getSubject());

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }
}
