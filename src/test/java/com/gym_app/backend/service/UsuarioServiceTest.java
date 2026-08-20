package com.gym_app.backend.service;

import com.gym_app.backend.domain.TipoUsuario;
import com.gym_app.backend.domain.Usuario;
import com.gym_app.backend.dto.UsuarioRequest;
import com.gym_app.backend.dto.UsuarioResponse;
import com.gym_app.backend.exception.BusinessException;
import com.gym_app.backend.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    private PasswordEncoder passwordEncoder;
    private UsuarioService service;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
        service = new UsuarioService(repository, passwordEncoder);
    }

    @Test
    @DisplayName("Deve criar usuário com senha criptografada usando BCrypt")
    void deveCriarUsuarioComSenhaCriptografada() {
        UsuarioRequest request = new UsuarioRequest("Carlos Silva", "carlos@teste.com", "senha123", TipoUsuario.ALUNO);

        when(repository.findByEmail(request.email())).thenReturn(Optional.empty());
        when(repository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UsuarioResponse response = service.criar(request);

        assertThat(response).isNotNull();
        assertThat(response.nome()).isEqualTo("Carlos Silva");
        assertThat(response.email()).isEqualTo("carlos@teste.com");
        assertThat(response.tipo()).isEqualTo(TipoUsuario.ALUNO);
        assertThat(response.ativo()).isTrue();

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(repository).save(captor.capture());

        Usuario usuarioSalvo = captor.getValue();
        assertThat(usuarioSalvo.getSenha()).isNotEqualTo("senha123");
        assertThat(usuarioSalvo.getSenha()).startsWith("$2a$");
        assertThat(passwordEncoder.matches("senha123", usuarioSalvo.getSenha())).isTrue();
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar usuário com e-mail duplicado")
    void deveLancarExcecaoQuandoEmailDuplicado() {
        UsuarioRequest request = new UsuarioRequest("Carlos Silva", "carlos@teste.com", "senha123", TipoUsuario.ALUNO);

        when(repository.findByEmail(request.email()))
                .thenReturn(Optional.of(new Usuario("id-1", "Carlos", "carlos@teste.com", "hash", TipoUsuario.ALUNO)));

        assertThatThrownBy(() -> service.criar(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("E-mail já cadastrado");

        verify(repository, never()).save(any());
    }
}
