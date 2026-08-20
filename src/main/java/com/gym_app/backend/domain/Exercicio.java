package com.gym_app.backend.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "exercicio")
public class Exercicio {
    @Id
    @Column(length = 36, columnDefinition = "CHAR(36)")
    private String id;
    @Column(nullable = false, length = 150)
    private String nome;
    @Column(columnDefinition = "TEXT")
    private String descricao;
    @Column(name = "grupo_muscular", length = 100)
    private String grupoMuscular;
    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    protected Exercicio() { }
    public Exercicio(String id, String nome, String descricao, String grupoMuscular) {
        this.id = id; this.nome = nome; this.descricao = descricao; this.grupoMuscular = grupoMuscular; this.criadoEm = LocalDateTime.now();
    }
    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public String getGrupoMuscular() { return grupoMuscular; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setGrupoMuscular(String grupoMuscular) { this.grupoMuscular = grupoMuscular; }
}
