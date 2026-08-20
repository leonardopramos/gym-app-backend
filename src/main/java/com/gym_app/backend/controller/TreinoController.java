package com.gym_app.backend.controller;
import com.gym_app.backend.dto.*;
import com.gym_app.backend.service.TreinoService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/v1/treinos")
public class TreinoController {
    private final TreinoService service;
    public TreinoController(TreinoService service) { this.service=service; }
    @PostMapping("/alunos/{alunoId}") @ResponseStatus(HttpStatus.CREATED)
    public TreinoResponse criar(@PathVariable String alunoId, @RequestHeader("X-Professor-Id") String professorId, @Valid @RequestBody TreinoRequest r) { return service.criar(alunoId,professorId,r); }
    @GetMapping("/{id}") public TreinoResponse buscar(@PathVariable String id) { return service.buscar(id); }
    @GetMapping("/alunos/{alunoId}") public List<TreinoResponse> porAluno(@PathVariable String alunoId) { return service.porAluno(alunoId); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void remover(@PathVariable String id) { service.remover(id); }
}
