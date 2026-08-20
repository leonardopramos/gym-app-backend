package com.gym_app.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym_app.backend.domain.TipoUsuario;
import com.gym_app.backend.dto.UsuarioRequest;
import com.gym_app.backend.dto.UsuarioResponse;
import com.gym_app.backend.service.UsuarioService;
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
class UsuarioControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Deve retornar 201 ao criar usuário com sucesso")
    void deveCriarUsuarioComSucesso() throws Exception {
        UsuarioRequest request = new UsuarioRequest("Maria Santos", "maria@teste.com", "senhaForte123", TipoUsuario.PROFESSOR);
        UsuarioResponse response = new UsuarioResponse("usr-123", "Maria Santos", "maria@teste.com", TipoUsuario.PROFESSOR, true);

        when(usuarioService.criar(any(UsuarioRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("usr-123"))
                .andExpect(jsonPath("$.nome").value("Maria Santos"))
                .andExpect(jsonPath("$.email").value("maria@teste.com"))
                .andExpect(jsonPath("$.tipo").value("PROFESSOR"))
                .andExpect(jsonPath("$.ativo").value(true))
                .andExpect(jsonPath("$.senha").doesNotExist());
    }
}
