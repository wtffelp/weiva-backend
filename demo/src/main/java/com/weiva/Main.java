package com.weiva;

import com.weiva.Controller.AuthController;
import com.weiva.Controller.CategoriaController;
import com.weiva.Controller.EnderecoController;
import com.weiva.Controller.FarmaciaController;
import com.weiva.Controller.ProdutoController;
import com.weiva.Controller.UserController;
import com.weiva.JWT.AuthMiddleware;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Javalin javalin = Javalin.create().start(7000);
        javalin.before(new AuthMiddleware());

        new AuthController().authRoutes(javalin);
        new UserController().userRoutes(javalin);
        new FarmaciaController().farmaciaRoutes(javalin);
        new ProdutoController().produtoRoutes(javalin);
        new CategoriaController().categoriaRoutes(javalin);
        new EnderecoController().enderecoRoutes(javalin);
    }
}