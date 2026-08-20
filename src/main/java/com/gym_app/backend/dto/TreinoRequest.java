package com.gym_app.backend.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
public record TreinoRequest(@NotBlank @Size(max=150) String nome, String descricao, @NotEmpty List<@Valid SessaoRequest> sessoes) {
    public record SessaoRequest(@NotBlank @Size(max=100) String nome, String descricao, @Positive int ordem, @NotEmpty List<@Valid ExercicioRequest> exercicios) { }
    public record ExercicioRequest(@NotBlank String exercicioId, @Positive int ordem, @PositiveOrZero Integer series, String repeticoes, @PositiveOrZero BigDecimal cargaSugerida, @PositiveOrZero Integer descansoSegundos, String observacao) { }
}
