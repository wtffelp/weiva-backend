package com.weiva.JWT;

import com.auth0.jwt.interfaces.DecodedJWT;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.http.HandlerType;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class AuthMiddleware implements Handler{
    private JWTService jwtService = new JWTService();
    
    @Override
    public void handle(Context ctx) throws Exception {
        String path = ctx.path();

        boolean rotaPublica = 
        path.startsWith("/auth") || 
        (path.startsWith("/produto") && ctx.method() == HandlerType.GET) ||
        (path.startsWith("/farmacias") && ctx.method() == HandlerType.GET) ||
        (path.startsWith("/categorias") && ctx.method() == HandlerType.GET) ||
        (path.startsWith("/usuario") && ctx.method() == HandlerType.POST);

        if (rotaPublica) return;

        String header = ctx.header("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            throw new UnauthorizedResponse("Token nao enviado");
        }

        String token = header.substring(7);

        try {
            DecodedJWT jwt = jwtService.decode(token);

            String user = jwt.getSubject();
            String role = jwt.getClaim("role").asString();

            ctx.attribute("user", user);
            ctx.attribute("role", role);
        } catch (Exception e){
            throw new UnauthorizedResponse("Token Invalido");
        }
    }
}
