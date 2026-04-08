CREATE TABLE app.fila_tarefas (
    id BIGSERIAL PRIMARY KEY,
    payload JSONB NOT NULL, -- (JSON)
    status TEXT DEFAULT 'pendente' CHECK (status IN ('pendente', 'processando', 'concluido', 'falhou')),
    tentativas INT DEFAULT 0,
    prioridade INT DEFAULT 1, -- 1 e 5
    agendado_para TIMESTAMPTZ DEFAULT NOW(),
    criado_at TIMESTAMPTZ DEFAULT NOW(),
    processado_em TIMESTAMPTZ,
    erro_log TEXT
);

-- 2. O Índice Mestre (O que torna o Postgres o melhor do mundo para filas)
-- Este índice só olha para o que é 'pendente', ignorando milhões de linhas concluídas.
CREATE INDEX idx_fila_processamento ON app.fila_tarefas (prioridade DESC, agendado_para ASC) 
WHERE status = 'pendente';
