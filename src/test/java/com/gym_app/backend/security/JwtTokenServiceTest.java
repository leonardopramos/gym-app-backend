package com.gym_app.backend.security;

import com.gym_app.backend.domain.TipoUsuario;
import com.gym_app.backend.domain.Usuario;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtTokenServiceTest {

    private static final String SECRET = "9a782b6c4d5e1f0a2b3c4d5e6f7a8b9c0d1e2f3a4b5c6d7e8f9a0b1c2d3e4f5a";
    private static final long EXPIRATION = 3600000; // 1 hour

    private JwtTokenService tokenService;

    @BeforeEach
    void setUp() {
        tokenService = new JwtTokenService(SECRET, EXPIRATION);
    }

    @Test
    @DisplayName("Deve gerar token JWT válido com claims do usuário")
    void deveGerarTokenComSucesso() {
        Usuario usuario = new Usuario("user-123", "João Silva", "joao@teste.com", "hash", TipoUsuario.ALUNO);

        String token = tokenService.gerarToken(usuario);

        assertThat(token).isNotBlank();
        assertThat(tokenService.validarToken(token)).isTrue();
        assertThat(tokenService.extrairEmail(token)).isEqualTo("joao@teste.com");

        Claims claims = tokenService.extrairClaims(token);
        assertThat(claims.get("id", String.class)).isEqualTo("user-123");
        assertThat(claims.get("nome", String.class)).isEqualTo("João Silva");
        assertThat(claims.get("tipo", String.class)).isEqualTo("ALUNO");
    }

    @Test
    @DisplayName("Deve retornar false para token inválido ou malformado")
    void deveRetornarFalseParaTokenInvalido() {
        assertThat(tokenService.validarToken("token-invalido-qualquer")).isFalse();
        assertThat(tokenService.validarToken("")).isFalse();
    }

    @Test
    @DisplayName("Deve retornar false para token expirado")
    void deveRetornarFalseParaTokenExpirado() {
        JwtTokenService expiredService = new JwtTokenService(SECRET, -1000);
        Usuario usuario = new Usuario("user-123", "João Silva", "joao@teste.com", "hash", TipoUsuario.ALUNO);

        String token = expiredService.gerarToken(usuario);

        assertThat(tokenService.validarToken(token)).isFalse();
    }
}
