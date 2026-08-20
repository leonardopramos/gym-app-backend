package com.gym_app.backend.controller;
import com.gym_app.backend.dto.UsuarioResponse;
import com.gym_app.backend.service.ProfessorAlunoService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/v1/professores")
public class ProfessorAlunoController {
    private final ProfessorAlunoService service;
    public ProfessorAlunoController(ProfessorAlunoService service) { this.service=service; }
    @PostMapping("/{professorId}/alunos/{alunoId}") @ResponseStatus(HttpStatus.CREATED) public void vincular(@PathVariable String professorId,@PathVariable String alunoId) { service.vincular(professorId,alunoId); }
    @GetMapping("/{professorId}/alunos") public List<UsuarioResponse> alunos(@PathVariable String professorId) { return service.alunos(professorId).stream().map(UsuarioResponse::from).toList(); }
    @DeleteMapping("/{professorId}/alunos/{alunoId}") @ResponseStatus(HttpStatus.NO_CONTENT) public void remover(@PathVariable String professorId,@PathVariable String alunoId) { service.remover(professorId,alunoId); }
}
