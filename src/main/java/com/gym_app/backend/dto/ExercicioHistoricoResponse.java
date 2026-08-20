package com.gym_app.backend.dto;
import com.gym_app.backend.domain.ExecucaoExercicio;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
public record ExercicioHistoricoResponse(LocalDateTime data, List<SerieHistoricoResponse> series) {
    public record SerieHistoricoResponse(int serie, BigDecimal carga, Integer repeticoes) { public static SerieHistoricoResponse from(ExecucaoExercicio s) { return new SerieHistoricoResponse(s.getSerie(),s.getCargaUtilizada(),s.getRepeticoesRealizadas()); } }
}
