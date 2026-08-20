package com.gym_app.backend.controller;
import com.gym_app.backend.dto.*;
import com.gym_app.backend.service.ExercicioService;
import com.gym_app.backend.service.ExecucaoTreinoService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/v1/exercicios")
public class ExercicioController {
    private final ExercicioService service; private final ExecucaoTreinoService execucoes;
    public ExercicioController(ExercicioService service, ExecucaoTreinoService execucoes) { this.service=service; this.execucoes=execucoes; }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public ExercicioResponse criar(@Valid @RequestBody ExercicioRequest r) { return service.criar(r); }
    @GetMapping public List<ExercicioResponse> listar(@RequestParam(required=false) String nome) { return service.listar(nome); }
    @GetMapping("/{id}") public ExercicioResponse buscar(@PathVariable String id) { return ExercicioResponse.from(service.buscar(id)); }
    @PutMapping("/{id}") public ExercicioResponse atualizar(@PathVariable String id, @Valid @RequestBody ExercicioRequest r) { return service.atualizar(id,r); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void remover(@PathVariable String id) { service.remover(id); }
    @GetMapping("/{id}/historico") public List<ExercicioHistoricoResponse> historico(@RequestHeader("X-Aluno-Id") String alunoId,@PathVariable String id) { return execucoes.historicoExercicio(alunoId,id); }
}
