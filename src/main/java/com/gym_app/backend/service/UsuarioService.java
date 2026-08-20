package com.gym_app.backend.service;
import com.gym_app.backend.domain.Usuario;
import com.gym_app.backend.dto.*;
import com.gym_app.backend.exception.*;
import com.gym_app.backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Service public class UsuarioService {
    private final UsuarioRepository repository;
    public UsuarioService(UsuarioRepository repository) { this.repository = repository; }
    @Transactional public UsuarioResponse criar(UsuarioRequest r) { if (repository.findByEmail(r.email()).isPresent()) throw new BusinessException("E-mail já cadastrado"); return UsuarioResponse.from(repository.save(new Usuario(UUID.randomUUID().toString(), r.nome(), r.email(), r.senha(), r.tipo()))); }
    @Transactional(readOnly=true) public UsuarioResponse buscar(String id) { return UsuarioResponse.from(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"))); }
    @Transactional(readOnly=true) public List<UsuarioResponse> listar() { return repository.findAll().stream().map(UsuarioResponse::from).toList(); }
}
