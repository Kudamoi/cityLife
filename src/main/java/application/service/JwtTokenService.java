package application.service;

import application.dto.JwtTokenGenerated;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtTokenService {
    @Getter
    private String secretKey;

    public JwtTokenService() {
        this.secretKey = "super_secret_key";
    }

    public JwtTokenGenerated generateToken(Long id) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        Instant now = Instant.now();
        Instant exp = now.plus(5, ChronoUnit.MINUTES);

        return new JwtTokenGenerated(JWT.create()
                .withIssuer("auth-service")
                .withAudience("authorize")
                .withSubject(String.valueOf(id))
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(exp))
                .sign(algorithm), Date.from(exp));
    }

    public Boolean validToken(String token) {
        return JWT.decode(token).getExpiresAt().getTime() - new Date().getTime() > 0;
    }

    public Long parseToken(String token) {

        return Long.parseLong(JWT.decode(token).getSubject(), 10);
    }
}
