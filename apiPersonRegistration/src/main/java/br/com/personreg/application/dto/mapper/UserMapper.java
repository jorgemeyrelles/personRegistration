package br.com.personreg.application.dto.mapper;

import org.springframework.stereotype.Component;

import br.com.personreg.application.dto.request.UserRequestDTO;
import br.com.personreg.application.dto.request.UserUpdateRequestDTO;
import br.com.personreg.application.dto.response.UserResponseDTO;
import br.com.personreg.domain.entity.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMapper {

	private final PersonMapper personMapper;

	public User toEntity(UserRequestDTO dto) {
		if (dto == null) {
			return null;
		}

		User user = new User();
		user.setUsername(dto.getUsername());
		user.setPassword(dto.getPassword()); // This will be encoded in the
												// service layer
		user.setEmail(dto.getEmail());
		user.setPerson(personMapper.toEntity(dto.getPerson()));

		// Role will be set in the service layer

		return user;
	}

	public void updateEntityFromDto(UserUpdateRequestDTO dto, User user) {
		if (dto == null || user == null) {
			return;
		}

		if (dto.getUsername() != null) {
			user.setUsername(dto.getUsername());
		}
		if (dto.getPassword() != null) {
			user.setPassword(dto.getPassword()); // This will be encoded in the
													// service layer
		}
		if (dto.getEmail() != null) {
			user.setEmail(dto.getEmail());
		}

		if (dto.getPerson() != null && user.getPerson() != null) {
			personMapper.updateEntityFromDto(dto.getPerson(), user.getPerson());
		}
	}

	public UserResponseDTO toDto(User entity) {
		if (entity == null) {
			return null;
		}

		return new UserResponseDTO(entity.getId(), entity.getUsername(),
				entity.getEmail(), personMapper.toDto(entity.getPerson()),
				entity.getRole().getName().toString());
	}
}
