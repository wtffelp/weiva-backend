package com.weiva.Model;

public class CategoriaModel {
    private int id;
    private String nome;
    private String descricao;
    private int fk_categoria_pai_id;

    public CategoriaModel() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getFk_categoria_pai_id(){
        return fk_categoria_pai_id;
    }

    public void setFk_categoria_pai_id(int fk_categoria_pai_id) {
        this.fk_categoria_pai_id = fk_categoria_pai_id;
    }
}
