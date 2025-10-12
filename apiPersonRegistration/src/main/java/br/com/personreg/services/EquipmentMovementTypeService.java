package br.com.personreg.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.personreg.dtos.ObterDadosEquipmentMovementTypeResponse;
import br.com.personreg.entities.EquipmentMovementType;
import br.com.personreg.repositories.EquipmentMovementTypeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EquipmentMovementTypeService {

	private final EquipmentMovementTypeRepository equipmentMovementTypeRepository;

	public ObterDadosEquipmentMovementTypeResponse obterDados(UUID id) {
		EquipmentMovementType equipmentMovementType = equipmentMovementTypeRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Equipment Movement Type não encontrado"));

		return new ObterDadosEquipmentMovementTypeResponse(equipmentMovementType.getId(), equipmentMovementType.getName());
	}

	public List<ObterDadosEquipmentMovementTypeResponse> buscarTodos() {
		List<ObterDadosEquipmentMovementTypeResponse> resposta = new ArrayList<>();
		List<EquipmentMovementType> equipmentMovementTypes = equipmentMovementTypeRepository.findAll();

		for (EquipmentMovementType equipmentMovementType : equipmentMovementTypes) {
			resposta.add(new ObterDadosEquipmentMovementTypeResponse(equipmentMovementType.getId(), equipmentMovementType.getName()));
		}
		return resposta;
	}

	public ObterDadosEquipmentMovementTypeResponse buscarPorNome(String name) {
		EquipmentMovementType equipmentMovementType = equipmentMovementTypeRepository.findByName(name);
		if (equipmentMovementType == null) {
			throw new NoSuchElementException("Equipment Movement Type não encontrado");
		}
		return new ObterDadosEquipmentMovementTypeResponse(equipmentMovementType.getId(), equipmentMovementType.getName());
	}
}
