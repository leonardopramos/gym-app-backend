package com.gym_app.backend.service;
import com.gym_app.backend.domain.Exercicio;
import com.gym_app.backend.dto.*;
import com.gym_app.backend.exception.ResourceNotFoundException;
import com.gym_app.backend.repository.ExercicioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Service public class ExercicioService {
    private final ExercicioRepository repository;
    public ExercicioService(ExercicioRepository repository) { this.repository = repository; }
    @Transactional public ExercicioResponse criar(ExercicioRequest r) { return ExercicioResponse.from(repository.save(new Exercicio(UUID.randomUUID().toString(), r.nome(), r.descricao(), r.grupoMuscular()))); }
    @Transactional(readOnly=true) public List<ExercicioResponse> listar(String nome) { return (nome == null || nome.isBlank() ? repository.findAll() : repository.findByNomeContainingIgnoreCase(nome)).stream().map(ExercicioResponse::from).toList(); }
    @Transactional(readOnly=true) public Exercicio buscar(String id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Exercício não encontrado")); }
    @Transactional public ExercicioResponse atualizar(String id, ExercicioRequest r) { Exercicio e=buscar(id); e.setNome(r.nome()); e.setDescricao(r.descricao()); e.setGrupoMuscular(r.grupoMuscular()); return ExercicioResponse.from(e); }
    @Transactional public void remover(String id) { repository.delete(buscar(id)); }
}
