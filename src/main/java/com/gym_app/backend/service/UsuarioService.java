package com.gym_app.backend.service;
import com.gym_app.backend.domain.Usuario;
import com.gym_app.backend.dto.*;
import com.gym_app.backend.exception.*;
import com.gym_app.backend.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Service public class UsuarioService {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional public UsuarioResponse criar(UsuarioRequest r) {
        if (repository.findByEmail(r.email()).isPresent()) throw new BusinessException("E-mail já cadastrado");
        String senhaCriptografada = passwordEncoder.encode(r.senha());
        return UsuarioResponse.from(repository.save(new Usuario(UUID.randomUUID().toString(), r.nome(), r.email(), senhaCriptografada, r.tipo())));
    }
    @Transactional(readOnly=true) public UsuarioResponse buscar(String id) { return UsuarioResponse.from(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"))); }
    @Transactional(readOnly=true) public List<UsuarioResponse> listar() { return repository.findAll().stream().map(UsuarioResponse::from).toList(); }
}
