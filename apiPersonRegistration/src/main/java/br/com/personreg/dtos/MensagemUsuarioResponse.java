package br.com.personreg.dtos;

import lombok.Data;

@Data
public class MensagemUsuarioResponse {

	private String emailDestinatario;
	private String assunto;
	private String texto;
}
