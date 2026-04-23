package com.weiva.Repository;

import java.util.List;

import org.jdbi.v3.core.Jdbi;

import com.weiva.Config.Database;
import com.weiva.Model.CategoriaModel;

public class CategoriaRepository {
    Jdbi jdbi = Database.getJdbi();
    public CategoriaModel criarCategoria(String nome, String descricao) {
        jdbi.withHandle(handle -> {
            return handle.createUpdate("INSERT INTO categorias (nome, descricao) VALUES (:nome, :descricao)")
            .bind("nome", nome)
            .bind("descricao", descricao)
            .execute();
        });
        CategoriaModel categoriaModel = buscarPorNome(nome);
        return categoriaModel;
    }

    public List<CategoriaModel> buscarTodasAsCategorias(){
        return jdbi.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM categorias")
            .mapToBean(CategoriaModel.class)
            .list();
        });
    }

    public CategoriaModel buscarPorId(int id) {
        CategoriaModel cat = jdbi.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM categorias WHERE id = :id")
            .bind("id", id)
            .mapToBean(CategoriaModel.class)
            .findOne()
            .orElse(null);
        });
        return cat;
    }

    public CategoriaModel buscarPorNome(String nome) {
        CategoriaModel cat = jdbi.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM categorias WHERE nome = :nome")
            .bind("nome", nome)
            .mapToBean(CategoriaModel.class)
            .findOne()
            .orElse(null);
        });
        return cat;
    }

    public CategoriaModel deletarCategoria(int id) {
        CategoriaModel cat = buscarPorId(id);
        if (cat != null) {
            jdbi.withHandle(handle -> {
                return handle.createUpdate("DELETE FROM categorias WHERE id = :id")
                .bind("id", id)
                .execute();
            });
        }
        return cat;
    }
    
}
