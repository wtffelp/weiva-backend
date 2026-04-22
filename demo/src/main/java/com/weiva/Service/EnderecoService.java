package com.weiva.Service;

import java.util.List;

import com.weiva.Model.EnderecoModel;
import com.weiva.Repository.EnderecoRespository;

public class EnderecoService {
    EnderecoRespository enderecoRespository = new EnderecoRespository();

    public EnderecoModel criarEndereco(int id, String logradouro, String numero, String bairro, String cidade, String estado, String cep, int fk_usuario_id) {
        return enderecoRespository.criarEndereco(id, logradouro, numero, bairro, cidade, estado, cep, fk_usuario_id);
    }

    public List<EnderecoModel> buscarPorUsuarios(int fk_usuario_id){
        return enderecoRespository.buscarPorUsuario(fk_usuario_id);
    }

    public EnderecoModel buscarPorId(int id){
        return enderecoRespository.buscarPorId(id);
    }

    public EnderecoModel atualizarEndereco(int id, String logradouro, String numero, String bairro, String cidade, String cep) {
        return enderecoRespository.atualizarEndereco(id, logradouro, numero, bairro, cidade, cep);
    }

    public void deletarEndereco(int id){
        enderecoRespository.deletarEndereco(id);
    }
}
