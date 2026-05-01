package com.weiva.Repository;

import java.util.List;

import org.jdbi.v3.core.Jdbi;

import com.weiva.Config.Database;
import com.weiva.Model.CategoriaModel;

public class CategoriaRepository {
    Jdbi jdbi = Database.getJdbi();
    public CategoriaModel criarCategoria(String nome, String descricao, int fk_categoria_pai_id) {
        jdbi.withHandle(handle -> {
            return handle.createUpdate("INSERT INTO categoria (nome, descricao, fk_categoria_pai_id) VALUES (:nome, :descricao, :fk_categoria_pai_id)")
            .bind("nome", nome)
            .bind("descricao", descricao)
            .bind("fk_categoria_pai_id", fk_categoria_pai_id)
            .execute();
        });
        CategoriaModel categoriaModel = buscarPorNome(nome);
        return categoriaModel;
    }

    public List<CategoriaModel> buscarTodasAsCategorias(){
        return jdbi.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM categoria")
            .mapToBean(CategoriaModel.class)
            .list();
        });
    }

    public List<CategoriaModel> buscarPorPai(int fk_categoria_pai_id){
        return (List<CategoriaModel>) jdbi.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM categoria WHERE fk_categoria_pai_id = :fk_categoria_pai_id")
            .mapToBean(CategoriaModel.class)
            .list();
        });
    }

    public CategoriaModel buscarPorId(int id) {
        CategoriaModel cat = jdbi.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM categoria WHERE id = :id")
            .bind("id", id)
            .mapToBean(CategoriaModel.class)
            .findOne()
            .orElse(null);
        });
        return cat;
    }

    public CategoriaModel buscarPorNome(String nome) {
        CategoriaModel cat = jdbi.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM categoria WHERE nome = :nome")
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
                return handle.createUpdate("DELETE FROM categoria WHERE id = :id")
                .bind("id", id)
                .execute();
            });
        }
        return cat;
    }
    
}
