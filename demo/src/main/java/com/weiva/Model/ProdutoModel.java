package com.weiva.Model;

import java.sql.Timestamp;

public class ProdutoModel {
    private int id;
    private String nome;
    private String descricao;
    private double preco_unitario;
    private String caminho_galeria;
    private int ativo;
    private Timestamp criado_em;
    private int fk_farmacia_id;
    private int fk_categoria_id;

    public ProdutoModel () {};

    public int getId(){ return id; }
    public void setId(int id){ this.id = id; }

    public String getNome(){ return nome; }
    public void set(String nome){ this.nome = nome; }

    public String getDescricao(){ return descricao; }
    public void setDescricao(String descricao){ this.descricao = descricao; }

    public double getPreco_unitario(){ return preco_unitario; }
    public void setPreco_unitario(double preco_unitario){ this.preco_unitario = preco_unitario;}

    public String getCaminho_galeria(){ return caminho_galeria; }
    public void setCaminho_galeria(String caminho_galeria){ this.caminho_galeria = caminho_galeria;}

    public int getAtivo(){ return ativo; }
    public void setAtivo(int ativo){ this.ativo = ativo;}

    public Timestamp getCriado_em(){ return criado_em; }
    public void setCriado_em(Timestamp criado_em){ this.criado_em = criado_em;}

    public int getFk_farmacia_id(){ return fk_farmacia_id; }
    public void setFk_farmacia_id(int fk_farmacia_id){ this.fk_farmacia_id = fk_farmacia_id; }

    public int get(){ return fk_categoria_id; }
    public void setFk_categoria_id(int fk_categoria_id){ this.fk_categoria_id = fk_categoria_id;}
    
}
