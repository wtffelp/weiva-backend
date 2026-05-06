package com.weiva.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.weiva.Model.RefreshTokenModel;
import com.weiva.Repository.RefreshTokenRepository;

public class RefreshTokenService {
    RefreshTokenRepository refreshTokenRepository = new RefreshTokenRepository();
    
    public RefreshTokenModel criarToken(int fk_usuario_id) {
        String token = UUID.randomUUID().toString() + UUID.randomUUID().toString();
        Timestamp expira_em = Timestamp.valueOf(LocalDateTime.now().plusDays(30));
        return refreshTokenRepository.criarToken(token, fk_usuario_id, expira_em);
    }
    public RefreshTokenModel validarToken(String token) {
        RefreshTokenModel refreshToken = refreshTokenRepository.buscarPorToken(token);
        if (refreshToken == null) {
            throw new RuntimeException("Token inválido.");
        }
        if (refreshToken.getAtivo() == 0) {
            throw new RuntimeException("Token inválido.");
        }
        if (refreshToken.getExpira_em().before(new Timestamp(System.currentTimeMillis()))) {
            throw new RuntimeException("Token expirado.");
        }
        return refreshToken;
    }
    public RefreshTokenModel buscaPorToken(String token){
        return refreshTokenRepository.buscarPorToken(token);
    }
    public List<RefreshTokenModel> buscarPorUsuario(int fk_usuario_id){
        return refreshTokenRepository.buscarPorUsuario(fk_usuario_id);
    }
    public void atualizarAtivo(String token, int ativo){
        refreshTokenRepository.atualizarAtivo(token, ativo);
    }
}
