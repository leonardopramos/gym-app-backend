package com.gym_app.backend.repository;
import com.gym_app.backend.domain.ExecucaoTreino;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface ExecucaoTreinoRepository extends JpaRepository<ExecucaoTreino,String> {
    @org.springframework.data.jpa.repository.Query("select distinct e from ExecucaoTreino e join fetch e.sessao s join fetch s.treino where e.aluno.id=:alunoId order by e.iniciadoEm desc") List<ExecucaoTreino> findHistorico(@org.springframework.data.repository.query.Param("alunoId") String alunoId);
    Optional<ExecucaoTreino> findByAlunoIdAndSessaoIdAndFinalizadoEmIsNull(String alunoId, String sessaoId);
    boolean existsByIdAndAlunoId(String id, String alunoId);
}
