package com.gym_app.backend.dto;

public record LoginResponse(
        String token,
        String tipoToken,
        UsuarioResponse usuario
) {
    public static LoginResponse of(String token, UsuarioResponse usuario) {
        return new LoginResponse(token, "Bearer", usuario);
    }
}
