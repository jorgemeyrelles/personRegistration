package br.com.personreg.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequestDTO {

	@Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
	private String username;

	@Size(min = 8, message = "Password must be at least 8 characters")
	private String password;

	@Email(message = "Email must be valid")
	private String email;

	@Valid
	private PersonRequestDTO person;
}
