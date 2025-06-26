package br.com.personreg.application.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDTO {

	private UUID id;
	private String number;
	private String complement;
	private String zipCode;
	private String neighborhood;
	private String city;
	private String state;
}
