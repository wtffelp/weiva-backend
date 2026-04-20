package com.weiva.Service;

import java.util.List;

import com.weiva.Model.UserModel;
import com.weiva.Repository.UserRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class UserService {
    UserRepository userRepository = new UserRepository();

    public UserModel criarUsuario(String email, String nome, String cpf, String telefone, String senha, String role){
        if (buscarPorEmail(email) == null) {
            String hashedPassword = BCrypt.withDefaults().hashToString(12, senha.toCharArray());
            return userRepository.criarUsuario(email, nome, cpf, telefone, hashedPassword, role);
        } else {
            throw new RuntimeException("Usuario ja cadastrado");
        }
    }

    public List<UserModel> buscarTodosOsUsuarios(){
        return userRepository.buscarTodosOsUsuarios();
    }

    public UserModel buscarPorEmail(String email) {
        return userRepository.buscarPorEmail(email);
    }

    public UserModel buscarPorId(int id){
        return userRepository.buscarPorId(id);
    }

    public List<UserModel> buscarPorNome(String nome){
        return userRepository.buscarPorNome(nome);
    }

    public UserModel buscarPorCPF(String CPF){
        return userRepository.buscarPorCPF(CPF);
    }

    public UserModel buscarPorTelefone(String telefone){
        return userRepository.buscarPorTelefone(telefone);
    }

    public UserModel atualizarUsuario(int id, String telefone, String email, String nome, String senha){
        if (buscarPorId(id) != null) {
            String hashedPassword = BCrypt.withDefaults().hashToString(12, senha.toCharArray());
            return userRepository.autalizarUsuario(id, telefone, email, nome, hashedPassword);
        } else {
            throw new RuntimeException("Usuário não encontrado.");
        }
    }

    public UserModel atualizarAtivo(int id, int ativo){
        if (buscarPorId(id) != null) {
            return userRepository.atualizarAtivoUser(id, ativo);
        } else {
            throw new RuntimeException("Usuário não encontrado.");
        }
    }

    public UserModel atualizarRole(int id, String role){
        if (buscarPorId(id) != null) {
            return userRepository.atualizarRole(id, role);
        } else {
            throw new RuntimeException("Usuário não encontrado.");
        }
    }

    public void deletarUsuario(int id){
        userRepository.deletarUsuario(id);
    }
}
