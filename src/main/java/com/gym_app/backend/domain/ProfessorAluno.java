package com.gym_app.backend.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "professor_aluno", uniqueConstraints = @UniqueConstraint(columnNames = {"professor_id", "aluno_id"}))
public class ProfessorAluno {
    @Id
    @Column(length = 36, columnDefinition = "CHAR(36)")
    private String id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "professor_id", nullable = false)
    private Usuario professor;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "aluno_id", nullable = false)
    private Usuario aluno;
    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    protected ProfessorAluno() { }
    public ProfessorAluno(String id, Usuario professor, Usuario aluno) {
        this.id = id; this.professor = professor; this.aluno = aluno; this.criadoEm = LocalDateTime.now();
    }
    public String getId() { return id; }
    public Usuario getProfessor() { return professor; }
    public Usuario getAluno() { return aluno; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
}
