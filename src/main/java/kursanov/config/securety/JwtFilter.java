package kursanov.config.securety;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kursanov.entities.User;
import kursanov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String headerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        String bearer = "Bearer ";
        if (headerToken != null && headerToken.startsWith(bearer)){
            String token = headerToken.substring(bearer.length());

            try {
                String email = jwtService.verifyToken(token);
                User user = userRepo.getByEmail(email);

                SecurityContextHolder.getContext()
                        .setAuthentication(
                                new UsernamePasswordAuthenticationToken(
                                        user.getEmail(),
                                        null,
                                        user.getAuthorities()
                                )
                        );
            }catch (JWTVerificationException e){
                response.sendError(HttpServletResponse.SC_FORBIDDEN,
                        "Invalid token");
            }
        }
        filterChain.doFilter(request, response);
    }
}