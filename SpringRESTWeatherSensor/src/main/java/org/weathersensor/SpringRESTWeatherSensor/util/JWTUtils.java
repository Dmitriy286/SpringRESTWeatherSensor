package org.weathersensor.SpringRESTWeatherSensor.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JWTUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Integer lifetime;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        List<String> rolesList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        claims.put("roles", rolesList);
        claims.put("username", userDetails.getUsername());

        Date issuedDate = new Date();

//        Date expiredDate = new Date(issuedDate.getTime() + lifetime.toMinutes());
        Date expiredDate = Date.from(ZonedDateTime.now().plusMinutes(lifetime).toInstant());

        return JWT
                .create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(issuedDate)
                .withExpiresAt(expiredDate)
                .withPayload(claims)
                .sign(Algorithm.HMAC256(secret));
    }

    public String getUserName(String token) {
        return getAllClaimsFromToken(token).get("username").asString();
    }

    public List<String> getRoles(String token) {
        Claim roles = getAllClaimsFromToken(token).get("roles");

        System.out.println("Roles: " + roles);

        return roles.asList(String.class);
    }

    public Map<String, Claim> getAllClaimsFromToken(String token) {
//        DecodedJWT decode = JWT.decode(token);

//        System.out.println("Decoded token payload: " + decode.getPayload());
//
//        System.out.println("Subj: " + decode.getSubject());

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .build();

        System.out.println("Claims: ");
        System.out.println(verifier.verify(token).getClaims());

        return verifier.verify(token).getClaims();
    }
}
