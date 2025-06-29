package br.com.personreg.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.personreg.dtos.CriarPerfilRequest;
import br.com.personreg.dtos.CriarPerfilResponse;
import br.com.personreg.dtos.ObterDadosPerfilResponse;
import br.com.personreg.entities.Perfil;
import br.com.personreg.services.PerfilService;
import jakarta.validation.Valid;

@RestController(value = "Perfil")
@RequestMapping("/api/perfil")
public class PerfilController {

	@Autowired // injeção de dependência
	private PerfilService perfilService;

	/*
	 * HTTP POST /api/perfil/criar Serviço para cadastro de perfil na API
	 */
	@PostMapping(value = "criar", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
	public CriarPerfilResponse criar(
			@RequestBody @Valid CriarPerfilRequest request) throws Exception {
		return perfilService.criar(request); // executando a camada de serviço;
	}

	/*
	 * HTTP PUT /api/perfil/atualizar/{id} Serviço para atualizar um perfil
	 */
	@PutMapping("atualizar/{id}")
	public Perfil atualizar(@PathVariable("id") UUID id,
			@RequestBody @Valid CriarPerfilRequest request) throws Exception {
		return perfilService.atualizar(id, request);
	}

	/*
	 * HTTP DELETE /api/perfil/deletar/{id} Serviço para deletar um perfil
	 */
	@DeleteMapping("deletar/{id}")
	public void deletar(@PathVariable("id") UUID id) throws Exception {
		perfilService.deletar(id);
	}

	/*
	 * HTTP GET /api/perfil/obter-dados/{id} Serviço para consultar dados de um
	 * perfil
	 */
	@GetMapping("obter-dados/{id}")
	public ObterDadosPerfilResponse obterDados(@PathVariable("id") UUID id)
			throws Exception {
		return perfilService.obterDados(id);
	}

	/*
	 * HTTP GET /api/perfil/buscar-todos Serviço para consultar todos os perfis
	 */
	@GetMapping("buscar-todos")
	public List<ObterDadosPerfilResponse> buscarTodos() throws Exception {
		return perfilService.buscarTodos();
	}

}
