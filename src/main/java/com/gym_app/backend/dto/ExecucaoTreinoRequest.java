package com.gym_app.backend.dto;
import jakarta.validation.constraints.NotBlank;
public record ExecucaoTreinoRequest(@NotBlank String sessaoTreinoId) { }
