package com.weiva.Service;

import java.util.List;

import com.weiva.Model.ProdutoModel;
import com.weiva.Repository.ProdutoRepository;

public class ProdutoService {
    ProdutoRepository produtoRepository = new ProdutoRepository();

    public ProdutoModel criarProduto(int id, String nome, String descricao, double preco_unitario, String caminho_galeria, int fk_farmacia_id, int fk_categoria_id){
        if (buscarPorNomeEFarmacia(nome, fk_farmacia_id) == null) {
            return produtoRepository.criarProduto(id, nome, descricao, preco_unitario, caminho_galeria, fk_farmacia_id, fk_categoria_id);
        } else {
            throw new RuntimeException("Produto ja cadastrado na farmácia.");
        }
    }

    public List<ProdutoModel> buscarTodosOsProdutos(){
        return produtoRepository.buscarTodosOsProdutos();
    }

    public ProdutoModel buscarPorNome(String nome){
        return produtoRepository.buscarPorNome(nome);
    }
    
    public ProdutoModel buscarPorNomeEFarmacia(String nome, int fk_farmacia_id){
        return produtoRepository.buscarPorNomeEFarmacia(nome, fk_farmacia_id);
    }

    public ProdutoModel buscarPorId(int id) {
        return produtoRepository.buscarPorId(id);
    }

    public ProdutoModel buscarPorFarmacia(int fk_farmacia_id){
        return produtoRepository.buscarPorFarmacia(fk_farmacia_id);
    }

    public ProdutoModel buscarPorCategoria(int fk_categoria_id){
        return produtoRepository.buscarPorCategoria(fk_categoria_id);
    }

    public ProdutoModel atualizarProduto(int id, String nome, String descricao, double preco_unitario, String caminho_galeria, int fk_farmacia_id, int fk_categoria_id){
        return produtoRepository.atualizarProduto(id, nome, descricao, preco_unitario, descricao, fk_farmacia_id, fk_categoria_id);
    }

    public ProdutoModel atualizarAtivo(int id, int ativo){
        return produtoRepository.atualizarAtivo(id, ativo);
    }

    public void deltarProduto(int id){
        produtoRepository.deletarProduto(id);
    }
}
