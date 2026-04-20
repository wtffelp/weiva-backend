package com.weiva.Repository;

import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Jdbi;

import com.weiva.Config.Database;
import com.weiva.Model.UserModel;

public class UserRepository {
    Jdbi jdbi = Database.getJdbi();
    public UserModel criarUsuario(String email, String nome, String cpf, String telefone, String senha, String role) {
        jdbi.withHandle(handle -> {
            return handle.createUpdate("""
                    INSERT INTO usuarios (email, nome, cpf, telefone, senha, role) VALUES (:email, :nome, :cpf, :telefone, :senha, :role)
                    """)
                .bind("email", email)
                .bind("nome", nome)
                .bind("cpf", cpf)
                .bind("telefone", telefone)
                .bind("senha", senha)
                .bind("role", role)
                .execute();
        });
        UserModel userModel = buscarPorEmail(email);
        return userModel;
    }

    public List<UserModel> buscarTodosOsUsuarios(){
        return jdbi.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM usuarios")
            .mapToBean(UserModel.class)
            .list();
        });
    }

    public UserModel buscarPorEmail(String email){
        UserModel user = jdbi.withHandle(handle -> {
            Optional<UserModel> result = handle.createQuery("SELECT * FROM usuarios WHERE email = :email")
            .bind("email", email)
            .mapToBean(UserModel.class)
            .findOne();
            return result.orElse(null);
        });
        return user;
    }

    public UserModel buscarPorId(int id){
        UserModel user = jdbi.withHandle(handle -> {
            Optional<UserModel> result = handle.createQuery("SELECT * FROM usuarios WHERE id = :id")
                .bind("id", id)
                .mapToBean(UserModel.class)
                .findOne();
            return result.orElse(null);
        });
        return user;
    }

    public List<UserModel> buscarPorNome(String nome){
        return jdbi.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM usuarios WHERE nome = :nome")
            .bind("nome", nome)
            .mapToBean(UserModel.class)
            .list();
        });
    }

    public UserModel buscarPorCPF(String CPF) {
        UserModel user = jdbi.withHandle(handle -> {
            Optional<UserModel> reuslt = handle.createQuery("SELECT * FROM usuarios WHERE cpf = :cpf")
                .bind("cpf", CPF)
                .mapToBean(UserModel.class)
                .findOne();
            return reuslt.orElse(null);
        });
        return user;
    }

    public UserModel buscarPorTelefone(String telefone){
        UserModel user = jdbi.withHandle(handle -> {
            Optional<UserModel> result = handle.createQuery("SELECT * FROM usuarios WHERE telefone = :telefone")
                .bind("telefone", telefone)
                .mapToBean(UserModel.class)
                .findOne();
            return result.orElse(null);
        });
        return user;
    }

    public UserModel atualizarAtivoUser(int id, int ativo){
        jdbi.withHandle(handle -> {
            return handle.createUpdate("UPDATE usuarios SET ativo = :ativo WHERE id = :id")
            .bind("id", id)
            .bind("ativo", ativo)
            .execute();
        });
        UserModel userModel = buscarPorId(id);
        return userModel;
    }

    public UserModel autalizarUsuario(int id, String telefone, String email, String nome, String senha){
        jdbi.withHandle(handle -> {
            return handle.createUpdate("UPDATE usuarios SET telefone = :telefone, email = :email, nome = :nome, senha = :senha WHERE id = :id")
                .bind("telefone", telefone)
                .bind("email", email)
                .bind("nome", nome)
                .bind("senha", senha)
                .bind("id", id)
                .execute();
        });
        UserModel userModel = buscarPorId(id);
        return userModel;
    }

    public UserModel atualizarRole(int id, String role){
        jdbi.withHandle(handle -> {
            return handle.createUpdate("UPDATE usuarios SET role = :role WHERE id = :id")
            .bind("role", role)
            .bind("id", id)
            .execute();
        });
        UserModel userModel = buscarPorId(id);
        return userModel;
    }

    public void deletarUsuario(int id){
        jdbi.withHandle(handle -> {
            return handle.createUpdate("DELETE FROM usuarios WHERE id = :id")
                .bind("id", id)
                .execute();
        });
    }
}
