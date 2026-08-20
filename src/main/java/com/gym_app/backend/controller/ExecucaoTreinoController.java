package com.gym_app.backend.controller;

import com.gym_app.backend.dto.*;
import com.gym_app.backend.service.ExecucaoTreinoService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/v1/execucoes-treino")
public class ExecucaoTreinoController {
    private final ExecucaoTreinoService service;
    public ExecucaoTreinoController(ExecucaoTreinoService service) { this.service=service; }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public ExecucaoTreinoResponse iniciar(@RequestHeader("X-Aluno-Id") String alunoId, @Valid @RequestBody ExecucaoTreinoRequest request) { return service.iniciar(alunoId,request); }
    @PostMapping("/{id}/series") @ResponseStatus(HttpStatus.CREATED) public ExecucaoTreinoResponse registrar(@RequestHeader("X-Aluno-Id") String alunoId,@PathVariable String id,@Valid @RequestBody SerieRequest request) { return service.registrar(alunoId,id,request); }
    @PutMapping("/{id}/series/{serieId}") public ExecucaoTreinoResponse atualizar(@RequestHeader("X-Aluno-Id") String alunoId,@PathVariable String id,@PathVariable String serieId,@Valid @RequestBody SerieAtualizacaoRequest request) { return service.atualizar(alunoId,id,serieId,request); }
    @PutMapping("/{id}/finalizar") public ExecucaoTreinoResponse finalizar(@RequestHeader("X-Aluno-Id") String alunoId,@PathVariable String id) { return service.finalizar(alunoId,id); }
    @GetMapping("/historico") public List<ExecucaoHistoricoResponse> historico(@RequestHeader("X-Aluno-Id") String alunoId) { return service.historico(alunoId); }
    @GetMapping("/{id}") public ExecucaoTreinoResponse buscar(@RequestHeader("X-Aluno-Id") String alunoId,@PathVariable String id) { return service.buscar(alunoId,id); }
}
