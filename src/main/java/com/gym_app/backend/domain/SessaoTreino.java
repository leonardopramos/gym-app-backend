package com.gym_app.backend.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "sessao_treino", uniqueConstraints = @UniqueConstraint(columnNames = {"treino_id", "ordem"}))
public class SessaoTreino {
    @Id @Column(length = 36, columnDefinition = "CHAR(36)") private String id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "treino_id", nullable = false) private Treino treino;
    @Column(nullable = false, length = 100) private String nome;
    @Column(columnDefinition = "TEXT") private String descricao;
    @Column(nullable = false) private int ordem;
    @OneToMany(mappedBy = "sessao", cascade = CascadeType.ALL, orphanRemoval = true) @OrderBy("ordem ASC") private List<SessaoExercicio> exercicios = new ArrayList<>();
    protected SessaoTreino() { }
    public SessaoTreino(String id, Treino treino, String nome, String descricao, int ordem) { this.id=id; this.treino=treino; this.nome=nome; this.descricao=descricao; this.ordem=ordem; }
    public void adicionarExercicio(SessaoExercicio item) { exercicios.add(item); }
    public String getId() { return id; } public Treino getTreino() { return treino; } public String getNome() { return nome; } public String getDescricao() { return descricao; } public int getOrdem() { return ordem; } public List<SessaoExercicio> getExercicios() { return exercicios; }
}
