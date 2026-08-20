package com.gym_app.backend.repository;
import com.gym_app.backend.domain.Exercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface ExercicioRepository extends JpaRepository<Exercicio, String> {
    List<Exercicio> findByNomeContainingIgnoreCase(String nome);
}
