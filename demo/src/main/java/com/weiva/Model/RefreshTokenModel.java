package com.weiva.Model;

import java.sql.Timestamp;

public class RefreshTokenModel {
    private int id;
    private String token;
    private int fk_usuario_id;
    private Timestamp criado_em;
    private Timestamp expira_em;
    private int ativo;

    public RefreshTokenModel () {}

    public int getId(){ return id; }
    public void setId(int id){ this.id = id; }

    public String getToken(){ return token; }
    public void setToken(String token){ this.token = token; }

    public int getFk_usuario_id(){ return fk_usuario_id; }
    public void setFk_usuario_id(int fk_usuario_id){ this.fk_usuario_id = fk_usuario_id; }

    public Timestamp getCriado_em(){ return criado_em; }
    public void setCriado_em(Timestamp criado_em){ this.criado_em = criado_em; }

    public Timestamp getExpira_em(){ return expira_em; }
    public void setExpira_em(Timestamp expira_em){ this.expira_em = expira_em; }

    public int getAtivo(){ return ativo; }
    public void setAtivo(int ativo){ this.ativo = ativo; }
}
