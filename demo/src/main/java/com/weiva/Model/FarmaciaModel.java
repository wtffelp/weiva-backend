package com.weiva.Model;

public class FarmaciaModel {
    private int id;
    private String nome;
    private double avalicao;
    private String imagem_perfil;
    private int fk_usuario_id;

    public FarmaciaModel () {}

    public int getId(){ return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public double getAvalicao() { return avalicao; }
    public void setAvaliacao(double avaliacao) { this.avalicao = avaliacao; }

    public String getImagem_perfil(){ return imagem_perfil; }
    public void setImagem_perfil(String imagem_perfil) { this.imagem_perfil = imagem_perfil; }

    public int getFk_usuario_id() { return fk_usuario_id; }
    public void setFk_usuario_id(int fk_usuario_id) { this.fk_usuario_id = fk_usuario_id; }

}
