package com.gym_app.backend.dto;
import com.gym_app.backend.domain.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public record ExecucaoTreinoResponse(String id, LocalDateTime iniciadoEm, LocalDateTime finalizadoEm, SessaoResponse sessao, List<SerieResponse> series) {
    public static ExecucaoTreinoResponse from(ExecucaoTreino e) { return new ExecucaoTreinoResponse(e.getId(), e.getIniciadoEm(), e.getFinalizadoEm(), SessaoResponse.from(e.getSessao()), e.getSeries().stream().map(SerieResponse::from).toList()); }
    public record SessaoResponse(String id, String nome, String treinoId, String treinoNome, List<ExercicioResponse> exercicios) {
        static SessaoResponse from(SessaoTreino s) { return new SessaoResponse(s.getId(), s.getNome(), s.getTreino().getId(), s.getTreino().getNome(), s.getExercicios().stream().map(ExercicioResponse::from).toList()); }
    }
    public record ExercicioResponse(String id, String exercicioId, String exercicioNome, int ordem, Integer series, String repeticoes, BigDecimal cargaSugerida, Integer descansoSegundos) {
        static ExercicioResponse from(SessaoExercicio s) { return new ExercicioResponse(s.getId(), s.getExercicio().getId(), s.getExercicio().getNome(), s.getOrdem(), s.getSeries(), s.getRepeticoes(), s.getCargaSugerida(), s.getDescansoSegundos()); }
    }
    public record SerieResponse(String id, String sessaoExercicioId, int serie, Integer repeticoesRealizadas, BigDecimal cargaUtilizada, boolean concluida) { static SerieResponse from(ExecucaoExercicio s) { return new SerieResponse(s.getId(), s.getSessaoExercicio().getId(), s.getSerie(), s.getRepeticoesRealizadas(), s.getCargaUtilizada(), s.isConcluida()); } }
}
