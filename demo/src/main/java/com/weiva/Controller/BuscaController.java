package com.weiva.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weiva.Annotations.AnnotationExclusionStrategy;
import com.weiva.Service.BuscaService;

import io.javalin.Javalin;

public class BuscaController {
    BuscaService buscaService = new BuscaService();
    private static Gson gson = new GsonBuilder()
    .setExclusionStrategies(new AnnotationExclusionStrategy()).create();
    public void buscarRoute(Javalin app){
        app.get("/busca", ctx -> {
            String termo = ctx.queryParam("q");
            ctx.result(gson.toJson(buscaService.buscar(termo)));
        });
    }
}
