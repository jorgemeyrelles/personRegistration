package br.com.personreg.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.personreg.dtos.ObterDadosEquipmentStatusResponse;
import br.com.personreg.services.EquipmentStatusService;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Equipment Status", description = "Operações de consulta de status de equipamentos")
@RestController
@RequestMapping("/api/equipment-status")
public class EquipmentStatusController {

	@Autowired
	private EquipmentStatusService equipmentStatusService;

	@GetMapping("/{id}")
	public ObterDadosEquipmentStatusResponse obterDados(@PathVariable UUID id) {
		return equipmentStatusService.obterDados(id);
	}

	@GetMapping
	public List<ObterDadosEquipmentStatusResponse> buscarTodos() {
		return equipmentStatusService.buscarTodos();
	}

	@GetMapping("/buscar-por-nome")
	public ObterDadosEquipmentStatusResponse buscarPorNome(@RequestParam String name) {
		return equipmentStatusService.buscarPorNome(name);
	}
}
