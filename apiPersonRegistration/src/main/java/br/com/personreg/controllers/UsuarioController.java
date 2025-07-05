package br.com.personreg.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.personreg.dtos.AutenticarUsuarioRequest;
import br.com.personreg.dtos.AutenticarUsuarioResponse;
import br.com.personreg.dtos.CriarUsuarioRequest;
import br.com.personreg.dtos.CriarUsuarioResponse;
import br.com.personreg.dtos.ObterDadosUsuarioResponse;
import br.com.personreg.services.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Tag(name = "Usuários", description = "Operações de cadastro e consulta de usuários")
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

	@Autowired // injeção de dependência
	private UsuarioService usuarioService;

	/*
	 * HTTP POST /api/usuario/criar Serviço para cadastro de usuário na API
	 */
	@PostMapping("criar")
	public CriarUsuarioResponse criar(
			@RequestBody @Valid CriarUsuarioRequest request) throws Exception {
		return usuarioService.criar(request); // executando a camada de serviço
	}

	/*
	 * HTTP POST /api/usuario/autenticar Serviço para autenticação de usuário na
	 * API
	 */
	@PostMapping("autenticar")
	public AutenticarUsuarioResponse autenticar(
			@RequestBody @Valid AutenticarUsuarioRequest request)
			throws Exception {
		return usuarioService.autenticar(request);
	}

	/*
	 * HTTP GET /api/usuario/obter-dados Serviço para consulta dos dados do
	 * usuário autenticado
	 */
	@GetMapping("obter-dados")
	public ObterDadosUsuarioResponse obterDados(HttpServletRequest request)
			throws Exception {

		// Capturar / extrair o TOKEN JWT enviado na requisição
		String token = request.getHeader("Authorization").replace("Bearer", "")
				.trim();
		// consultando os dados do usuário
		return usuarioService.obterDados(token);
	}

	@GetMapping("/buscar-todos")
	public List<ObterDadosUsuarioResponse> buscarTodosUsuarios() throws Exception {
		// Capturar / extrair o TOKEN JWT enviado na requisição
		// HttpServletRequest request
		// String token = request.getHeader("Authorization").replace("Bearer", "")
		// 		.trim();
		return usuarioService.buscarTodosUsuarios();
	}

	@DeleteMapping("/deletar/{id}")
	public void deletarUsuario(@PathVariable UUID id) throws Exception {
		usuarioService.deletarUsuario(id);
	}
}
