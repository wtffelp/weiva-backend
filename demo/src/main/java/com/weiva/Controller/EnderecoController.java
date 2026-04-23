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
        app.get("/enderecos/usuario/{id}", ctx -> {
            int fk_usuario_id = Integer.parseInt(ctx.pathParam("id"));
            ctx.result(gson.toJson(enderecoService.buscarPorUsuario(fk_usuario_id)));
        });

        app.get("/enderecos/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            ctx.result(gson.toJson(enderecoService.buscarPorId(id)));
        });

        app.post("/enderecos", ctx -> {
            EnderecoModel end = gson.fromJson(ctx.body(), EnderecoModel.class);
            enderecoService.criarEndereco(
                end.getId(),
                end.getLogradouro(),
                end.getNumero(),
                end.getBairro(),
                end.getCidade(),
                end.getEstado(),
                end.getCep(),
                end.getFk_usuario_id()
            );
            ctx.status(201);
        });

        app.delete("/enderecos/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            enderecoService.deletarEndereco(id);
            ctx.status(204);
        });
    }
}
