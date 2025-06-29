package br.com.personreg.dtos;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObterDadosPerfilResponse {
	private UUID id;
	private String nome;
	private List<ObterDadosUsuarioResponse> usuarios;
}
