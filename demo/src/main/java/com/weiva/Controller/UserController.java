package com.weiva.Controller;

import com.weiva.Annotations.AnnotationExclusionStrategy;
import com.weiva.Model.UserModel;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weiva.Service.UserService;

import io.javalin.Javalin;

public class UserController {
    UserService userService = new UserService();
    private static Gson gsonSemSenha = new GsonBuilder()
    .setExclusionStrategies(new AnnotationExclusionStrategy()).create();
    private static Gson gsonComSenha = new Gson();
    
    public void userRoutes(Javalin app){
        app.get("/usuario/{id}", ctx ->{
            int id = Integer.parseInt(ctx.pathParam("id"));
            String role = ctx.attribute("role");
            String userIdToken = ctx.attribute("user");
            if (!role.equals("super_admin") && !userIdToken.equals(String.valueOf(id))) {
                ctx.status(403).result("Acesso negado");
                return;
            }
            UserModel user = userService.buscarPorId(id);
            String jsonConversor = gsonSemSenha.toJson(user);
            ctx.result(jsonConversor);
        });
        app.get("/usuario", ctx -> {
            String role = ctx.attribute("role");
            if (!role.equals("super_admin")){
                ctx.status(403).result("Acesso negado");
                return;
            }
            String email = ctx.queryParam("email");
            String nome = ctx.queryParam("nome");
            String telefone = ctx.queryParam("telefone");
            String cpf = ctx.queryParam("cpf");
            if (email != null) {
                UserModel user = userService.buscarPorEmail(email);
                ctx.result(gsonSemSenha.toJson(user));
            } else if (nome != null) {
                List<UserModel> user = userService.buscarPorNome(nome);
                ctx.result(gsonSemSenha.toJson(user));
            } else if (telefone != null){
                UserModel user = userService.buscarPorTelefone(telefone);
                ctx.result(gsonSemSenha.toJson(user));
            } else if (cpf != null) {
                UserModel user = userService.buscarPorCPF(cpf);
                ctx.result(gsonSemSenha.toJson(user));
            } else {
                List<UserModel> users = userService.buscarTodosOsUsuario();
                String jsonConversor = gsonSemSenha.toJson(users);
                ctx.result(jsonConversor);
            }
        });

        app.post("/usuario", ctx -> {
            String role = ctx.attribute("role");
            if (!role.equals("super_admin")) {
                ctx.status(403).result("Acesso negado");
                return;
            }
            UserModel postUser = gsonComSenha.fromJson(ctx.body(), UserModel.class);
            userService.criarUsuario(
                postUser.getEmail(),
                postUser.getNome(),
                postUser.getFoto_perfil_url(),
                postUser.getCPF(),
                postUser.getTelefone(),
                postUser.getSenha(),
                postUser.getRole()
            );
            ctx.status(201);
        });

        app.put("/usuario/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            String role = ctx.attribute("role");
            String userIdToken = ctx.attribute("user");
            if (!role.equals("super_admin") && !userIdToken.equals(String.valueOf(id))) {
                ctx.status(403).result("Acesso negado");
                return;
            }
            UserModel atualizarUser = gsonComSenha.fromJson(ctx.body(), UserModel.class);
            userService.atualizarUsuario(
                id,
                atualizarUser.getTelefone(), 
                atualizarUser.getFoto_perfil_url(),
                atualizarUser.getEmail(), 
                atualizarUser.getNome(), 
                atualizarUser.getSenha()
            );
            ctx.status(201);
        });

        app.put("/usuario/{id}/ativo", ctx -> {
            String role = ctx.attribute("role");
            if (!role.equals("super_admin")) {
                ctx.status(403).result("Acesso negado");
                return;
            }
            int id = Integer.parseInt(ctx.pathParam("id"));
            UserModel atualizarAtivoUser = gsonComSenha.fromJson(ctx.body(), UserModel.class);
            userService.atualizarAtivo(
                id,
                atualizarAtivoUser.getAtivo()
            );
        });

        app.put("/usuario/{id}/role", ctx ->{String role = ctx.attribute("role");
            if (!role.equals("super_admin")) {
                ctx.status(403).result("Acesso negado");
                return;
            }
            int id = Integer.parseInt(ctx.pathParam("id"));
            UserModel atualizarRole = gsonComSenha.fromJson(ctx.body(), UserModel.class);
            userService.atualizarRole(
                id,
                atualizarRole.getRole()
            );
        });

        app.delete("/usuario/{id}", ctx -> {
            String role = ctx.attribute("role");
            if (!role.equals("super_admin")) {
                ctx.status(403).result("Acesso negado");
                return;
            }
            int id = Integer.parseInt(ctx.pathParam("id"));
            userService.deletarUsuario(id);
            ctx.status(204);
        });
    }
}
