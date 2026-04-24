package com.weiva.Config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jdbi.v3.core.Jdbi;

public class Database {
    private static Jdbi jdbi;

    public static Jdbi getJdbi(){
        if (jdbi == null) {
            try{
                Properties props = new Properties();
                InputStream input = Database.class.getClassLoader().getResourceAsStream("db.properties");
                props.load(input);
                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.password");
                jdbi = Jdbi.create(url, user, password);
            } catch (IOException e) {
                System.out.println("Erro ao conectar com o banco: " + e.getMessage());
            }
            return jdbi;
        }
        return jdbi;
    }
}
