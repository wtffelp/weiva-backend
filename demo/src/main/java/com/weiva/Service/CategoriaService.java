package com.weiva.Service;

import java.util.List;

import com.weiva.Model.CategoriaModel;
import com.weiva.Repository.CategoriaRepository;

public class CategoriaService {
    CategoriaRepository categoriaRepository = new CategoriaRepository();

    public CategoriaModel criarCategoria(String nome, String descricao) {
        if (buscarPorNome(nome) == null) {
            return categoriaRepository.criarCategoria(nome, descricao);
        } else {
            throw new RuntimeException("Categoria ja cadastrada.");
        }
    }

    public List<CategoriaModel> buscarTodasAsCategorias(){
        return buscarTodasAsCategorias();
    }

    public CategoriaModel buscarPorId(int id) {
        return categoriaRepository.buscarPorId(id);
    }

    public CategoriaModel buscarPorNome(String nome) {
        return categoriaRepository.buscarPorNome(nome);
    }

    public CategoriaModel deletarCategoria(int id) {
        return categoriaRepository.deletarCategoria(id);
    }
    
}
