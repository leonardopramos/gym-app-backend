package com.gym_app.backend.repository;
import com.gym_app.backend.domain.ProfessorAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface ProfessorAlunoRepository extends JpaRepository<ProfessorAluno, String> {
    boolean existsByProfessorIdAndAlunoId(String professorId, String alunoId);
    List<ProfessorAluno> findByProfessorId(String professorId);
    List<ProfessorAluno> findByAlunoId(String alunoId);
    void deleteByProfessorIdAndAlunoId(String professorId, String alunoId);
}
