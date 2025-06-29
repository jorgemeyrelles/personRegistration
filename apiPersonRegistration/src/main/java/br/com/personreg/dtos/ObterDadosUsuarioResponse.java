package br.com.personreg.dtos;

import java.util.UUID;

import lombok.Data;

@Data
public class ObterDadosUsuarioResponse {

	private UUID id;
	private String nome;
	private String email;
	private String nomePerfil;
}
