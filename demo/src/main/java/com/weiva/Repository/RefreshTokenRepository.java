package com.weiva.Repository;

import java.util.List;
import java.util.Optional;

import java.sql.Timestamp;

import org.jdbi.v3.core.Jdbi;

import com.weiva.Config.Database;
import com.weiva.Model.RefreshTokenModel;

public class RefreshTokenRepository {
    Jdbi jdbi = Database.getJdbi();

    public RefreshTokenModel criarToken(String token, int fk_usuario_id, Timestamp expira_em){
        return jdbi.withHandle(handle -> {
            handle.createUpdate("INSERT INTO refresh_token (token, fk_usuario_id, expira_em) VALUES (:token, :fk_usuario_id, :expira_em)")
                .bind("token", token)
                .bind("fk_usuario_id", fk_usuario_id)
                .bind("expira_em", expira_em)
                .execute();

                return handle.createQuery("SELECT * FROM refresh_token WHERE token = :token")
                .bind("token", token)
                .mapToBean(RefreshTokenModel.class)
                .findOne()
                .orElse(null);
        });
    }
    public RefreshTokenModel buscarPorToken(String token){
        RefreshTokenModel tok = jdbi.withHandle(handle -> {
            Optional<RefreshTokenModel> result = handle.createQuery("SELECT * FROM refresh_token WHERE token = :token")
            .bind("token", token)
            .mapToBean(RefreshTokenModel.class)
            .findOne();
            return result.orElse(null);
        });
        return tok;
    }
    public List<RefreshTokenModel> buscarPorUsuario(int fk_usuario_id){
        return jdbi.withHandle(handle ->
            handle.createQuery("SELECT * FROM refresh_token WHERE fk_usuario_id = :fk_usuario_id")
            .bind("fk_usuario_id", fk_usuario_id)
            .mapToBean(RefreshTokenModel.class)
            .list()
        );
    }
    public void atualizarAtivo(String token, int ativo){
        jdbi.withHandle(handle -> 
            handle.createUpdate("UPDATE refresh_token SET ativo = :ativo WHERE token = :token")
            .bind("ativo", ativo)
            .bind("token", token)
            .execute()
        );
    }
}
