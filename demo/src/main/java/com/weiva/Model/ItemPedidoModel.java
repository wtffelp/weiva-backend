package com.weiva.Model;

public class ItemPedidoModel {
    // id, fk_pedido_id, fk_produto_id, quantidade, preco_unitario
    private int id;
    private int fk_pedido_id;
    private int fk_produto_id;
    private int quantidade;
    private double preco_unitario;

    ItemPedidoModel () {}

    public int getId(){ return id; }
    public void setId(int id){ this.id = id; }

    public int getFk_pedido_id(){ return fk_pedido_id; }
    public void setFk_pedido_id(int fk_pedido_id){ this.fk_pedido_id = fk_pedido_id; }

    public int getFk_produto_id(){ return fk_produto_id; }
    public void setFk_produto_id(int fk_produto_id){ this.fk_produto_id = fk_produto_id; }

    public int getQuantidade(){ return quantidade;}
    public void setQuantidade(int quantidade){ this.quantidade = quantidade; }

    public double getPreco_unitario(){ return preco_unitario; }
    public void setPreco_unitario(double preco_unitario){ this.preco_unitario = preco_unitario; }
    
}
