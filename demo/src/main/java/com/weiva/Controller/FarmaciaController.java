package com.weiva.Controller;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weiva.Annotations.AnnotationExclusionStrategy;
import com.weiva.Model.FarmaciaModel;
import com.weiva.Service.FarmaciaService;

import io.javalin.Javalin;

public class FarmaciaController {
    FarmaciaService farmaciaService = new FarmaciaService();
    private static Gson gson = new GsonBuilder()
    .setExclusionStrategies(new AnnotationExclusionStrategy()).create();

    public void farmaciaRoutes(Javalin app) {
        app.get("/farmacias/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            FarmaciaModel farma = farmaciaService.buscarPorId(id);
            ctx.result(gson.toJson(farma));
        });
        app.get("/farmacias", ctx -> {
            // cpnj, nome, avaliacao
            String cnpj = ctx.queryParam("cnpj");
            String nome = ctx.queryParam("nome");
            String avaliacao = ctx.queryParam("avaliacao");
            String fk_usuario_id = ctx.queryParam("fk_usuario_id");
            if (cnpj != null) {
                FarmaciaModel farma = farmaciaService.buscarPorCnpj(cnpj);
                ctx.result(gson.toJson(farma));
            } else if (fk_usuario_id != null){
                int farmaInt = Integer.parseInt(fk_usuario_id);
                FarmaciaModel farma = farmaciaService.buscarPorUsuario(farmaInt);
                ctx.result(gson.toJson(farma));
            } else if (nome != null) {
                List<FarmaciaModel> farma = farmaciaService.buscarPorNome(nome);
                ctx.result(gson.toJson(farma));
            } else if (avaliacao != null) {
                double avaliacaoDouble = Double.parseDouble(avaliacao);
                List<FarmaciaModel> farma = farmaciaService.buscarPorAvaliacao(avaliacaoDouble);
                ctx.result(gson.toJson(farma));
            } else {
                List<FarmaciaModel> farma = farmaciaService.buscarTodasAsFarmacias();
                ctx.result(gson.toJson(farma));
            }
        });

        app.post("/farmacias", ctx -> {
            String role = ctx.attribute("role");
            if (!role.equals("admin") && !role.equals("super_admin") && !role.equals("farmacia")) {
                ctx.status(403).result("Acesso negado");
                return;
            }
            FarmaciaModel postFarma = gson.fromJson(ctx.body(), FarmaciaModel.class);
            int fk_usuario_id;
            if (role.equals("farmacia")) {
                fk_usuario_id = Integer.parseInt(ctx.attribute("user"));
            } else {
                fk_usuario_id = postFarma.getFk_usuario_id();
            }
            farmaciaService.criarFarmacia(
                fk_usuario_id,
                postFarma.getCnpj(),
                postFarma.getNome(),
                postFarma.getDescricao(),
                postFarma.getAvalicao(),
                postFarma.getImagem_perfil()
            );
            ctx.status(201);
        });

        app.put("/farmacias/{id}", ctx -> {
            String role = ctx.attribute("role");
            if(!role.equals("admin") && !role.equals("super_admin") && !role.equals("farmacia")){
                ctx.status(403).result("Acesso negado");
                return;
            }
            int id = Integer.parseInt(ctx.pathParam("id"));
            if (role.equals("farmacia")) {
                int userId = Integer.parseInt(ctx.attribute("user"));
                FarmaciaModel farma = farmaciaService.buscarPorUsuario(userId);
                if (farma == null || farma.getId() != id) {
                    ctx.status(403).result("Acesso negado");
                    return;
                }
            }
            FarmaciaModel atualizarFarmacia = gson.fromJson(ctx.body(), FarmaciaModel.class);
            farmaciaService.atualizarFarmacia(
                id,
                atualizarFarmacia.getNome(),
                atualizarFarmacia.getImagem_perfil()
            );
        });

        app.put("/farmacias/{id}/ativo", ctx -> {
            String role = ctx.attribute("role");
            if(!role.equals("super_admin")){
                ctx.status(403).result("Acesso negado");
                return;
            }
            int id = Integer.parseInt(ctx.pathParam("id"));
            FarmaciaModel atualizarAtivoFarma = gson.fromJson(ctx.body(), FarmaciaModel.class);
            farmaciaService.atualizarAtivo(
                id,
                atualizarAtivoFarma.getAtivo()
            );
        });

        app.delete("/farmacias/{id}", ctx -> {
            String role = ctx.attribute("role");
            if(!role.equals("super_admin")){
                ctx.status(403).result("Acesso negado");
                return;
            }
            int id = Integer.parseInt(ctx.pathParam("id"));
            farmaciaService.deletarFarmacia(id);
            ctx.status(204);
        });
    }
}
