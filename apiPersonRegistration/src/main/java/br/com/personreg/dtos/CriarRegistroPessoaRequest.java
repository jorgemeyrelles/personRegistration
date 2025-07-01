package br.com.personreg.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CriarRegistroPessoaRequest {

	@Size(min = 8, max = 100, message = "Informe o nome com 8 a 100 caracteres.")
	@NotEmpty(message = "Por favor, informe o nome.")
	private String nome;

	@NotEmpty(message = "Por favor, informe o telefone.")
	@Size(min = 8, max = 20, message = "O telefone deve ter entre 8 e 20 caracteres.")
	private String telefone;

	@NotEmpty(message = "Por favor, informe o CPF.")
	@Pattern(regexp = "\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "Informe um CPF válido (somente números ou no formato 000.000.000-00).")
	private String cpf;

	@NotEmpty(message = "Por favor, informe o número do endereço.")
	@Size(max = 10, message = "O número deve ter no máximo 10 caracteres.")
	private String numero;

	@Size(max = 50, message = "O complemento deve ter no máximo 50 caracteres.")
	private String complemento;

	@NotEmpty(message = "Por favor, informe o CEP.")
	@Pattern(regexp = "\\d{8}|\\d{5}-\\d{3}", message = "Informe um CEP válido (somente números ou no formato 00000-000).")
	private String cep;

	@NotEmpty(message = "Por favor, informe o bairro.")
	@Size(max = 60, message = "O bairro deve ter no máximo 60 caracteres.")
	private String bairro;

	@NotEmpty(message = "Por favor, informe o município.")
	@Size(max = 60, message = "O município deve ter no máximo 60 caracteres.")
	private String nomeMunicipio;

	@NotEmpty(message = "Por favor, informe o estado.")
	@Size(max = 60, message = "O estado deve ter no máximo 60 caracteres.")
	private String nomeEstado;

	@NotEmpty(message = "Por favor, informe a latitude.")
	@Size(max = 60, message = "O estado deve ter no máximo 60 caracteres.")
	private String latitude;

	@NotEmpty(message = "Por favor, informe a longitude.")
	@Size(max = 60, message = "O estado deve ter no máximo 60 caracteres.")
	private String longitude;

	@NotEmpty(message = "Por favor, informe o ID do usuário responsável pelo cadastro.")
	private String usuarioId;
}
