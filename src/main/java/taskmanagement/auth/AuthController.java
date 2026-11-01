package taskmanagement.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class AuthController {

    private final JwtEncoder jwtEncoder;
    public AuthController(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @PostMapping("/api/auth/token")
    public TokenResponse getToken(Authentication authentication)
    {
        var claims = JwtClaimsSet
                .builder()
                .subject(authentication.getName())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .build();

        JwtEncoderParameters from = JwtEncoderParameters.from(claims);

        return new TokenResponse(jwtEncoder.encode(from).getTokenValue());
    }
}
