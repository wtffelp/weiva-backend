package com.weiva.Controller;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weiva.Annotations.AnnotationExclusionStrategy;
import com.weiva.JWT.JWTService;
import com.weiva.Model.RefreshTokenModel;
import com.weiva.Model.UserModel;
import com.weiva.Service.RefreshTokenService;
import com.weiva.Service.UserService;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.javalin.Javalin;

public class AuthController {
    private UserService userService = new UserService();
    private RefreshTokenService refreshTokenService = new RefreshTokenService();
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

            String acess_token = jwtService.gerarToken(
                String.valueOf(userDB.getId()),
                userDB.getRole()
            );
            Map<String, String> response = new HashMap<>();
            RefreshTokenModel refreshToken = refreshTokenService.criarToken(userDB.getId());
            response.put("acess_token", acess_token);
            response.put("refresh_token", refreshToken.getToken());
            ctx.result(gsonComSenha.toJson(response));
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

        app.post("/auth/refresh", ctx -> {
            Map<String, String> request = gsonComSenha.fromJson(ctx.body(), Map.class);
            String refreshTokenStr = request.get("refresh_token");

            if (refreshTokenStr == null || refreshTokenStr.isEmpty()) {
                ctx.status(400).result("Refresh token não fornecido");
                return;
            }

            try {
                // validar o refresh token (verifica se existe, esta ativ e não expirou)
                RefreshTokenModel refreshToken = refreshTokenService.validarToken(refreshTokenStr);

                // buscar usuario pelo id armazenado no refresh token
                UserModel user = userService.buscarPorId(refreshToken.getFk_usuario_id());
                if (user == null || user.getAtivo() == 0) {
                    ctx.status(403).result("Usuario inválido ou desativado");
                    return;
                }

                // gerar novo access token
                String newAccessToken = jwtService.gerarToken(
                    String.valueOf(user.getId()),
                    user.getRole()
                );

                // retornar apenas o novo access token (refresh token continua o msesmo)
                Map<String, String> response = new HashMap<>();
                response.put("access_token", newAccessToken);
                ctx.result(gsonComSenha.toJson(response));
            } catch (RuntimeException e){
                ctx.status(401).result("Refresh token inválido ou expirado");
            }
        });
    }
}