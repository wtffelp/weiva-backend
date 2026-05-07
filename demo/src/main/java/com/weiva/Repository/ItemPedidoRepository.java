package com.weiva.Repository;

import java.util.Optional;

import org.jdbi.v3.core.Jdbi;

import com.weiva.Config.Database;
import com.weiva.Model.ItemPedidoModel;

public class ItemPedidoRepository {
    Jdbi jdbi = Database.getJdbi();
    public ItemPedidoModel criarItemPedido(int fk_pedido_id, int fk_produto_id, int quantidade, double preco_unitario){
        return jdbi.withHandle(handle -> {
            return handle.createUpdate("INSERT INTO item_pedido (fk_pedido_id, fk_produto_id, quantidade, preco_unitario) VALUES (:fk_pedido_id, :fk_produto_id, :quantidade, :preco_unitario)")
            .bind("fk_pedido_id", fk_pedido_id)
            .bind("fk_produto_id", fk_produto_id)
            .bind("quantidade", quantidade)
            .bind("preco_unitario", preco_unitario)
            .executeAndReturnGeneratedKeys("id")
            .mapToBean(ItemPedidoModel.class)
            .findOne().orElse(null);
        });
    }
    public ItemPedidoModel buscarPorPedido(int fk_pedido_id){
        ItemPedidoModel item = jdbi.withHandle(handle -> {
            Optional<ItemPedidoModel> result = handle.createQuery("SELECT * FROM item_pedido WHERE fk_pedido_id = :fk_pedido_id")
            .bind("fk_pedido_id", fk_pedido_id)
            .mapToBean(ItemPedidoModel.class)
            .findOne();
            return result.orElse(null);
        });
        return item;
    }
    public ItemPedidoModel buscarPorId(int id){
        ItemPedidoModel item = jdbi.withHandle(handle -> {
            Optional<ItemPedidoModel> result = handle.createQuery("SELECT * FROM item_pedido WHERE id = :id")
            .bind("id", id)
            .mapToBean(ItemPedidoModel.class)
            .findOne();
            return result.orElse(null);
        });
        return item;
    }
    public void deletarPedido(int id){
        jdbi.withHandle(handle -> {
            return handle.createUpdate("DELETE FROM item_pedido WHERE id = :id")
            .bind("id", id)
            .execute();
        });
    }
}
