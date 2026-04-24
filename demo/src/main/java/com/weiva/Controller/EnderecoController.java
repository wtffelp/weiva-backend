package com.weiva.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weiva.Annotations.AnnotationExclusionStrategy;
import com.weiva.Model.EnderecoModel;
import com.weiva.Service.EnderecoService;

import io.javalin.Javalin;

public class EnderecoController {
    EnderecoService enderecoService = new EnderecoService();
    private static Gson gson = new GsonBuilder()
    .setExclusionStrategies(new AnnotationExclusionStrategy()).create();

    public void enderecoRoutes(Javalin app){
        app.get("/enderecos/me", ctx -> {
            String userId = ctx.attribute("user");
            ctx.result(gson.toJson(enderecoService.buscarPorUsuario(Integer.parseInt(userId))));
        });

        app.get("/enderecos/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            String userId = ctx.attribute("user");
            EnderecoModel end = enderecoService.buscarPorId(id);
            if (end == null || end.getFk_usuario_id() != Integer.parseInt(userId)) {
                ctx.status(403).result("Acesso negado");
                return;
            }
            ctx.result(gson.toJson(end));
        });

        app.put("/enderecos/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            String userId = ctx.attribute("user");
            EnderecoModel existente = enderecoService.buscarPorId(id);
            if (existente == null || existente.getFk_usuario_id() != Integer.parseInt(userId)) {
                ctx.status(403).result("Acesso negado");
                return;
            }
            EnderecoModel end = gson.fromJson(ctx.body(), EnderecoModel.class);
            enderecoService.atualizarEndereco(
                id,
                end.getLogradouro(),
                end.getNumero(),
                end.getBairro(),
                end.getCidade(),
                end.getCep()
            );
            ctx.status(200);
        });

        app.post("/enderecos", ctx -> {
            String userId = ctx.attribute("user"); 
            EnderecoModel end = gson.fromJson(ctx.body(), EnderecoModel.class);
            enderecoService.criarEndereco(
                end.getLogradouro(),
                end.getNumero(),
                end.getBairro(),
                end.getCidade(),
                end.getEstado(),
                end.getCep(),
                Integer.parseInt(userId)
            );
            ctx.status(201);
        });

        app.delete("/enderecos/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            String userId = ctx.attribute("user");
            EnderecoModel existente = enderecoService.buscarPorId(id);
            if (existente == null || existente.getFk_usuario_id() != Integer.parseInt(userId)) {
                ctx.status(403).result("Acesso negado");
                return;
            }

            enderecoService.deletarEndereco(id);
            ctx.status(204);
        });
    }
}
