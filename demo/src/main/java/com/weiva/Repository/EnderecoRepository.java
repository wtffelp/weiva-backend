package com.weiva.Repository;

import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Jdbi;

import com.weiva.Config.Database;
import com.weiva.Model.EnderecoModel;

public class EnderecoRepository {
    Jdbi jdbi = Database.getJdbi();
    public EnderecoModel criarEndereco(String logradouro, String numero, String bairro, String cidade, String estado, String cep, int fk_usuario_id){
        return jdbi.withHandle(handle -> {
            return handle.createUpdate("INSERT INTO endereco (logradouro, numero, bairro, cidade, estado, cep, fk_usuario_id) VALUES (:logradouro, :numero, :bairro, :cidade, :estado, :cep, :fk_usuario_id)")
                .bind("logradouro", logradouro)
                .bind("numero", numero)
                .bind("bairro", bairro)
                .bind("cidade", cidade)
                .bind("estado", estado)
                .bind("cep", cep)
                .bind("fk_usuario_id", fk_usuario_id)
                .executeAndReturnGeneratedKeys("id")
                .mapToBean(EnderecoModel.class)
                .findOne()
                .orElse(null);
        });
    }

    public List<EnderecoModel> buscarPorUsuario(int fk_usuario_id){
        return jdbi.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM endereco WHERE fk_usuario_id = :fk_usuario_id")
                .bind("fk_usuario_id", fk_usuario_id)
                .mapToBean(EnderecoModel.class)
                .list();
        });
    }

    public EnderecoModel buscarPorId(int id){
        EnderecoModel ende = jdbi.withHandle(handle -> {
            Optional<EnderecoModel> result = handle.createQuery("SELECT * FROM endereco WHERE id = :id")
                .bind("id", id)
                .mapToBean(EnderecoModel.class)
                .findOne();
            return result.orElse(null);
        });
        return ende;
    }
    
    public EnderecoModel atualizarEndereco(int id, String logradouro, String numero, String bairro, String cidade, String estado, String cep){
        jdbi.withHandle(handle -> {
            return handle.createUpdate("UPDATE endereco SET logradouro = :logradouro, numero = :numero, bairro = :bairro, cidade = :cidade, cep = :cep WHERE id = :id")
                .bind("logradouro", logradouro)
                .bind("numero", numero)
                .bind("bairro", bairro)
                .bind("cidade", cidade)
                .bind("estado", estado)
                .bind("cep", cep)
                .bind("id", id)
                .execute();
        });
        return buscarPorId(id);
    }

    public void deletarEndereco(int id){
        jdbi.withHandle(handle -> {
            return handle.createUpdate("DELETE FROM endereco WHERE id = :id")
            .bind("id", id)
            .execute();
        });
    }

}
