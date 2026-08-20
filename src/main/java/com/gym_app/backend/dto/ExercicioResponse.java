package com.gym_app.backend.dto;
import com.gym_app.backend.domain.Exercicio;
public record ExercicioResponse(String id, String nome, String descricao, String grupoMuscular) {
    public static ExercicioResponse from(Exercicio e) { return new ExercicioResponse(e.getId(), e.getNome(), e.getDescricao(), e.getGrupoMuscular()); }
}
