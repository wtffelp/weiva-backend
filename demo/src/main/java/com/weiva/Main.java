package com.weiva;

import com.weiva.Controller.AuthController;
import com.weiva.Controller.BuscaController;
import com.weiva.Controller.CategoriaController;
import com.weiva.Controller.EnderecoController;
import com.weiva.Controller.FarmaciaController;
import com.weiva.Controller.ItemPedidoController;
import com.weiva.Controller.PedidoController;
import com.weiva.Controller.ProdutoController;
import com.weiva.Controller.UserController;
import com.weiva.JWT.AuthMiddleware;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Javalin javalin = Javalin.create( config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(rule -> {
                    // rule.allowHost("https://weiva.vercel.app");
                    rule.anyHost();
                });
            });
        }).start(7000);

        javalin.before(new AuthMiddleware());

        new AuthController().authRoutes(javalin);
        new BuscaController().buscarRoute(javalin);
        new UserController().userRoutes(javalin);
        new FarmaciaController().farmaciaRoutes(javalin);
        new ProdutoController().produtoRoutes(javalin);
        new CategoriaController().categoriaRoutes(javalin);
        new EnderecoController().enderecoRoutes(javalin);
        new PedidoController().pedidoRoutes(javalin);
        new ItemPedidoController().itemPedidoRoutes(javalin);
    }
}