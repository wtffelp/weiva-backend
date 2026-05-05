package com.weiva.Controller;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weiva.Annotations.AnnotationExclusionStrategy;
import com.weiva.Model.PedidoModel;
import com.weiva.Service.PedidoService;

import io.javalin.Javalin;

public class PedidoController {
    PedidoService pedidoService = new PedidoService();
    private static Gson gson = new GsonBuilder()
    .setExclusionStrategies(new AnnotationExclusionStrategy()).create();

    public void pedidoRoutes(Javalin app){
        app.get("/pedido/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            PedidoModel pedido = pedidoService.buscarPorId(id);
            ctx.result(gson.toJson(pedido));
        });
        app.get("/pedido", ctx -> {
            // farmacia, endereco, usuario, metodopagamento
            String farmacia = ctx.queryParam("farmacia");
            String endereco = ctx.queryParam("endereco");
            String usuario = ctx.queryParam("usuario");
            String metodo_pagamento = ctx.queryParam("metodo_pagamento");
            if (farmacia != null) {
                int farmaciaInt = Integer.parseInt(farmacia);
                List<PedidoModel> pedido = pedidoService.buscarPorFarmacia(farmaciaInt);
                ctx.result(gson.toJson(pedido));
            } else if (endereco != null){
                int enderecoInt = Integer.parseInt(endereco);
                List<PedidoModel> pedido = pedidoService.buscaPorEndereco(enderecoInt);
                ctx.result(gson.toJson(pedido));
            } else if (usuario != null){
                int usuarioInt = Integer.parseInt(usuario);
                List<PedidoModel> pedido = pedidoService.buscarPorUsuario(usuarioInt);
                ctx.result(gson.toJson(pedido));
            } else if (metodo_pagamento != null) {
                List<PedidoModel> pedido = pedidoService.buscarPorMetodoPagamento(metodo_pagamento);
                ctx.result(gson.toJson(pedido));
            } else {
                List<PedidoModel> pedido = pedidoService.buscarTodosOsPedidos();
                ctx.result(gson.toJson(pedido));
            }
        });
        app.post("/pedido", ctx -> {
            PedidoModel body = gson.fromJson(ctx.body(), PedidoModel.class);
            PedidoModel novo = pedidoService.criarPedido(
                body.getFk_usuario_id(),
                body.getFk_farmacia_id(),
                body.getFk_endereco_id(),
                body.getMetodo_pagamento(),
                body.getSubtotal(),
                body.getTaxa_entrega()
            );
            ctx.status(201).result(gson.toJson(novo));
        });

        app.put("/pedido/{id}/status", ctx -> {
            String role = ctx.attribute("role");
            if (!role.equals("admin") && !role.equals("super_admin")) {
                ctx.status(403).result("Acesso negado");
                return;
            }
            int id = Integer.parseInt(ctx.pathParam("id"));
            PedidoModel atualizarPedido = gson.fromJson(ctx.body(), PedidoModel.class);
            pedidoService.atualizarStatus(
                id,
                atualizarPedido.getStatus()
            );
        });
        app.delete("/pedido/{id}", ctx -> {
            String role = ctx.attribute("role");
            if (!role.equals("super_admin")) {
                ctx.status(403).result("Acesso negado");
                return;
            }
            int id = Integer.parseInt(ctx.pathParam("id"));
            pedidoService.deletarPedido(id);
            ctx.status(204);
        });
    }
}
