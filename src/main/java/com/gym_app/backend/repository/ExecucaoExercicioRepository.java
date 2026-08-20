package com.gym_app.backend.repository;
import com.gym_app.backend.domain.ExecucaoExercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface ExecucaoExercicioRepository extends JpaRepository<ExecucaoExercicio,String> {
    boolean existsByExecucaoIdAndSessaoExercicioIdAndSerie(String execucaoId,String sessaoExercicioId,int serie);
    Optional<ExecucaoExercicio> findByIdAndExecucaoId(String id,String execucaoId);
}
