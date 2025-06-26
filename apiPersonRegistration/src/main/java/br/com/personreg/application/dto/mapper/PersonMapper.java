package br.com.personreg.application.dto.mapper;

import org.springframework.stereotype.Component;

import br.com.personreg.application.dto.request.PersonRequestDTO;
import br.com.personreg.application.dto.response.PersonResponseDTO;
import br.com.personreg.domain.entity.Person;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PersonMapper {
    
    private final AddressMapper addressMapper;
    
    public Person toEntity(PersonRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Person person = new Person();
        person.setName(dto.getName());
        person.setPhone(dto.getPhone());
        person.setCpf(dto.getCpf());
        person.setAddress(addressMapper.toEntity(dto.getAddress()));
        
        return person;
    }
    
    public void updateEntityFromDto(PersonRequestDTO dto, Person person) {
        if (dto == null || person == null) {
            return;
        }
        
        if (dto.getName() != null) {
            person.setName(dto.getName());
        }
        if (dto.getPhone() != null) {
            person.setPhone(dto.getPhone());
        }
        // CPF shouldn't be updated once set
        
        if (dto.getAddress() != null) {
            if (person.getAddress() != null) {
                addressMapper.updateEntityFromDto(dto.getAddress(), person.getAddress());
            } else {
                person.setAddress(addressMapper.toEntity(dto.getAddress()));
            }
        }
    }
    
    public PersonResponseDTO toDto(Person entity) {
        if (entity == null) {
            return null;
        }
        
        return new PersonResponseDTO(
            entity.getId(),
            entity.getName(),
            entity.getPhone(),
            entity.getCpf(),
            addressMapper.toDto(entity.getAddress())
        );
    }
}
