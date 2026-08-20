package com.gym_app.backend.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "execucao_treino")
public class ExecucaoTreino {
    @Id @Column(length = 36, columnDefinition = "CHAR(36)") private String id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "aluno_id", nullable = false) private Usuario aluno;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "sessao_treino_id", nullable = false) private SessaoTreino sessao;
    @Column(name = "iniciado_em", nullable = false) private LocalDateTime iniciadoEm;
    @Column(name = "finalizado_em") private LocalDateTime finalizadoEm;
    @OneToMany(mappedBy = "execucao", cascade = CascadeType.ALL, orphanRemoval = true) @OrderBy("serie ASC") private List<ExecucaoExercicio> series = new ArrayList<>();
    protected ExecucaoTreino() { }
    public ExecucaoTreino(String id, Usuario aluno, SessaoTreino sessao) { this.id=id; this.aluno=aluno; this.sessao=sessao; this.iniciadoEm=LocalDateTime.now(); }
    public void adicionarSerie(ExecucaoExercicio serie) { series.add(serie); }
    public String getId() { return id; } public Usuario getAluno() { return aluno; } public SessaoTreino getSessao() { return sessao; }
    public LocalDateTime getIniciadoEm() { return iniciadoEm; } public LocalDateTime getFinalizadoEm() { return finalizadoEm; }
    public List<ExecucaoExercicio> getSeries() { return series; }
    public void finalizar() { finalizadoEm = LocalDateTime.now(); }
}
