package com.gym_app.backend.repository;
import com.gym_app.backend.domain.Treino;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;
public interface TreinoRepository extends JpaRepository<Treino, String> {
    @Query("select distinct t from Treino t left join fetch t.sessoes s left join fetch s.exercicios se left join fetch se.exercicio where t.id = :id")
    Optional<Treino> findCompletoById(@Param("id") String id);
    List<Treino> findByAlunoId(String alunoId);
}
