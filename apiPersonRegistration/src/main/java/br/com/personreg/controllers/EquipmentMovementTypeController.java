package br.com.personreg.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.personreg.dtos.ObterDadosEquipmentMovementTypeResponse;
import br.com.personreg.services.EquipmentMovementTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Equipment Movement Type", description = "Operações de consulta de tipos de movimentação de equipamentos")
@RestController
@RequestMapping("/api/equipment-movement-type")
public class EquipmentMovementTypeController {

	@Autowired
	private EquipmentMovementTypeService equipmentMovementTypeService;

	@GetMapping("/{id}")
	public ObterDadosEquipmentMovementTypeResponse obterDados(@PathVariable UUID id) {
		return equipmentMovementTypeService.obterDados(id);
	}

	@GetMapping
	public List<ObterDadosEquipmentMovementTypeResponse> buscarTodos() {
		return equipmentMovementTypeService.buscarTodos();
	}

	@GetMapping("/buscar-por-nome")
	public ObterDadosEquipmentMovementTypeResponse buscarPorNome(@RequestParam String name) {
		return equipmentMovementTypeService.buscarPorNome(name);
	}
}
