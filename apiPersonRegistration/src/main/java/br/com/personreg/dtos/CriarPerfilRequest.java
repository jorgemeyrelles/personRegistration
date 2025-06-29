package br.com.personreg.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CriarPerfilRequest {

	@NotBlank(message = "O nome do perfil é obrigatório")
	@Size(max = 50, message = "O nome do perfil deve ter no máximo 50 caracteres")
	private String nome;
}
