package com.weiva.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weiva.Annotations.AnnotationExclusionStrategy;
import com.weiva.Model.ItemPedidoModel;
import com.weiva.Service.ItemPedidoService;

import io.javalin.Javalin;

public class ItemPedidoController {
    ItemPedidoService itemPedidoService = new ItemPedidoService();
    private static Gson gson = new GsonBuilder()
    .setExclusionStrategies(new AnnotationExclusionStrategy()).create();

    public void itemPedidoRoutes(Javalin app){
        app.get("/itemPedido/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            ItemPedidoModel item = itemPedidoService.buscarPorId(id);
            ctx.result(gson.toJson(item));
        });
        app.get("/itemPedido/{pedido_id}", ctx -> {
            int pedido_id = Integer.parseInt(ctx.pathParam("pedido_id"));
            ItemPedidoModel item = itemPedidoService.buscarPorPedido(pedido_id);
            ctx.result(gson.toJson(item));
        });
        app.post("/itemPedido", ctx -> {
            try {
                ItemPedidoModel body = gson.fromJson(ctx.body(), ItemPedidoModel.class);
                System.out.println(
                    "Creating ItemPedido with data:" + 
                    ", fk_pedido_id= " + body.getFk_pedido_id() +
                    ", fk_produto_id= " + body.getFk_produto_id() +
                    ", quantidade= " + body.getQuantidade() +
                    ", preco_unitario= " + body.getPreco_unitario()
                );

                ItemPedidoModel novo = itemPedidoService.criarItemPedido(
                    body.getFk_pedido_id(),
                    body.getFk_produto_id(),
                    body.getQuantidade(),
                    body.getPreco_unitario()
                );
                ctx.status(201).result(gson.toJson(novo));
            } catch (Exception e) {
                System.err.println("Error creating ItemPedido: " + e.getMessage());
                e.printStackTrace();
                ctx.status(400).result("Erro: " + e.getMessage());
            }
        });
        app.delete("/itemPedido/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            itemPedidoService.deletarPedido(id);
            ctx.status(204);
        });
    }
}
