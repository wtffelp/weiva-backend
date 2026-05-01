package com.weiva.Service;

import java.util.List;

import com.weiva.Model.CategoriaModel;
import com.weiva.Repository.CategoriaRepository;

public class CategoriaService {
    CategoriaRepository categoriaRepository = new CategoriaRepository();

    public CategoriaModel criarCategoria(String nome, String descricao, int fk_categoria_pai_id) {
        if (buscarPorNome(nome) == null) {
            return categoriaRepository.criarCategoria(nome, descricao, fk_categoria_pai_id);
        } else {
            throw new RuntimeException("Categoria ja cadastrada.");
        }
    }

    public List<CategoriaModel> buscarTodasAsCategorias(){
        return categoriaRepository.buscarTodasAsCategorias();
    }

    public List<CategoriaModel> buscarPorPai(int fk_categoria_pai_id){
        return categoriaRepository.buscarPorPai(fk_categoria_pai_id);
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
