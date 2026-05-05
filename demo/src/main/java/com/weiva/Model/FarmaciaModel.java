package com.weiva.Model;

import com.weiva.Annotations.Exclude;

public class FarmaciaModel {
    private int id;
    @Exclude
    private String cnpj;
    private String nome;
    private String descricao;
    private double avalicao;
    private String imagem_perfil;
    private int fk_usuario_id;
    private int ativo;

    public FarmaciaModel () {}

    public int getId(){ return id; }
    public void setId(int id) { this.id = id; }

    public String getCnpj() {return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getAvalicao() { return avalicao; }
    public void setAvaliacao(double avaliacao) { this.avalicao = avaliacao; }

    public String getImagem_perfil(){ return imagem_perfil; }
    public void setImagem_perfil(String imagem_perfil) { this.imagem_perfil = imagem_perfil; }

    public int getFk_usuario_id() { return fk_usuario_id; }
    public void setFk_usuario_id(int fk_usuario_id) { this.fk_usuario_id = fk_usuario_id; }

    public int getAtivo() { return ativo; }
    public void setAtivo(int ativo) { this.ativo = ativo; }

}
