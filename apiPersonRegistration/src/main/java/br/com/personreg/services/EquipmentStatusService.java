package br.com.personreg.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.personreg.dtos.ObterDadosEquipmentStatusResponse;
import br.com.personreg.entities.EquipmentStatus;
import br.com.personreg.repositories.EquipmentStatusRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EquipmentStatusService {

	private final EquipmentStatusRepository equipmentStatusRepository;

	public ObterDadosEquipmentStatusResponse obterDados(UUID id) {
		EquipmentStatus equipmentStatus = equipmentStatusRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Equipment Status não encontrado"));

		return new ObterDadosEquipmentStatusResponse(equipmentStatus.getId(), equipmentStatus.getName());
	}

	public List<ObterDadosEquipmentStatusResponse> buscarTodos() {
		List<ObterDadosEquipmentStatusResponse> resposta = new ArrayList<>();
		List<EquipmentStatus> equipmentStatuses = equipmentStatusRepository.findAll();

		for (EquipmentStatus equipmentStatus : equipmentStatuses) {
			resposta.add(new ObterDadosEquipmentStatusResponse(equipmentStatus.getId(), equipmentStatus.getName()));
		}
		return resposta;
	}

	public ObterDadosEquipmentStatusResponse buscarPorNome(String name) {
		EquipmentStatus equipmentStatus = equipmentStatusRepository.findByName(name);
		if (equipmentStatus == null) {
			throw new NoSuchElementException("Equipment Status não encontrado");
		}
		return new ObterDadosEquipmentStatusResponse(equipmentStatus.getId(), equipmentStatus.getName());
	}
}
