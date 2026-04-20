package com.weiva.Service;

import java.util.List;

import com.weiva.Model.FarmaciaModel;
import com.weiva.Repository.FarmaciaRepository;

public class FarmaciaService {
    FarmaciaRepository farmaciaRepository = new FarmaciaRepository();

    public FarmaciaModel criarFarmacia(int fk_Usuario_id, String cnpj, String nome, String descricao, double avaliacao, String imagem_perfil) {
        if (buscarPorCnpj(cnpj) == null) {
            return farmaciaRepository.criarFarmacia(fk_Usuario_id, cnpj, nome, descricao, avaliacao, imagem_perfil);
        } else {
            throw new RuntimeException("CNPJ ja cadastrado.");
        }
    }

    public List<FarmaciaModel> buscarTodasAsFarmacias() {
        return farmaciaRepository.buscarTodasAsFarmacias();
    }

    public FarmaciaModel buscarPorId(int id) {
        return farmaciaRepository.buscarPorId(id);
    }

    public FarmaciaModel buscarPorCnpj(String cnpj){
        return farmaciaRepository.buscarPorCnpj(cnpj);
    }

    public List<FarmaciaModel> buscarPorNome(String nome) {
        return farmaciaRepository.buscarPorNome(nome);
    }

    public List<FarmaciaModel> buscarPorAvaliacao(double avaliacao) {
        return farmaciaRepository.buscarPorAvalicao(avaliacao);
    }

    public FarmaciaModel atualizarFarmacia(int id, String nome, String imagem_perfil) {
        if (buscarPorId(id) != null) {
            return farmaciaRepository.atualizarFarmacia(id, nome, imagem_perfil);
        } else {
            throw new RuntimeException("Farmácia não encontrada.");
        }
    }

    public FarmaciaModel atualizarAtivo(int id, int ativo){
        if (buscarPorId(id) != null) {
            return farmaciaRepository.atualizarAtivo(id, ativo);
        } else {
            throw new RuntimeException("Farmácia não encontrada");
        }
    }

    public void deletarFarmacia(int id) {
        farmaciaRepository.deletarFarmacia(id);
    }
}
