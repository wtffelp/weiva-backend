package com.weiva.Model;

import java.sql.Timestamp;

public class PedidoModel {
    // id, fk_usuario_id, fk_farmacia_id, fk_endereco_id, status, metodo_pagamento, subtotal, taxa_entrega, total, data_criacao
    private int id;
    private int fk_usuario_id;
    private int fk_farmacia_id;
    private int fk_endereco_id;
    private String status;
    private String metodo_pagamento;
    private double subtotal;
    private double taxa_entrega;
    private double total;
    private Timestamp data_criacao;

    public PedidoModel () {}

    public int getId(){ return id; }
    public void setId(int id){ this.id = id; }

    public int getFk_usuario_id(){ return fk_usuario_id; }
    public void setFk_usuario_id(int fk_usuario_id){ this.fk_usuario_id = fk_usuario_id; }

    public int getFk_farmacia_id(){ return fk_farmacia_id; }
    public void setFk_farmacia_id(int fk_farmacia_id){ this.fk_farmacia_id = fk_farmacia_id; }

    public int getFk_endereco_id(){ return fk_endereco_id;}
    public void setFk_endereco_id(int fk_endereco_id){ this.fk_endereco_id = fk_endereco_id;}

    public String getStatus(){ return status; }
    public void setStatus(String status){ this.status = status; }

    public String getMetodo_pagamento(){ return metodo_pagamento; }
    public void setMetodo_pagamento(String metodo_pagamento){ this.metodo_pagamento = metodo_pagamento; }

    public double getSubtotal(){ return subtotal; }
    public void setSubtotal(double subtotal){ this.subtotal = subtotal; }

    public double getTaxa_entrega(){ return taxa_entrega; }
    public void setTaxa_entrega(double taxa_entrega){ this.taxa_entrega = taxa_entrega; }

    public double getTotal(){ return total; }
    public void setTotal(double total){ this.total = total; }

    public Timestamp getData_criacao(){ return data_criacao; }
    public void setData_criacao(Timestamp data_criacao){ this.data_criacao = data_criacao; }
}
