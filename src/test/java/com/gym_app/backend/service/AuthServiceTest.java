package com.gym_app.backend.service;

import com.gym_app.backend.domain.TipoUsuario;
import com.gym_app.backend.domain.Usuario;
import com.gym_app.backend.dto.LoginRequest;
import com.gym_app.backend.dto.LoginResponse;
import com.gym_app.backend.dto.UsuarioRequest;
import com.gym_app.backend.dto.UsuarioResponse;
import com.gym_app.backend.exception.BusinessException;
import com.gym_app.backend.repository.UsuarioRepository;
import com.gym_app.backend.security.JwtTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenService jwtTokenService;

    @Mock
    private UsuarioService usuarioService;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService(usuarioRepository, passwordEncoder, jwtTokenService, usuarioService);
    }

    @Test
    @DisplayName("Deve realizar login com sucesso e retornar token e dados do usuário")
    void deveRealizarLoginComSucesso() {
        Usuario usuario = new Usuario("user-1", "João Silva", "joao@teste.com", "$2a$10$encodedPassword", TipoUsuario.ALUNO);
        LoginRequest request = new LoginRequest("joao@teste.com", "senha123");

        when(usuarioRepository.findByEmail(request.email())).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("senha123", "$2a$10$encodedPassword")).thenReturn(true);
        when(jwtTokenService.gerarToken(usuario)).thenReturn("jwt.token.valido");

        LoginResponse response = authService.login(request);

        assertThat(response).isNotNull();
        assertThat(response.token()).isEqualTo("jwt.token.valido");
        assertThat(response.tipoToken()).isEqualTo("Bearer");
        assertThat(response.usuario().id()).isEqualTo("user-1");
        assertThat(response.usuario().nome()).isEqualTo("João Silva");
        assertThat(response.usuario().email()).isEqualTo("joao@teste.com");
        assertThat(response.usuario().tipo()).isEqualTo(TipoUsuario.ALUNO);
    }

    @Test
    @DisplayName("Deve lançar BadCredentialsException quando e-mail não existir")
    void deveLancarExcecaoQuandoEmailNaoExiste() {
        LoginRequest request = new LoginRequest("inexistente@teste.com", "senha123");

        when(usuarioRepository.findByEmail(request.email())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("E-mail ou senha inválidos");
    }

    @Test
    @DisplayName("Deve lançar BadCredentialsException quando a senha estiver incorreta")
    void deveLancarExcecaoQuandoSenhaIncorreta() {
        Usuario usuario = new Usuario("user-1", "João Silva", "joao@teste.com", "$2a$10$encodedPassword", TipoUsuario.ALUNO);
        LoginRequest request = new LoginRequest("joao@teste.com", "senhaErrada");

        when(usuarioRepository.findByEmail(request.email())).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("senhaErrada", "$2a$10$encodedPassword")).thenReturn(false);

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("E-mail ou senha inválidos");
    }

    @Test
    @DisplayName("Deve lançar BusinessException quando usuário estiver inativo")
    void deveLancarExcecaoQuandoUsuarioInativo() {
        Usuario usuario = new Usuario("user-1", "João Silva", "joao@teste.com", "$2a$10$encodedPassword", TipoUsuario.ALUNO);
        usuario.setAtivo(false);
        LoginRequest request = new LoginRequest("joao@teste.com", "senha123");

        when(usuarioRepository.findByEmail(request.email())).thenReturn(Optional.of(usuario));

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Usuário inativo no sistema");
    }

    @Test
    @DisplayName("Deve obter dados do usuário autenticado")
    void deveObterUsuarioAutenticado() {
        Usuario usuario = new Usuario("user-1", "João Silva", "joao@teste.com", "hash", TipoUsuario.PROFESSOR);
        when(usuarioRepository.findByEmail("joao@teste.com")).thenReturn(Optional.of(usuario));

        UsuarioResponse response = authService.obterUsuarioAutenticado("joao@teste.com");

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo("user-1");
        assertThat(response.tipo()).isEqualTo(TipoUsuario.PROFESSOR);
    }

    @Test
    @DisplayName("Deve delegar cadastro para UsuarioService")
    void deveCadastrarUsuario() {
        UsuarioRequest request = new UsuarioRequest("Maria", "maria@teste.com", "senha123", TipoUsuario.ALUNO);
        UsuarioResponse expectedResponse = new UsuarioResponse("usr-1", "Maria", "maria@teste.com", TipoUsuario.ALUNO, true);

        when(usuarioService.criar(request)).thenReturn(expectedResponse);

        UsuarioResponse response = authService.cadastrar(request);

        assertThat(response).isEqualTo(expectedResponse);
        verify(usuarioService).criar(request);
    }
}
