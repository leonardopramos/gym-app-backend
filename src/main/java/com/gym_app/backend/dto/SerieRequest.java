package com.gym_app.backend.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record SerieRequest(@NotBlank String sessaoExercicioId, @Positive int serie, @PositiveOrZero Integer repeticoesRealizadas, @PositiveOrZero @Digits(integer=4, fraction=2) BigDecimal cargaUtilizada, boolean concluida) { }
