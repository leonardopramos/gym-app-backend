package com.gym_app.backend.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "treino")
public class Treino {
    @Id @Column(length = 36, columnDefinition = "CHAR(36)") private String id;
    @Column(nullable = false, length = 150) private String nome;
    @Column(columnDefinition = "TEXT") private String descricao;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "aluno_id", nullable = false) private Usuario aluno;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "professor_id", nullable = false) private Usuario professor;
    @Column(nullable = false) private boolean ativo = true;
    @Column(name = "criado_em", nullable = false) private LocalDateTime criadoEm;
    @OneToMany(mappedBy = "treino", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("ordem ASC") private List<SessaoTreino> sessoes = new ArrayList<>();

    protected Treino() { }
    public Treino(String id, String nome, String descricao, Usuario aluno, Usuario professor) {
        this.id = id; this.nome = nome; this.descricao = descricao; this.aluno = aluno; this.professor = professor; this.criadoEm = LocalDateTime.now();
    }
    public void adicionarSessao(SessaoTreino sessao) { sessoes.add(sessao); }
    public String getId() { return id; } public String getNome() { return nome; } public String getDescricao() { return descricao; }
    public Usuario getAluno() { return aluno; } public Usuario getProfessor() { return professor; }
    public boolean isAtivo() { return ativo; } public void setAtivo(boolean ativo) { this.ativo = ativo; }
    public List<SessaoTreino> getSessoes() { return sessoes; }
}
