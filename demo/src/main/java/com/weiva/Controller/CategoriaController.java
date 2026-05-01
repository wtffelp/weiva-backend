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
            String pai = ctx.queryParam("pai");
            String nome = ctx.queryParam("nome");
            if (nome != null) {
                CategoriaModel cat = categoriaService.buscarPorNome(nome);
                ctx.result(gson.toJson(cat));
            } else if (pai != null){
                int paiId = Integer.parseInt(pai);
                ctx.result(gson.toJson(categoriaService.buscarPorId(paiId)));
            } else {
                List<CategoriaModel> cat = categoriaService.buscarTodasAsCategorias();
                ctx.result(gson.toJson(cat));
            }
        });

        app.post("/categorias", ctx -> {
            String role = ctx.attribute("role");
            if (!role.equals("admin") && !role.equals("super_admin")) {
                ctx.status(403).result("Acesso negado");
                return;
            }
            CategoriaModel postCat = gson.fromJson(ctx.body(), CategoriaModel.class);
            categoriaService.criarCategoria(
                postCat.getNome(),
                postCat.getDescricao(),
                postCat.getFk_categoria_pai_id()
            );
        });

        app.delete("/categorias/{id}", ctx -> {
            String role = ctx.attribute("role");
            if (!role.equals("admin") && !role.equals("super_admin")) {
                ctx.status(403).result("Acesso negado");
                return;
            }
            int id = Integer.parseInt(ctx.pathParam("id"));
            categoriaService.deletarCategoria(id);
            ctx.status(204);
        });
    }
}
