package br.com.personreg.collections;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Data;

@Data
@Document(collection = "log_operacoes")
public class LogOperações {
	@Id
	private UUID id;
	private Date dataHora;
	private String operacao;
	private String status;
	private String descricao;
}