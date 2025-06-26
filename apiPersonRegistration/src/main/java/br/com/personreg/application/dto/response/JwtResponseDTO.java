package br.com.personreg.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponseDTO {

	private String token;
	private String type = "Bearer";
	private UserResponseDTO user;

	public JwtResponseDTO(String token, UserResponseDTO user) {
		this.token = token;
		this.user = user;
	}
}
