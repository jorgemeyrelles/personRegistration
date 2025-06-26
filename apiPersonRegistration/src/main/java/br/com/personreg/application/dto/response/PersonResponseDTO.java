package br.com.personreg.application.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponseDTO {

	private UUID id;
	private String name;
	private String phone;
	private String cpf;
	private AddressResponseDTO address;
}
