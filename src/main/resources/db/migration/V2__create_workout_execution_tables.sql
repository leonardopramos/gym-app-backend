CREATE TABLE execucao_treino (
    id CHAR(36) NOT NULL PRIMARY KEY,
    aluno_id CHAR(36) NOT NULL,
    sessao_treino_id CHAR(36) NOT NULL,
    iniciado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    finalizado_em TIMESTAMP NULL,
    CONSTRAINT fk_execucao_treino_aluno FOREIGN KEY (aluno_id) REFERENCES usuario(id),
    CONSTRAINT fk_execucao_treino_sessao FOREIGN KEY (sessao_treino_id) REFERENCES sessao_treino(id)
);

CREATE TABLE execucao_exercicio (
    id CHAR(36) NOT NULL PRIMARY KEY,
    execucao_treino_id CHAR(36) NOT NULL,
    sessao_exercicio_id CHAR(36) NOT NULL,
    serie INTEGER NOT NULL,
    repeticoes_realizadas INTEGER NULL,
    carga_utilizada NUMERIC(6,2) NULL,
    concluida BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_execucao_exercicio_execucao FOREIGN KEY (execucao_treino_id) REFERENCES execucao_treino(id) ON DELETE CASCADE,
    CONSTRAINT fk_execucao_exercicio_sessao_exercicio FOREIGN KEY (sessao_exercicio_id) REFERENCES sessao_exercicio(id),
    CONSTRAINT chk_execucao_exercicio_serie CHECK (serie > 0),
    CONSTRAINT chk_execucao_exercicio_repeticoes CHECK (repeticoes_realizadas IS NULL OR repeticoes_realizadas >= 0),
    CONSTRAINT chk_execucao_exercicio_carga CHECK (carga_utilizada IS NULL OR carga_utilizada >= 0),
    CONSTRAINT uq_execucao_exercicio_serie UNIQUE (execucao_treino_id, sessao_exercicio_id, serie)
);

CREATE INDEX idx_execucao_treino_aluno ON execucao_treino(aluno_id);
CREATE INDEX idx_execucao_treino_sessao ON execucao_treino(sessao_treino_id);
CREATE INDEX idx_execucao_treino_iniciado ON execucao_treino(iniciado_em);
CREATE INDEX idx_execucao_exercicio_execucao ON execucao_exercicio(execucao_treino_id);
CREATE INDEX idx_execucao_exercicio_sessao_exercicio ON execucao_exercicio(sessao_exercicio_id);
