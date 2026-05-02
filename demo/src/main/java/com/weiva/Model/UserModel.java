package com.weiva.Model;

import java.sql.Timestamp;

import com.weiva.Annotations.Exclude;

public class UserModel {
    private int id;
    @Exclude
    private String senha;
    private Timestamp criado_em;
    private String nome;
    private String email;
    @Exclude
    private String cpf;
    private String telefone;
    private String role;
    private int ativo;

    public UserModel () {}

    public Timestamp getCriado_em(){ return criado_em; }
    public void setCriado_em(Timestamp criado_em){ this.criado_em = criado_em; }

    public int getId(){ return id; }
    public void setId(int id) { this.id = id; }

    public String getSenha(){ return senha; }
    public void setSenha(String senha){ this.senha = senha; }

    public String getNome() { return nome; }
    public void setNome(String nome){ this.nome = nome; }

    public String getEmail(){ return email; }
    public void setEmail(String email){ this.email = email; }

    public String getCPF(){ return cpf; }
    public void setCPF(String cpf){ this.cpf = cpf; }

    public String getTelefone(){ return telefone; }
    public void setTelefone(String telefone){ this.telefone = telefone; }

    public String getRole(){ return role; }
    public void setRole(String role){ this.role = role; }

    public int getAtivo(){ return ativo; }
    public void setAtivo(int ativo){ this.ativo = ativo; }
}