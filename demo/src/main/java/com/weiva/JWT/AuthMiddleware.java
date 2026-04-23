package com.weiva.JWT;

import com.auth0.jwt.interfaces.DecodedJWT;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class AuthMiddleware implements Handler{
    private JWTService jwtService = new JWTService();

    @Override
    public void handle(Context ctx) throws Exception {
        String path = ctx.path();

        // liberar rotas publicas
        if (path.startsWith("/auth") || path.equals("/usuario") && ctx.method().equals("POST")) {
            return;
        }

        String header = ctx.header("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            ctx.status(401).result("Token não enviado");
            return;
        }

        String token = header.substring(7);

        try {
            DecodedJWT jwt = jwtService.decode(token);

            String user = jwt.getSubject();
            String role = jwt.getClaim("role").asString();

            ctx.attribute("user", user);
            ctx.attribute("role", role);
        } catch (Exception e){
            ctx.status(401).result("Token Invalido");
        }
    }
}
