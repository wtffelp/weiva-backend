package com.weiva.Controller;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weiva.Annotations.AnnotationExclusionStrategy;
import com.weiva.Model.ProdutoModel;
import com.weiva.Service.ProdutoService;

import io.javalin.Javalin;

public class ProdutoController {
    ProdutoService produtoService = new ProdutoService();
    private static Gson gson = new GsonBuilder()
    .setExclusionStrategies(new AnnotationExclusionStrategy()).create();
    
    public void produtoRoutes(Javalin app){
        app.get("/produto/{id}", ctx -> {
            int id = Integer .parseInt(ctx.pathParam("id"));
            ProdutoModel prod = produtoService.buscarPorId(id);
            ctx.result(gson.toJson(prod));
        });

        app.get("/produto", ctx -> {
            String nome = ctx.queryParam("nome");
            String fk_farmacia_id = ctx.queryParam("fk_farmacia_id");
            String fk_categoria_id = ctx.queryParam("fk_categoria_id");
            if (nome != null) {
                ProdutoModel prod = produtoService.buscarPorNome(nome);
                ctx.result(gson.toJson(prod));
            } else if (fk_farmacia_id != null){
                int fk_farmacia_id_int = Integer.parseInt(fk_farmacia_id);
                List<ProdutoModel> prod = produtoService.buscarPorFarmacia(fk_farmacia_id_int);
                ctx.result(gson.toJson(prod));
            } else if (fk_categoria_id != null){
                int fk_categoria_id_int = Integer.parseInt(fk_categoria_id);
                List<ProdutoModel> prod = produtoService.buscarPorCategoria(fk_categoria_id_int);
                ctx.result(gson.toJson(prod));
            } else {
                List<ProdutoModel> prod = produtoService.buscarTodosOsProdutos();
                ctx.result(gson.toJson(prod));
            }
        });

        app.post("/produto", ctx -> {
            String role = ctx.attribute("role");
            if (!role.equals("admin") && !role.equals("super_admin")) {
                ctx.status(403).result("Acesso negado");
                return;
            }
            ProdutoModel postProd = gson.fromJson(ctx.body(), ProdutoModel.class);
            // ALTERAÇÃO: postProd.getId() foi removido pois chegava como 0 (default do int primitivo).
            // O id agora é gerado automaticamente pelo banco (auto-increment).
            // O parâmetro "id" no service/repo ainda existe para compatibilidade, mas é ignorado.
            produtoService.criarProduto(
                0, // antes: postProd.getId()
                postProd.getNome(), 
                postProd.getDescricao(), 
                postProd.getPreco_unitario(), 
                postProd.getCaminho_galeria(),
                postProd.getFk_farmacia_id(), 
                postProd.getFk_categoria_id()
            );
        });

        app.put("/produto/{id}", ctx -> {
            String role = ctx.attribute("role");
            if (!role.equals("admin") && !role.equals("super_admin")) {
                ctx.status(403).result("Acesso negado");
                return;
            }
            int id = Integer.parseInt(ctx.pathParam("id"));
            ProdutoModel atualizarProduto = gson.fromJson(ctx.body(), ProdutoModel.class);
            produtoService.atualizarProduto(
                id,
                atualizarProduto.getNome(),
                atualizarProduto.getDescricao(),
                atualizarProduto.getPreco_unitario(),
                atualizarProduto.getCaminho_galeria(),
                atualizarProduto.getFk_farmacia_id(),
                atualizarProduto.getFk_categoria_id()
            );
        });

        app.put("/produto/{id}/ativo", ctx -> {
            String role = ctx.attribute("role");
            if (!role.equals("admin") && !role.equals("super_admin")) {
                ctx.status(403).result("Acesso negado");
                return;
            }
            int id = Integer.parseInt(ctx.pathParam("id"));
            ProdutoModel atualizarProdutoAtivo = gson.fromJson(ctx.body(), ProdutoModel.class);
            produtoService.atualizarAtivo(
                id,
                atualizarProdutoAtivo.getAtivo()
            );
        });

        app.delete("/produto/{id}", ctx -> {
            String role = ctx.attribute("role");
            if (!role.equals("super_admin")) {
                ctx.status(403).result("Acesso negado");
                return;
            }
            int id = Integer.parseInt(ctx.pathParam("id"));
            produtoService.deletarProduto(id);
            ctx.status(204);
        });
    }
}
