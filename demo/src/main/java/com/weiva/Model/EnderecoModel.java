package com.weiva.Model;

public class EnderecoModel {
    private int id;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;;
    private String cep;
    private int fk_usuario_id;

    public EnderecoModel () {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public int getFk_usuario_id() { return fk_usuario_id; }
    public void setFk_usuario_id(int fk_usuario_id) { this.fk_usuario_id = fk_usuario_id; }
    
}
