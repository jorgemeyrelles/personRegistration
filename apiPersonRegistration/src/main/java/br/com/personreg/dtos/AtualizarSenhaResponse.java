package br.com.personreg.dtos;

import java.util.Date;
import java.util.UUID;

import lombok.Data;

/**
 * DTO para resposta de atualização de senha
 */
@Data
public class AtualizarSenhaResponse {

	private UUID id;
	private String email;
	private Date dataHoraAtualizacao;
}
