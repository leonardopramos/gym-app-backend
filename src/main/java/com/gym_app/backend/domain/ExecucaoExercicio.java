package com.gym_app.backend.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity @Table(name = "execucao_exercicio", uniqueConstraints = @UniqueConstraint(columnNames = {"execucao_treino_id", "sessao_exercicio_id", "serie"}))
public class ExecucaoExercicio {
    @Id @Column(length = 36, columnDefinition = "CHAR(36)") private String id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "execucao_treino_id", nullable = false) private ExecucaoTreino execucao;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "sessao_exercicio_id", nullable = false) private SessaoExercicio sessaoExercicio;
    @Column(nullable = false) private int serie;
    @Column(name = "repeticoes_realizadas") private Integer repeticoesRealizadas;
    @Column(name = "carga_utilizada", precision = 6, scale = 2) private BigDecimal cargaUtilizada;
    @Column(nullable = false) private boolean concluida;
    protected ExecucaoExercicio() { }
    public ExecucaoExercicio(String id, ExecucaoTreino e, SessaoExercicio se, int serie, Integer reps, BigDecimal carga, boolean concluida) { this.id=id; this.execucao=e; this.sessaoExercicio=se; this.serie=serie; this.repeticoesRealizadas=reps; this.cargaUtilizada=carga; this.concluida=concluida; }
    public String getId() { return id; } public SessaoExercicio getSessaoExercicio() { return sessaoExercicio; } public int getSerie() { return serie; }
    public Integer getRepeticoesRealizadas() { return repeticoesRealizadas; } public BigDecimal getCargaUtilizada() { return cargaUtilizada; } public boolean isConcluida() { return concluida; }
    public java.time.LocalDateTime getExecucaoData() { return execucao.getIniciadoEm(); }
    public void atualizar(Integer reps, BigDecimal carga, boolean concluida) { this.repeticoesRealizadas=reps; this.cargaUtilizada=carga; this.concluida=concluida; }
}
