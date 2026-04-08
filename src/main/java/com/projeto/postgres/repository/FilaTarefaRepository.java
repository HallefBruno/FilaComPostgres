package com.projeto.postgres.repository;

import com.projeto.postgres.model.FilaTarefa;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FilaTarefaRepository extends JpaRepository<FilaTarefa, Long> {
	
    @Query(value = """
        UPDATE app.fila_tarefas
        SET status = 'processando', tentativas = tentativas + 1
        WHERE id = (
            SELECT id FROM app.fila_tarefas
            WHERE status = 'pendente' AND agendado_para <= NOW()
            ORDER BY prioridade DESC, agendado_para ASC
            FOR UPDATE SKIP LOCKED
            LIMIT 1
        )
        RETURNING *
        """, nativeQuery = true)
    Optional<FilaTarefa> consumirProxima();
}
