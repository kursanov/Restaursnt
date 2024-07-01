package kursanov.config.securety;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import kursanov.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secretKey;

    public String createToken(User user) {
        return JWT.create()
                .withClaim("email", user.getUsername())
                .withClaim("role", user.getRole().name())
                .withClaim("id", user.getId())
                .withIssuedAt(ZonedDateTime.now().toInstant())
                .withExpiresAt(ZonedDateTime.now().plusHours(23).toInstant())
                .sign(Algorithm.HMAC512(secretKey));
    }

    public String verifyToken(String token) {
        JWTVerifier jwtVerify = JWT.require(Algorithm.HMAC512(secretKey)).build();
        DecodedJWT decodedJWT = jwtVerify.verify(token);
        return decodedJWT.getClaim("email").asString();
    }
}