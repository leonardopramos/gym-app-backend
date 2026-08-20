package com.gym_app.backend.domain;

import jakarta.persistence.*;

@Entity @Table(name = "sessao_exercicio", uniqueConstraints = @UniqueConstraint(columnNames = {"sessao_treino_id", "ordem"}))
public class SessaoExercicio {
    @Id @Column(length = 36, columnDefinition = "CHAR(36)") private String id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "sessao_treino_id", nullable = false) private SessaoTreino sessao;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "exercicio_id", nullable = false) private Exercicio exercicio;
    @Column(nullable = false) private int ordem;
    private Integer series;
    private String repeticoes;
    @Column(name = "carga_sugerida") private java.math.BigDecimal cargaSugerida;
    @Column(name = "descanso_segundos") private Integer descansoSegundos;
    @Column(columnDefinition = "TEXT") private String observacao;
    protected SessaoExercicio() { }
    public SessaoExercicio(String id, SessaoTreino sessao, Exercicio exercicio, int ordem, Integer series, String repeticoes, java.math.BigDecimal cargaSugerida, Integer descansoSegundos, String observacao) { this.id=id; this.sessao=sessao; this.exercicio=exercicio; this.ordem=ordem; this.series=series; this.repeticoes=repeticoes; this.cargaSugerida=cargaSugerida; this.descansoSegundos=descansoSegundos; this.observacao=observacao; }
    public String getId() { return id; } public SessaoTreino getSessao() { return sessao; } public Exercicio getExercicio() { return exercicio; } public int getOrdem() { return ordem; } public Integer getSeries() { return series; } public String getRepeticoes() { return repeticoes; } public java.math.BigDecimal getCargaSugerida() { return cargaSugerida; } public Integer getDescansoSegundos() { return descansoSegundos; } public String getObservacao() { return observacao; }
}
