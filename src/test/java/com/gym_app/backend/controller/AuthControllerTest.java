package com.gym_app.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym_app.backend.domain.TipoUsuario;
import com.gym_app.backend.dto.LoginRequest;
import com.gym_app.backend.dto.LoginResponse;
import com.gym_app.backend.dto.UsuarioRequest;
import com.gym_app.backend.dto.UsuarioResponse;
import com.gym_app.backend.exception.GlobalExceptionHandler;
import com.gym_app.backend.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Deve realizar login com sucesso e retornar 200 OK")
    void deveRealizarLoginComSucesso() throws Exception {
        LoginRequest request = new LoginRequest("usuario@teste.com", "senha123");
        UsuarioResponse usuarioResponse = new UsuarioResponse("usr-1", "Usuario Teste", "usuario@teste.com", TipoUsuario.ALUNO, true);
        LoginResponse loginResponse = LoginResponse.of("token.jwt.aqui", usuarioResponse);

        when(authService.login(any(LoginRequest.class))).thenReturn(loginResponse);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token.jwt.aqui"))
                .andExpect(jsonPath("$.tipoToken").value("Bearer"))
                .andExpect(jsonPath("$.usuario.id").value("usr-1"))
                .andExpect(jsonPath("$.usuario.email").value("usuario@teste.com"))
                .andExpect(jsonPath("$.usuario.tipo").value("ALUNO"));
    }

    @Test
    @DisplayName("Deve retornar 400 Bad Request ao tentar login com dados inválidos")
    void deveRetornarBadRequestComDadosInvalidos() throws Exception {
        LoginRequest request = new LoginRequest("email-invalido", "");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve cadastrar novo usuário com sucesso e retornar 201 Created")
    void deveCadastrarComSucesso() throws Exception {
        UsuarioRequest request = new UsuarioRequest("Novo Aluno", "novo@teste.com", "senha12345", TipoUsuario.ALUNO);
        UsuarioResponse response = new UsuarioResponse("usr-2", "Novo Aluno", "novo@teste.com", TipoUsuario.ALUNO, true);

        when(authService.cadastrar(any(UsuarioRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/auth/cadastro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("usr-2"))
                .andExpect(jsonPath("$.email").value("novo@teste.com"));
    }
}
