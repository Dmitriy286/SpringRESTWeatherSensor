package org.weathersensor.SpringRESTWeatherSensor.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.weathersensor.SpringRESTWeatherSensor.util.JWTUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTRequestFilter extends OncePerRequestFilter {

    private final JWTUtils jwtUtils;

    private static final Integer PREFIX_CHARS_COUNT = 7;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        if (Objects.nonNull(authHeader) && authHeader.startsWith("Bearer ")) {
//            jwt = authHeader.substring(PREFIX_CHARS_COUNT);
            jwt = authHeader.substring(7);

            System.out.println("jwt: " + jwt);

            try {
                username = jwtUtils.getUserName(jwt);
            } catch (TokenExpiredException exception) {
                log.debug("Token expired");
                exception.printStackTrace();
            } catch (SignatureVerificationException exception) {
                log.debug("Incorrect signature");
                exception.printStackTrace();
            }
        }

        if (Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    jwtUtils.getRoles(jwt).stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList()
            );

            SecurityContextHolder.getContext().setAuthentication(token);
        }

        filterChain.doFilter(request, response);
    }
}
