package com.weiva.Repository;

import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Jdbi;

import com.weiva.Config.Database;
import com.weiva.Model.ProdutoModel;

public class ProdutoRepository {
    Jdbi jdbi = Database.getJdbi();

    public ProdutoModel criarProduto(int id, String nome, String descricao, double preco_unitario, String caminho_galeria, int fk_farmacia_id, int fk_categoria_id){
        ProdutoModel produto = jdbi.withHandle(handle -> {
            int generatedId = handle.createUpdate(
                "INSERT INTO produto (nome, descricao, preco_unitario, caminho_galeria, fk_farmacia_id, fk_categoria_id) VALUES (:nome, :descricao, :preco_unitario, :caminho_galeria, :fk_farmacia_id, :fk_categoria_id)")
                .bind("nome", nome)
                .bind("descricao", descricao)
                .bind("preco_unitario", preco_unitario)
                .bind("caminho_galeria", caminho_galeria)
                .bind("fk_farmacia_id", fk_farmacia_id)
                .bind("fk_categoria_id", fk_categoria_id)
                .executeAndReturnGeneratedKeys("id")
                .mapTo(Integer.class)
                .one();
            return handle.createQuery("SELECT * FROM produto WHERE id = :id")
                .bind("id", generatedId)
                .mapToBean(ProdutoModel.class)
                .findOne()
                .orElse(null);
        });
        return produto;
    }

    public List<ProdutoModel> buscarTodosOsProdutos(){
        return jdbi.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM produto WHERE ativo = 1")
            .mapToBean(ProdutoModel.class)
            .list();
        });
    }

    public ProdutoModel buscarPorNome(String nome){
        ProdutoModel prod = jdbi.withHandle(handle -> {
            Optional<ProdutoModel> result = handle.createQuery("SELECT * FROM produto WHERE nome = :nome AND ativo = 1")
                .bind("nome", nome)
                .mapToBean(ProdutoModel.class)
                .findOne();
            return result.orElse(null);
        });
        return prod;
    }

    public ProdutoModel buscarPorNomeEFarmacia(String nome, int fk_farmacia_id) {
        return jdbi.withHandle(handle -> {
            Optional<ProdutoModel> result = handle.createQuery(
            "SELECT * FROM produto WHERE nome = :nome AND fk_farmacia_id = :fk_farmacia_id")
                .bind("nome", nome)
                .bind("fk_farmacia_id", fk_farmacia_id)
                .mapToBean(ProdutoModel.class)
                .findOne();
            return result.orElse(null);
        });
    }

    public ProdutoModel buscarPorId(int id){
        ProdutoModel prod = jdbi.withHandle(handle -> {
            Optional<ProdutoModel> result = handle.createQuery("SELECT * FROM produto WHERE id = :id")
                .bind("id", id)
                .mapToBean(ProdutoModel.class)
                .findOne();
            return result.orElse(null);
        });
        return prod;
    }

    public List<ProdutoModel> buscarPorFarmacia(int fk_farmacia_id){
        return jdbi.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM produto WHERE fk_farmacia_id = :fk_farmacia_id AND ativo = 1")
                .bind("fk_farmacia_id", fk_farmacia_id)
                .mapToBean(ProdutoModel.class)
                .list();
        });
    }

    public List<ProdutoModel> buscarPorCategoria(int fk_categoria_id){
        return jdbi.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM produto WHERE fk_categoria_id AND ativo = 1")
                .bind("fk_categoria_id", fk_categoria_id)
                .mapToBean(ProdutoModel.class)
                .list();
        });
    }

    public ProdutoModel atualizarProduto(int id, String nome, String descricao, double preco_unitario, String caminho_galeria, int fk_farmacia_id, int fk_categoria_id){
        jdbi.withHandle(handle -> {
            return handle.createUpdate("UPDATE produto SET nome = :nome, descricao = :descricao, preco_unitario = :preco_unitario, caminho_galeria = :caminho_galeria, fk_farmacia_id = :fk_farmacia_id, fk_categoria_id = :fk_categoria_id WHERE id = :id")
                .bind("nome", nome)
                .bind("descricao", descricao)
                .bind("preco_unitario", preco_unitario)
                .bind("caminho_galeria", caminho_galeria)
                .bind("fk_farmacia_id", fk_farmacia_id)
                .bind("fk_categoria_id", fk_categoria_id)
                .bind("id", id)
                .execute();
        });
        return buscarPorId(id);
    }

    public ProdutoModel atualizarAtivo(int id, int ativo) {
        jdbi.withHandle(handle -> {
        return handle.createUpdate("UPDATE produto SET ativo = :ativo WHERE id = :id")
            .bind("ativo", ativo)
            .bind("id", id)
            .execute();
        });
        return buscarPorId(id);
    }

    public void deletarProduto(int id){
        jdbi.withHandle(handle -> {
            return handle.createUpdate("DELETE FROM produto WHERE id = :id")
            .bind("id", id)
            .execute();
        });
    }
}
