package com.weiva.Repository;

import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Jdbi;

import com.weiva.Config.Database;
import com.weiva.Model.FarmaciaModel;

public class FarmaciaRepository {
    Jdbi jdbi = Database.getJdbi();
    public FarmaciaModel criarFarmacia(int fk_usuario_id,String cnpj, String nome, String descricao, double avaliacao, String imagem_perfil) {
        jdbi.withHandle(handle -> {
            return handle.createUpdate("INSERT INTO farmacia (cnpj, nome, descricao, avaliacao, imagem_perfil, fk_usuario_id) VALUES (:cnpj, :nome, :descricao, :avaliacao, :imagem_perfil, :fk_usuario_id)")
            .bind("cnpj", cnpj)
            .bind("nome", nome)
            .bind("descricao", descricao)
            .bind("avaliacao", avaliacao)
            .bind("imagem_perfil", imagem_perfil)
            .bind("fk_usuario_id", fk_usuario_id)
            .execute();
        });
        FarmaciaModel farmaciaModel = buscarPorCnpj(cnpj);
        return farmaciaModel;
    }

    public List<FarmaciaModel> buscarTodasAsFarmacias(){
        return jdbi.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM farmacia WHERE ativo = 1")
            .mapToBean(FarmaciaModel.class)
            .list();
        });
    }

    public FarmaciaModel buscarPorId(int id) {
        FarmaciaModel farma = jdbi.withHandle(handle -> {
            Optional<FarmaciaModel> result = handle.createQuery("SELECT * FROM farmacia WHERE id = :id AND ativo = 1")
            .bind("id", id)
            .mapToBean(FarmaciaModel.class)
            .findOne();
            return result.orElse(null);
        });
        return farma;
    }

    public FarmaciaModel buscarPorCnpj(String cnpj) {
        FarmaciaModel user = jdbi.withHandle(handle -> {
            Optional<FarmaciaModel> result = handle.createQuery("SELECT * FROM farmacia WHERE cnpj = :cnpj AND ativo = 1")
            .bind("cnpj", cnpj)
            .mapToBean(FarmaciaModel.class)
            .findOne();
            return result.orElse(null);
        });
        return user;
    }

    public List<FarmaciaModel> buscarPorNome(String nome) {
        return jdbi.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM farmacia WHERE nome = :nome AND ativo = 1")
            .bind("nome", nome)
            .mapToBean(FarmaciaModel.class)
            .list();
        });
    }

    public List<FarmaciaModel> buscarPorAvalicao(double avaliacao) {
        return jdbi.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM farmacia WHERE avaliacao = :avaliacao AND ativo = 1")
            .bind("avaliacao", avaliacao)
            .mapToBean(FarmaciaModel.class)
            .list();
        });
    }
    
    public FarmaciaModel atualizarFarmacia(int id, String nome, String imagem_perfil) {
        jdbi.withHandle(handle -> {
            return handle.createUpdate("UPDATE farmacia SET nome = :nome, imagem_perfil = :imagem_perfil WHERE id = :id")
            .bind("id", id)
            .bind("nome", nome)
            .bind("imagem_perfil", imagem_perfil)
            .execute();
        });
        return buscarPorId(id);
    }

    public FarmaciaModel atualizarAtivo(int id, int ativo) {
        jdbi.withHandle(handle -> {
            return handle.createUpdate("UPDATE farmacia SET ativo = :ativo WHERE id = :id")
            .bind("ativo", ativo)
            .bind("id", id)
            .execute();
        });
        return buscarPorId(id);
    }

    public void deletarFarmacia(int id) {
        jdbi.withHandle(handle -> {
            return handle.createUpdate("DELETE FROM farmacia WHERE id = :id")
            .bind("id", id)
            .execute();
        });
    }
}
