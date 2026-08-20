package com.gym_app.backend.dto;
import com.gym_app.backend.domain.*;
public record UsuarioResponse(String id, String nome, String email, TipoUsuario tipo, boolean ativo) {
    public static UsuarioResponse from(Usuario u) { return new UsuarioResponse(u.getId(), u.getNome(), u.getEmail(), u.getTipo(), u.isAtivo()); }
}
