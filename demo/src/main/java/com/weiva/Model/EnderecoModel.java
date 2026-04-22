package com.weiva.Model;

public class EnderecoModel {
    private int id;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;;
    private String cep;
    private int fk_Usuario_id;

    public EnderecoModel(int id, String logradouro, String numero, String bairro, String cidade, String estado, String cep, int fk_Usuario_id) {
        this.id = id;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.fk_Usuario_id = fk_Usuario_id;
    }

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

    public int getFk_Usuario_id() { return fk_Usuario_id; }
    public void setFk_Usuario_id(int fk_Usuario_id) { this.fk_Usuario_id = fk_Usuario_id; }
    
}
