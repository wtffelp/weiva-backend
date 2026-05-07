package com.weiva.Service;

import com.weiva.Model.ItemPedidoModel;
import com.weiva.Model.ProdutoModel;
import com.weiva.Repository.ItemPedidoRepository;

public class ItemPedidoService {
    ItemPedidoRepository itemPedidoRepository = new ItemPedidoRepository();
    PedidoService pedidoService = new PedidoService();
    ProdutoService produtoService = new ProdutoService();

    public ItemPedidoModel criarItemPedido(int fk_pedido_id, int fk_produto_id, int quantidade, double preco_unitario){
        if (pedidoService.buscarPorId(fk_pedido_id) == null) {
            throw new RuntimeException("Pedido não encontrado.");
        }

        if (quantidade <= 0) {
            throw new RuntimeException("Quantidade invalida.");
        }

        ProdutoModel produto = produtoService.buscarPorId(fk_produto_id);
        if (produto == null || produto.getAtivo() == 0) {
            throw new RuntimeException("Produto não encontrado ou desativado.");
        }
        
        preco_unitario = produto.getPreco_unitario();

        return itemPedidoRepository.criarItemPedido(fk_pedido_id, fk_produto_id, quantidade, preco_unitario);
    }

    public ItemPedidoModel buscarPorPedido(int fk_pedido_id){
        return itemPedidoRepository.buscarPorPedido(fk_pedido_id);
    }

    public ItemPedidoModel buscarPorId(int id){
        return itemPedidoRepository.buscarPorId(id);
    }

    public void deletarPedido(int id){
        itemPedidoRepository.deletarPedido(id);
    }
}
