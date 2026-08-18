-- ============================================================
-- USUARIO
-- ============================================================

CREATE TABLE usuario (
    id CHAR(36) NOT NULL PRIMARY KEY,

    nome VARCHAR(150) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,

    tipo VARCHAR(20) NOT NULL,

    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_usuario_tipo
        CHECK (tipo IN ('ALUNO', 'PROFESSOR'))
);


-- ============================================================
-- PROFESSOR_ALUNO
-- ============================================================

CREATE TABLE professor_aluno (
    id CHAR(36) NOT NULL PRIMARY KEY,

    professor_id CHAR(36) NOT NULL,
    aluno_id CHAR(36) NOT NULL,

    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_professor_aluno_professor
        FOREIGN KEY (professor_id)
        REFERENCES usuario(id),

    CONSTRAINT fk_professor_aluno_aluno
        FOREIGN KEY (aluno_id)
        REFERENCES usuario(id),

    CONSTRAINT uq_professor_aluno
        UNIQUE (professor_id, aluno_id),

    CONSTRAINT chk_professor_aluno_diferentes
        CHECK (professor_id <> aluno_id)
);


-- ============================================================
-- EXERCICIO
-- ============================================================

CREATE TABLE exercicio (
    id CHAR(36) NOT NULL PRIMARY KEY,

    nome VARCHAR(150) NOT NULL,
    descricao TEXT,

    grupo_muscular VARCHAR(100),

    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


-- ============================================================
-- TREINO
-- Representa uma ficha/programa de treino criada pelo professor
-- para determinado aluno.
-- ============================================================

CREATE TABLE treino (
    id CHAR(36) NOT NULL PRIMARY KEY,

    nome VARCHAR(150) NOT NULL,
    descricao TEXT,

    aluno_id CHAR(36) NOT NULL,
    professor_id CHAR(36) NOT NULL,

    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_treino_aluno
        FOREIGN KEY (aluno_id)
        REFERENCES usuario(id),

    CONSTRAINT fk_treino_professor
        FOREIGN KEY (professor_id)
        REFERENCES usuario(id)
);


-- ============================================================
-- SESSAO_TREINO
-- Ex.: Treino A, Treino B, Treino C...
-- ============================================================

CREATE TABLE sessao_treino (
    id CHAR(36) NOT NULL PRIMARY KEY,

    treino_id CHAR(36) NOT NULL,

    nome VARCHAR(100) NOT NULL,
    descricao TEXT,

    ordem INTEGER NOT NULL,

    CONSTRAINT fk_sessao_treino_treino
        FOREIGN KEY (treino_id)
        REFERENCES treino(id)
        ON DELETE CASCADE,

    CONSTRAINT chk_sessao_treino_ordem
        CHECK (ordem > 0),

    CONSTRAINT uq_sessao_treino_ordem
        UNIQUE (treino_id, ordem)
);


-- ============================================================
-- SESSAO_EXERCICIO
-- Exercícios que fazem parte de uma sessão.
-- ============================================================

CREATE TABLE sessao_exercicio (
    id CHAR(36) NOT NULL PRIMARY KEY,

    sessao_treino_id CHAR(36) NOT NULL,
    exercicio_id CHAR(36) NOT NULL,

    ordem INTEGER NOT NULL,

    series INTEGER,
    repeticoes VARCHAR(50),

    carga_sugerida NUMERIC(6,2),

    descanso_segundos INTEGER,

    observacao TEXT,

    CONSTRAINT fk_sessao_exercicio_sessao
        FOREIGN KEY (sessao_treino_id)
        REFERENCES sessao_treino(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_sessao_exercicio_exercicio
        FOREIGN KEY (exercicio_id)
        REFERENCES exercicio(id),

    CONSTRAINT chk_sessao_exercicio_ordem
        CHECK (ordem > 0),

    CONSTRAINT chk_sessao_exercicio_series
        CHECK (series IS NULL OR series > 0),

    CONSTRAINT chk_sessao_exercicio_carga
        CHECK (carga_sugerida IS NULL OR carga_sugerida >= 0),

    CONSTRAINT chk_sessao_exercicio_descanso
        CHECK (descanso_segundos IS NULL OR descanso_segundos >= 0),

    CONSTRAINT uq_sessao_exercicio_ordem
        UNIQUE (sessao_treino_id, ordem)
);


-- ============================================================
-- ÍNDICES
-- ============================================================

CREATE INDEX idx_professor_aluno_professor
    ON professor_aluno(professor_id);

CREATE INDEX idx_professor_aluno_aluno
    ON professor_aluno(aluno_id);

CREATE INDEX idx_treino_aluno
    ON treino(aluno_id);

CREATE INDEX idx_treino_professor
    ON treino(professor_id);

CREATE INDEX idx_sessao_treino_treino
    ON sessao_treino(treino_id);

CREATE INDEX idx_sessao_exercicio_sessao
    ON sessao_exercicio(sessao_treino_id);

CREATE INDEX idx_sessao_exercicio_exercicio
    ON sessao_exercicio(exercicio_id);
