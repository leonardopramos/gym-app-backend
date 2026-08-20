package com.gym_app.backend.controller;

import com.gym_app.backend.dto.LoginRequest;
import com.gym_app.backend.dto.LoginResponse;
import com.gym_app.backend.dto.UsuarioRequest;
import com.gym_app.backend.dto.UsuarioResponse;
import com.gym_app.backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticação", description = "Endpoints de autenticação, login e registro")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Realizar login", description = "Autentica usuário e retorna token JWT com dados do perfil")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/cadastro")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar novo usuário", description = "Cria uma nova conta de Aluno ou Professor")
    public UsuarioResponse cadastrar(@Valid @RequestBody UsuarioRequest request) {
        return authService.cadastrar(request);
    }

    @GetMapping("/me")
    @Operation(summary = "Obter dados do usuário logado", description = "Retorna os dados do usuário autenticado no token JWT")
    public UsuarioResponse me(@AuthenticationPrincipal UserDetails userDetails) {
        return authService.obterUsuarioAutenticado(userDetails.getUsername());
    }
}
