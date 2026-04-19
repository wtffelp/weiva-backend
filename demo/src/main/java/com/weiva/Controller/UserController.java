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
    private static Gson gson = new GsonBuilder()
    .setExclusionStrategies(new AnnotationExclusionStrategy()).create();
    
    public void registrarRotas(Javalin app){
        app.get("/usuarios/{id}", ctx ->{
            int id = Integer.parseInt(ctx.pathParam("id"));
            UserModel user = userService.buscarPorId(id);
            String jsonConversor = gson.toJson(user);
            ctx.result(jsonConversor);
        });
        app.get("/usuarios", ctx -> {
            String email = ctx.queryParam("email");
            String nome = ctx.queryParam("nome");
            String telefone = ctx.queryParam("telefone");
            String cpf = ctx.queryParam("cpf");
            if (email != null) {
                UserModel user = userService.buscarPorEmail(email);
                ctx.result(gson.toJson(user));
            } else if (nome != null) {
                List<UserModel> user = userService.buscarPorNome(nome);
                ctx.result(gson.toJson(user));
            } else if (telefone != null){
                UserModel user = userService.buscarPorTelefone(telefone);
                ctx.result(gson.toJson(user));
            } else if (cpf != null) {
                UserModel user = userService.buscarPorCPF(cpf);
                ctx.result(gson.toJson(user));
            } else {
                List<UserModel> users = userService.buscarTodosOsUsuarios();
                String jsonConversor = gson.toJson(users);
                ctx.result(jsonConversor);
            }
        });

        app.post("/usuarios", ctx -> {
            UserModel postUser = gson.fromJson(ctx.body(), UserModel.class);
            userService.criarUsuario(postUser.getEmail(), postUser.getNome(), postUser.getCPF(), postUser.getTelefone(), postUser.getSenha(), postUser.getRole());
            ctx.status(201);
        });

        app.put("/usuarios/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            UserModel atualizarUser = gson.fromJson(ctx.body(), UserModel.class);
            userService.atualizarUsuario(
                id,
                atualizarUser.getTelefone(), 
                atualizarUser.getEmail(), 
                atualizarUser.getNome(), 
                atualizarUser.getSenha()
            );
            ctx.status(201);
        });

        app.put("/usuarios/{id}/ativo", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            UserModel atualizarAtivo = gson.fromJson(ctx.body(), UserModel.class);
            userService.atualizarAtivo(
                id,
                atualizarAtivo.getAtivo()
            );
        });

        app.put("/usuarios/{id}/role", ctx ->{
            int id = Integer.parseInt(ctx.pathParam("id"));
            UserModel atualizarRole = gson.fromJson(ctx.body(), UserModel.class);
            userService.atualizarRole(
                id,
                atualizarRole.getRole()
            );
        });

        app.delete("/usuarios/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            userService.deletarUsuario(id);
            ctx.status(204);
        });
    }
}
