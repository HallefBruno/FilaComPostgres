package com.projeto.postgres.service;

import com.projeto.postgres.LogSystem;
import com.projeto.postgres.model.FilaTarefa;
import com.projeto.postgres.repository.FilaTarefaRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class FilaService {

	private static final int MAX_TENTATIVAS = 3;
	
    @Autowired
    private FilaTarefaRepository repository;
	
    @Transactional
    public void publicarTarefa(Map<String, Object> dados) {
        FilaTarefa novaTarefa = new FilaTarefa();
        novaTarefa.setPayload(dados);
        novaTarefa.setStatus("pendente");
        novaTarefa.setAgendadoPara(LocalDateTime.now().plusMinutes(1));
        repository.save(novaTarefa);
    }

    @Scheduled(fixedDelay = 1000)
    @Transactional
    public void processarFila() {
        repository.consumirProxima().ifPresent((FilaTarefa tarefa) -> {
            int totalTentativas = tarefa.getTentativas() + 1;
            try {
				LogSystem.log(FilaService.class, "Processando tarefa ID: {} | Tentativa: {}", tarefa.getId(), totalTentativas);
                tarefa.setStatus("concluido");
                tarefa.setProcessadoEm(LocalDateTime.now());
                tarefa.setTentativas(totalTentativas);
            } catch (Exception ex) {
				LogSystem.log(FilaService.class, "Um erro ocorreu ao processar a tarefa ID: {} | Tentativa: {}", tarefa.getId(), totalTentativas, ex.getLocalizedMessage());
                tarefa.setTentativas(totalTentativas);
                tarefa.setErroLog(ex.getMessage());
                
                if (totalTentativas >= MAX_TENTATIVAS) {
                    tarefa.setStatus("falhou");
                } else {
                    tarefa.setStatus("pendente");
                }
            } finally {
                repository.save(tarefa);
            }
        });
    }
}

