package com.gym_app.backend.dto;
import com.gym_app.backend.domain.*;
import java.math.BigDecimal;
import java.util.*;
public record TreinoResponse(String id, String nome, String descricao, String alunoId, String professorId, boolean ativo, List<SessaoResponse> sessoes) {
    public static TreinoResponse from(Treino t) { return new TreinoResponse(t.getId(), t.getNome(), t.getDescricao(), t.getAluno().getId(), t.getProfessor().getId(), t.isAtivo(), t.getSessoes().stream().map(SessaoResponse::from).toList()); }
    public record SessaoResponse(String id, String nome, String descricao, int ordem, List<ItemResponse> exercicios) { static SessaoResponse from(SessaoTreino s) { return new SessaoResponse(s.getId(), s.getNome(), s.getDescricao(), s.getOrdem(), s.getExercicios().stream().map(ItemResponse::from).toList()); } }
    public record ItemResponse(String id, String exercicioId, String exercicioNome, int ordem, Integer series, String repeticoes, BigDecimal cargaSugerida, Integer descansoSegundos, String observacao) { static ItemResponse from(SessaoExercicio i) { return new ItemResponse(i.getId(), i.getExercicio().getId(), i.getExercicio().getNome(), i.getOrdem(), i.getSeries(), i.getRepeticoes(), i.getCargaSugerida(), i.getDescansoSegundos(), i.getObservacao()); } }
}
