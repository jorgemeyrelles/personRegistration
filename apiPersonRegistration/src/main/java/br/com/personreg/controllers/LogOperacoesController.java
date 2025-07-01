package br.com.personreg.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.personreg.collections.LogOperações;
import br.com.personreg.services.LogOperacoesService;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Log Operações", description = "Operações de consulta de logs")
@RestController
@RequestMapping("/api/log-operacoes")
public class LogOperacoesController {

	@Autowired
	private LogOperacoesService logOperacoesService;

	/**
	 * HTTP GET /api/log-operacoes/buscar-todos Serviço para retornar todos os
	 * registros de operações
	 */
	@GetMapping("buscar-todos")
	public List<LogOperações> buscarTodos() {
		return logOperacoesService.buscarTodos();
	}
}
