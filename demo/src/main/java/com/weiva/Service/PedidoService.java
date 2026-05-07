package com.weiva.Service;

import java.util.List;

import com.weiva.Model.EnderecoModel;
import com.weiva.Model.FarmaciaModel;
import com.weiva.Model.PedidoModel;
import com.weiva.Repository.PedidoRepository;

public class PedidoService {
    private UserService userService = new UserService();
    private EnderecoService enderecoService = new EnderecoService();
    private FarmaciaService farmaciaService = new FarmaciaService();
    PedidoRepository pedidoRepository = new PedidoRepository();

    public PedidoModel criarPedido(int fk_usuario_id, int fk_farmacia_id, int fk_endereco_id, String metodo_pagamento, double subtotal, double taxa_entrega){
        try {
            System.out.println("Creating order - user: " + fk_usuario_id + ", farmacia: " + fk_farmacia_id + ", endereco: " + fk_endereco_id);
            
            // usuario existe
            if (userService.buscarPorId(fk_usuario_id) == null) {
                throw new RuntimeException("Usuário não encontrado: " + fk_usuario_id);
            }
            System.out.println("User found");
            
            // farmacia existe e ta ativa
            FarmaciaModel farmacia = farmaciaService.buscarPorId(fk_farmacia_id);
            if (farmacia == null) {
                throw new RuntimeException("Farmacia não encontrada: " + fk_farmacia_id);
            }
            if (farmacia.getAtivo() == 0) {
                throw new RuntimeException("Farmacia desativada: " + fk_farmacia_id);
            }
            System.out.println("Farmacia found and active");
            
            // se o endereco existe e pertence ao usuario
            EnderecoModel endereco = enderecoService.buscarPorId(fk_endereco_id);
            if (endereco == null) {
                throw new RuntimeException("Endereço não encontrado: " + fk_endereco_id);
            }
            if (endereco.getFk_usuario_id() != fk_usuario_id) {
                throw new RuntimeException("Endereço " + fk_endereco_id + " não pertence ao usuário " + fk_usuario_id);
            }
            System.out.println("Endereco found and belongs to user");
            
            // metodo de pagamento valido
            List<String> metodosValidos = List.of("pix", "cartao", "dinheiro");
            if (!metodosValidos.contains(metodo_pagamento)) {
                throw new RuntimeException("Metodo de pagamento inválido: " + metodo_pagamento);
            }
            System.out.println("Payment method valid");
            
            // calcular o total
            double total = subtotal + taxa_entrega;
            System.out.println("Subtotal: " + subtotal + ", Taxa: " + taxa_entrega + ", Total: " + total);
            
            return pedidoRepository.criarPedido(fk_usuario_id, fk_farmacia_id, fk_endereco_id, "pendente", metodo_pagamento, subtotal, taxa_entrega, total);
        } catch (Exception e) {
            System.err.println("Error in criarPedido: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    public List<PedidoModel> buscarTodosOsPedidos(){
        return pedidoRepository.buscarTodosOsPedidos();
    }
    public PedidoModel buscarPorId(int id){
        return pedidoRepository.buscarPorId(id);
    }
    public List<PedidoModel> buscarPorFarmacia(int fk_farmacia_id){
        return pedidoRepository.buscarPorFarmacia(fk_farmacia_id);
    }
    public List<PedidoModel> buscaPorEndereco(int fk_endereco_id){
        return pedidoRepository.buscarPorEndereco(fk_endereco_id);
    }
    public List<PedidoModel> buscarPorUsuario(int fk_usuario_id){
        return pedidoRepository.buscarPorUsuario(fk_usuario_id);
    }
    public List<PedidoModel> buscarPorMetodoPagamento(String metodo_pagamento){
        return pedidoRepository.buscarPorMetodoPagamento(metodo_pagamento);
    }
    public PedidoModel atualizarStatus(int id, String status){
        return pedidoRepository.atualizarStatus(id, status);
    }
    public void deletarPedido(int id){
        pedidoRepository.deletarPedido(id);
    }
}
