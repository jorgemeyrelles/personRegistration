package br.com.personreg.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.personreg.dtos.ObterDadosConsumableMovementTypeResponse;
import br.com.personreg.services.ConsumableMovementTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Consumable Movement Type", description = "Operações de consulta de tipos de movimentação de consumíveis")
@RestController
@RequestMapping("/api/consumable-movement-type")
public class ConsumableMovementTypeController {

	@Autowired
	private ConsumableMovementTypeService consumableMovementTypeService;

	@GetMapping("/{id}")
	public ObterDadosConsumableMovementTypeResponse obterDados(@PathVariable UUID id) {
		return consumableMovementTypeService.obterDados(id);
	}

	@GetMapping
	public List<ObterDadosConsumableMovementTypeResponse> buscarTodos() {
		return consumableMovementTypeService.buscarTodos();
	}

	@GetMapping("/buscar-por-nome")
	public ObterDadosConsumableMovementTypeResponse buscarPorNome(@RequestParam String name) {
		return consumableMovementTypeService.buscarPorNome(name);
	}
}
