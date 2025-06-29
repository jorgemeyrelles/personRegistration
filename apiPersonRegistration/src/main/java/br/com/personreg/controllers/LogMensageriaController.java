package br.com.personreg.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.personreg.collections.LogMensageria;
import br.com.personreg.services.LogMensageriaService;

@RestController(value = "Log Mensageria")
@RequestMapping("/api/log-mensageria")
public class LogMensageriaController {

	@Autowired
	private LogMensageriaService logMensageriaService;

	/**
	 * GET /api/log-mensageria/buscar-todos Retorna todos os registros da
	 * coleção log_mensageria
	 */
	@GetMapping("buscar-todos")
	public List<LogMensageria> buscarTodos() {
		return logMensageriaService.buscarTodos();
	}
}
