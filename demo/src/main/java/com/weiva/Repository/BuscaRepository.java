package com.weiva.Repository;

import java.util.List;

import org.jdbi.v3.core.Jdbi;

import com.weiva.Config.Database;
import com.weiva.Model.ProdutoModel;

public class BuscaRepository {
    Jdbi jdbi = Database.getJdbi();
    public List<ProdutoModel> buscar(String termo){
        String like = "%" + termo + "%";
        return jdbi.withHandle(handle -> {
            return handle.createQuery("""
                    SELECT p.* FROM produto p
                    INNER JOIN farmacia f ON p.fk_farmacia_id = f.id
                    WHERE (p.nome ILIKE :like 
                    OR p.descricao ILIKE :like 
                    OR f.nome ILIKE :like)
                    AND p.ativo = 1
                    """)
                .bind("like", like)
                .mapToBean(ProdutoModel.class)
                .list();
        });
    }
}
