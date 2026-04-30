package com.weiva.Service;

import java.util.List;

import com.weiva.Model.ProdutoModel;
import com.weiva.Repository.BuscaRepository;

public class BuscaService {
    BuscaRepository buscaRepository = new BuscaRepository();
    public List<ProdutoModel> buscar(String termo){
        return buscaRepository.buscar(termo);
    }
}