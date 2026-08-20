package com.gym_app.backend.dto;
import com.gym_app.backend.domain.ExecucaoTreino;
import java.time.LocalDateTime;
public record ExecucaoHistoricoResponse(String id, String treinoNome, String sessaoNome, LocalDateTime iniciadoEm, LocalDateTime finalizadoEm, Long duracaoSegundos) {
    public static ExecucaoHistoricoResponse from(ExecucaoTreino e) { Long d=e.getFinalizadoEm()==null?null:java.time.Duration.between(e.getIniciadoEm(),e.getFinalizadoEm()).getSeconds(); return new ExecucaoHistoricoResponse(e.getId(),e.getSessao().getTreino().getNome(),e.getSessao().getNome(),e.getIniciadoEm(),e.getFinalizadoEm(),d); }
}
