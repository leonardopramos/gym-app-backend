package com.gym_app.backend.service;

import com.gym_app.backend.domain.Usuario;
import com.gym_app.backend.dto.LoginRequest;
import com.gym_app.backend.dto.LoginResponse;
import com.gym_app.backend.dto.UsuarioRequest;
import com.gym_app.backend.dto.UsuarioResponse;
import com.gym_app.backend.exception.BusinessException;
import com.gym_app.backend.repository.UsuarioRepository;
import com.gym_app.backend.security.JwtTokenService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final UsuarioService usuarioService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenService jwtTokenService,
            UsuarioService usuarioService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
        this.usuarioService = usuarioService;
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadCredentialsException("E-mail ou senha inválidos"));

        if (!usuario.isAtivo()) {
            throw new BusinessException("Usuário inativo no sistema");
        }

        if (!passwordEncoder.matches(request.senha(), usuario.getSenha())) {
            throw new BadCredentialsException("E-mail ou senha inválidos");
        }

        String token = jwtTokenService.gerarToken(usuario);
        return LoginResponse.of(token, UsuarioResponse.from(usuario));
    }

    @Transactional(readOnly = true)
    public UsuarioResponse obterUsuarioAutenticado(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Usuário autenticado não encontrado"));
        return UsuarioResponse.from(usuario);
    }

    @Transactional
    public UsuarioResponse cadastrar(UsuarioRequest request) {
        return usuarioService.criar(request);
    }
}
