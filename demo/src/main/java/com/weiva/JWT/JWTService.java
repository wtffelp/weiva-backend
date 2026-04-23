package com.weiva.JWT;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTService {

    private final String SECRET = "WeivaTempSecretKey";

    public String gerarToken(String userId, String role) {
        return JWT.create()
                .withSubject(userId)
                .withClaim("role", role)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .sign(Algorithm.HMAC256(SECRET));
    }

    public DecodedJWT decode(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET))
                .build()
                .verify(token);
    }
}