package com.weiva.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weiva.Annotations.AnnotationExclusionStrategy;
import com.weiva.JWT.JWTService;
import com.weiva.Model.UserModel;
import com.weiva.Service.UserService;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.javalin.Javalin;

public class AuthController {
    private UserService userService = new UserService();
    private JWTService jwtService = new JWTService();
    private Gson gsonSemSenha = new GsonBuilder()
    .setExclusionStrategies(new AnnotationExclusionStrategy()).create();
    private static Gson gsonComSenha = new Gson();
    public void authRoutes(Javalin app) {
        app.post("/auth/login", ctx -> {
            UserModel user = gsonComSenha.fromJson(ctx.body(), UserModel.class);
            UserModel userDB = userService.buscarPorEmail(user.getEmail());

            if (userDB == null || !BCrypt.verifyer().verify(user.getSenha().toCharArray(), userDB.getSenha()).verified) {
                ctx.status(401).result("Credenciais invalidas");
                return;
            }

            if (userDB.getAtivo() == 0) {
                ctx.status(403).result("Usuario desativado");
                return;
            }

            String token = jwtService.gerarToken(
                String.valueOf(userDB.getId()),
                userDB.getRole()
            );
            ctx.result(token);
        });

        app.post("/auth/register", ctx -> {
            UserModel body = gsonComSenha.fromJson(ctx.body(), UserModel.class);
            if (userService.buscarPorEmail(body.getEmail()) != null){
                ctx.status(409).result("Email já cadastrado");
                return;
            }
            UserModel novo = userService.criarUsuario(
                body.getEmail(),
                body.getNome(),
                body.getCPF(),
                body.getTelefone(),
                body.getSenha(),
                "usuario"
            );
            ctx.status(201).result(gsonSemSenha.toJson(novo));
        });
    }
}