package com.weiva;

import com.weiva.Controller.UserController;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Javalin javalin = Javalin.create().start(7000);
        UserController userController = new UserController();
        userController.userRoutes(javalin);
    }
}