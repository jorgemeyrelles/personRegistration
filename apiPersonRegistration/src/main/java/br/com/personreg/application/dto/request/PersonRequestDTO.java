package br.com.personreg.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonRequestDTO {

	@NotBlank(message = "Name is required")
	private String name;

	@NotBlank(message = "Phone is required")
	private String phone;

	@NotBlank(message = "CPF is required")
	@Pattern(regexp = "^\\d{11}$", message = "CPF must contain 11 digits")
	private String cpf;

	@Valid
	private AddressRequestDTO address;
}
