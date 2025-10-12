package br.com.personreg.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.personreg.dtos.ObterDadosConsumableMovementTypeResponse;
import br.com.personreg.entities.ConsumableMovementType;
import br.com.personreg.repositories.ConsumableMovementTypeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsumableMovementTypeService {

	private final ConsumableMovementTypeRepository consumableMovementTypeRepository;

	public ObterDadosConsumableMovementTypeResponse obterDados(UUID id) {
		ConsumableMovementType consumableMovementType = consumableMovementTypeRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Consumable Movement Type não encontrado"));

		return new ObterDadosConsumableMovementTypeResponse(consumableMovementType.getId(), consumableMovementType.getName());
	}

	public List<ObterDadosConsumableMovementTypeResponse> buscarTodos() {
		List<ObterDadosConsumableMovementTypeResponse> resposta = new ArrayList<>();
		List<ConsumableMovementType> consumableMovementTypes = consumableMovementTypeRepository.findAll();

		for (ConsumableMovementType consumableMovementType : consumableMovementTypes) {
			resposta.add(new ObterDadosConsumableMovementTypeResponse(consumableMovementType.getId(), consumableMovementType.getName()));
		}
		return resposta;
	}

	public ObterDadosConsumableMovementTypeResponse buscarPorNome(String name) {
		ConsumableMovementType consumableMovementType = consumableMovementTypeRepository.findByName(name);
		if (consumableMovementType == null) {
			throw new NoSuchElementException("Consumable Movement Type não encontrado");
		}
		return new ObterDadosConsumableMovementTypeResponse(consumableMovementType.getId(), consumableMovementType.getName());
	}
}
