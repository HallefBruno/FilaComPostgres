package com.projeto.postgres.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Map;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "fila_tarefas", schema = "app")
public class FilaTarefa {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@JdbcTypeCode(SqlTypes.JSON) 
    @Column(name = "payload", columnDefinition = "jsonb")
    private Map<String, Object> payload;

    private String status;
    private Integer tentativas;
    
    @Column(name = "agendado_para")
    private LocalDateTime agendadoPara;
	
	@Column(name = "processado_em")
	private LocalDateTime processadoEm;
	
	private String erroLog;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Map<String, Object> getPayload() {
		return payload;
	}

	public void setPayload(Map<String, Object> payload) {
		this.payload = payload;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getTentativas() {
		return tentativas == null ? 0 : tentativas;
	}

	public void setTentativas(Integer tentativas) {
		this.tentativas = tentativas;
	}

	public LocalDateTime getAgendadoPara() {
		return agendadoPara;
	}

	public void setAgendadoPara(LocalDateTime agendadoPara) {
		this.agendadoPara = agendadoPara;
	}

	public LocalDateTime getProcessadoEm() {
		return processadoEm;
	}

	public void setProcessadoEm(LocalDateTime processadoEm) {
		this.processadoEm = processadoEm;
	}

	public String getErroLog() {
		return erroLog;
	}

	public void setErroLog(String erroLog) {
		this.erroLog = erroLog;
	}
	
}
