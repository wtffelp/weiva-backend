package com.weiva.Repository;

import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Jdbi;

import com.weiva.Config.Database;
import com.weiva.Model.PedidoModel;

public class PedidoRepository {
    Jdbi jdbi = Database.getJdbi();
    public PedidoModel criarPedido(int fk_usuario_id, int fk_farmacia_id, int fk_endereco_id, String status, String metodo_pagamento, double subtotal, double taxa_entrega, double total){
        return jdbi.withHandle(handle -> {
            return handle.createUpdate("""
                    INSERT INTO pedido (fk_usuario_id, fk_farmacia_id, fk_endereco_id, status, metodo_pagamento, subtotal, taxa_entrega, total) VALUES (:fk_usuario_id, :fk_farmacia_id, :fk_endereco_id, :status, :metodo_pagamento, :subtotal, :taxa_entrega, :total)
                    """)
                .bind("fk_usuario_id", fk_usuario_id)
                .bind("fk_farmacia_id", fk_farmacia_id)
                .bind("fk_endereco_id", fk_endereco_id)
                .bind("status", status)
                .bind("metodo_pagamento", metodo_pagamento)
                .bind("subtotal", subtotal)
                .bind("taxa_entrega", taxa_entrega)
                .bind("total", total)
                .executeAndReturnGeneratedKeys("id")
                .mapToBean(PedidoModel.class)
                .findOne().orElse(null);
        }); 
    }
    public List<PedidoModel> buscarTodosOsPedidos(){
        return jdbi.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM pedido")
            .mapToBean(PedidoModel.class)
            .list();
        });
    }
    public PedidoModel buscarPorId(int id){
        PedidoModel pedido = jdbi.withHandle(handle -> {
            Optional<PedidoModel> result = handle.createQuery("SELECT * FROM pedido WHERE id = :id")
            .bind("id", id)
            .mapToBean(PedidoModel.class)
            .findOne();
            return result.orElse(null);
        });
        return pedido;
    }
    public List<PedidoModel> buscarPorFarmacia(int fk_farmacia_id){
        return jdbi.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM pedido WHERE fk_farmacia_id = :fk_farmacia_id")
            .bind("fk_farmacia_id", fk_farmacia_id)
            .mapToBean(PedidoModel.class)
            .list();
        });
    }
    public List<PedidoModel> buscarPorEndereco(int fk_endereco_id){
        return jdbi.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM pedido WHERE fk_endereco_id = :fk_endereco_id")
            .bind("fk_endereco_id", fk_endereco_id)
            .mapToBean(PedidoModel.class)
            .list();
        });
    }
    public List<PedidoModel> buscarPorUsuario(int fk_usuario_id){
        return jdbi.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM pedido WHERE fk_usuario_id = :fk_usuario_id")
            .bind("fk_usuario_id", fk_usuario_id)
            .mapToBean(PedidoModel.class)
            .list();
        });
    }
    public List<PedidoModel> buscarPorStatus(String status){
        return jdbi.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM pedido WHERE status = :status")
            .bind("status", status)
            .mapToBean(PedidoModel.class)
            .list();
        });
    }
    public List<PedidoModel> buscarPorMetodoPagamento(String metodo_pagamento){
        return jdbi.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM pedido WHERE metodo_pagamento = :metodo_pagamento")
            .bind("metodo_pagamento", metodo_pagamento)
            .mapToBean(PedidoModel.class)
            .list();
        });
    }
    public PedidoModel atualizarStatus(int id, String status){
        jdbi.withHandle(handle -> {
            return handle.createUpdate("UPDATE pedido SET status = :status WHERE id = :id")
                .bind("status", status)
                .bind("id", id)
                .execute();
        });
        return buscarPorId(id);
    }
    public void deletarPedido(int id){
        jdbi.withHandle(handle -> {
            return handle.createUpdate("DELETE FROM pedido WHERE id = :id")
            .bind("id", id)
            .execute();
        });
    }
}
