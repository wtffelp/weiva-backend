package com.weiva.Service;

import java.util.List;

import com.weiva.Model.EnderecoModel;
import com.weiva.Repository.EnderecoRepository;

public class EnderecoService {
    EnderecoRepository enderecoRespository = new EnderecoRepository();

    public EnderecoModel criarEndereco(String logradouro, String numero, String bairro, String cidade, String estado, String cep, int fk_usuario_id) {
        return enderecoRespository.criarEndereco(logradouro, numero, bairro, cidade, estado, cep, fk_usuario_id);
    }

    public List<EnderecoModel> buscarPorUsuario(int fk_usuario_id){
        return enderecoRespository.buscarPorUsuario(fk_usuario_id);
    }

    public EnderecoModel buscarPorId(int id){
        return enderecoRespository.buscarPorId(id);
    }

    public EnderecoModel atualizarEndereco(int id, String logradouro, String numero, String bairro, String cidade, String estado, String cep) {
        return enderecoRespository.atualizarEndereco(id, logradouro, numero, bairro, cidade, estado, cep);
    }

    public void deletarEndereco(int id){
        enderecoRespository.deletarEndereco(id);
    }
}
