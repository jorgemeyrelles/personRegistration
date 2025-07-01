package br.com.personreg.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.personreg.dtos.CriarRegistroPessoaRequest;
import br.com.personreg.dtos.CriarRegistroPessoaResponse;
import br.com.personreg.services.RegistroPessoaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Registro de Pessoas", description = "Operações de cadastro e consulta de pessoas")
@RestController
@RequestMapping("/api/registro-pessoa")
@RequiredArgsConstructor
public class RegistroPessoaController {

	private final RegistroPessoaService registroPessoaService;

	@PostMapping
	public CriarRegistroPessoaResponse criar(
			@RequestBody @Valid CriarRegistroPessoaRequest request) {
		return registroPessoaService.criar(request);
	}

	@PutMapping("/{id}")
	public CriarRegistroPessoaResponse atualizar(@PathVariable UUID id,
			@RequestBody Optional<CriarRegistroPessoaRequest> request) {
		return registroPessoaService.atualizar(id, request);
	}

	@DeleteMapping("/{id}")
	public void deletar(@PathVariable UUID id) {
		registroPessoaService.deletar(id);
	}

	@GetMapping("/{id}")
	public CriarRegistroPessoaResponse obterDados(@PathVariable UUID id) {
		return registroPessoaService.obterDados(id);
	}

	@GetMapping
	public List<CriarRegistroPessoaResponse> buscarTodos() {
		return registroPessoaService.buscarTodos();
	}

	@GetMapping("/cpf/{cpf}")
	public Optional<CriarRegistroPessoaResponse> buscarPorCpf(
			@PathVariable String cpf) {
		return registroPessoaService.buscarPorCpf(cpf);
	}

	@GetMapping("/existe/cpf/{cpf}")
	public boolean existePorCpf(@PathVariable String cpf) {
		return registroPessoaService.existePorCpf(cpf);
	}

	@GetMapping("/nome/{nome}")
	public List<CriarRegistroPessoaResponse> buscarPorNome(
			@PathVariable String nome) {
		return registroPessoaService.buscarPorNome(nome);
	}

	@GetMapping("/telefone/{telefone}")
	public Optional<CriarRegistroPessoaResponse> buscarPorTelefone(
			@PathVariable String telefone) {
		return registroPessoaService.buscarPorTelefone(telefone);
	}
}
