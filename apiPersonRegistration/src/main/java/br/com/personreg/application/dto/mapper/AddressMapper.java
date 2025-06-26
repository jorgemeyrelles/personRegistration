package br.com.personreg.application.dto.mapper;

import org.springframework.stereotype.Component;

import br.com.personreg.application.dto.request.AddressRequestDTO;
import br.com.personreg.application.dto.response.AddressResponseDTO;
import br.com.personreg.domain.entity.Address;

@Component
public class AddressMapper {

	public Address toEntity(AddressRequestDTO dto) {
		if (dto == null) {
			return null;
		}

		Address address = new Address();
		address.setNumber(dto.getNumber());
		address.setComplement(dto.getComplement());
		address.setZipCode(dto.getZipCode());
		address.setNeighborhood(dto.getNeighborhood());
		address.setCity(dto.getCity());
		address.setState(dto.getState());

		return address;
	}

	public void updateEntityFromDto(AddressRequestDTO dto, Address address) {
		if (dto == null || address == null) {
			return;
		}

		if (dto.getNumber() != null) {
			address.setNumber(dto.getNumber());
		}
		if (dto.getComplement() != null) {
			address.setComplement(dto.getComplement());
		}
		if (dto.getZipCode() != null) {
			address.setZipCode(dto.getZipCode());
		}
		if (dto.getNeighborhood() != null) {
			address.setNeighborhood(dto.getNeighborhood());
		}
		if (dto.getCity() != null) {
			address.setCity(dto.getCity());
		}
		if (dto.getState() != null) {
			address.setState(dto.getState());
		}
	}

	public AddressResponseDTO toDto(Address entity) {
		if (entity == null) {
			return null;
		}

		return new AddressResponseDTO(entity.getId(), entity.getNumber(),
				entity.getComplement(), entity.getZipCode(),
				entity.getNeighborhood(), entity.getCity(), entity.getState());
	}
}
