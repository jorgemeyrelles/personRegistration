package br.com.personreg.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.personreg.dtos.ObterDadosServiceOrderStatusResponse;
import br.com.personreg.services.ServiceOrderStatusService;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Service Order Status", description = "Operações de consulta de status de ordens de serviço")
@RestController
@RequestMapping("/api/service-order-status")
public class ServiceOrderStatusController {

	@Autowired
	private ServiceOrderStatusService serviceOrderStatusService;

	@GetMapping("/{id}")
	public ObterDadosServiceOrderStatusResponse obterDados(@PathVariable UUID id) {
		return serviceOrderStatusService.obterDados(id);
	}

	@GetMapping
	public List<ObterDadosServiceOrderStatusResponse> buscarTodos() {
		return serviceOrderStatusService.buscarTodos();
	}

	@GetMapping("/buscar-por-nome")
	public ObterDadosServiceOrderStatusResponse buscarPorNome(@RequestParam String name) {
		return serviceOrderStatusService.buscarPorNome(name);
	}
}
