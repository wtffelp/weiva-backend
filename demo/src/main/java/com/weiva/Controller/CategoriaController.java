package com.weiva.Controller;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weiva.Annotations.AnnotationExclusionStrategy;
import com.weiva.Model.CategoriaModel;
import com.weiva.Service.CategoriaService;

import io.javalin.Javalin;

public class CategoriaController {
    CategoriaService categoriaService = new CategoriaService();
    private Gson gson = new GsonBuilder()
    .setExclusionStrategies(new AnnotationExclusionStrategy()).create();

    public void categoriaRoutes(Javalin app){
        app.get("/categorias/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            CategoriaModel cat = categoriaService.buscarPorId(id);
            ctx.result(gson.toJson(cat));
        });

        app.get("/categorias", ctx -> {
            String nome = ctx.queryParam("nome");
            if (nome != null) {
                CategoriaModel cat = categoriaService.buscarPorNome(nome);
                ctx.result(gson.toJson(cat));
            } else {
                List<CategoriaModel> cat = categoriaService.buscarTodasAsCategorias();
                ctx.result(gson.toJson(cat));
            }
        });

        app.post("/categorias", ctx -> {
            CategoriaModel postCat = gson.fromJson(ctx.body(), CategoriaModel.class);
            categoriaService.criarCategoria(
                postCat.getNome(),
                postCat.getDescricao()
            );
        });

        app.delete("/categorias/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            categoriaService.deletarCategoria(id);
            ctx.status(204);
        });
    }
}
