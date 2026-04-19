package com.weiva.Repository;

import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Jdbi;

import com.weiva.Config.Database;
import com.weiva.Model.FarmaciaModel;

public class FarmaciaRepository {
    Jdbi jdbi = Database.getJdbi();
    public FarmaciaModel criarFarmacia(int id,String cpnj, String nome, String descricao, double avaliacao, String imagem_perfil) {
        jdbi.withHandle(handle -> {
            return handle.createUpdate("INSERT INTO farmacias (cnpj, nome, descricao, avaliacao, imagem_perfil) VALUES (:cpnj, :nome, :descricao, :avaliacao, :imagem_perfil")
            .bind("cpnj", cpnj)
            .bind("nome", nome)
            .bind("descricao", descricao)
            .bind("avaliacao", avaliacao)
            .bind("imagem_perfil", imagem_perfil)
            .execute();
        });
        FarmaciaModel farmaciaModel = buscarPorCpnj(cpnj);
        return farmaciaModel;
    }

    public List<FarmaciaModel> buscarTodasAsFarmacias(){
        return jdbi.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM farmacias")
            .mapToBean(FarmaciaModel.class)
            .list();
        });
    }

    public FarmaciaModel buscarPorId(int id) {
        FarmaciaModel farma = jdbi.withHandle(handle -> {
            Optional<FarmaciaModel> result = handle.createQuery("SELECT * FROM farmacias WHERE id = :id")
            .bind("id", id)
            .mapToBean(FarmaciaModel.class)
            .findOne();
            return result.orElse(null);
        });
        return farma;
    }

    public FarmaciaModel buscarPorCpnj(String cpnj) {
        FarmaciaModel user = jdbi.withHandle(handle -> {
            Optional<FarmaciaModel> result = handle.createQuery("SELECT * FROM farmacias WHERE cnpj = :cnpj")
            .bind("cpnj", cpnj)
            .mapToBean(FarmaciaModel.class)
            .findOne();
            return result.orElse(null);
        });
        return user;
    }

    public List<FarmaciaModel> buscarPorNome(String nome) {
        return jdbi.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM farmacias WHERE nome = :nome")
            .bind("nome", nome)
            .mapToBean(FarmaciaModel.class)
            .list();
        });
    }

    public List<FarmaciaModel> buscarPorAvalicao(double avaliacao) {
        return jdbi.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM farmacias WHERE avaliacao = :avaliacao")
            .bind("avaliacao", avaliacao)
            .mapToBean(FarmaciaModel.class)
            .list();
        });
    }
}
