package com.projeto.postgres.controller;

import com.projeto.postgres.service.FilaService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/fila")
public class FilaController {

    @Autowired
    private FilaService filaService;

    @PostMapping("/publicar")
    public ResponseEntity<String> publicar(@RequestBody Map<String, Object> payload) {
        filaService.publicarTarefa(payload);
        return ResponseEntity.ok("Tarefa enviada para o Postgres com sucesso!");
    }
}
