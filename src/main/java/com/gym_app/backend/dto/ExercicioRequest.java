package com.gym_app.backend.dto;
import jakarta.validation.constraints.*;
public record ExercicioRequest(@NotBlank @Size(max=150) String nome, String descricao, @Size(max=100) String grupoMuscular) { }
