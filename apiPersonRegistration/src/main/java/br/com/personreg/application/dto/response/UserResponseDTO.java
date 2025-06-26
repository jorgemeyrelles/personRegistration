package br.com.personreg.application.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

	private UUID id;
	private String username;
	private String email;
	private PersonResponseDTO person;
	private String role;
}
