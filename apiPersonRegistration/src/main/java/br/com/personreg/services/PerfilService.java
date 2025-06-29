package br.com.personreg.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.personreg.components.LogOperacoesComponent;
import br.com.personreg.dtos.CriarPerfilRequest;
import br.com.personreg.dtos.CriarPerfilResponse;
import br.com.personreg.dtos.ObterDadosPerfilResponse;
import br.com.personreg.dtos.ObterDadosUsuarioResponse;
import br.com.personreg.entities.Perfil;
import br.com.personreg.entities.Usuario;
import br.com.personreg.repositories.PerfilRepository;
import br.com.personreg.repositories.UsuarioRepository;
import io.jsonwebtoken.lang.Collections;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PerfilService {

	private final PerfilRepository perfilRepository;
	private final UsuarioRepository usuarioRepository;
	private final LogOperacoesComponent logOperacoesComponent;

	@Transactional
	public CriarPerfilResponse criar(CriarPerfilRequest request) {
		Perfil perfil = new Perfil();
		perfil.setId(UUID.randomUUID());
		perfil.setNome(request.getNome());
		perfil.setUsuarios(new ArrayList<>());

		Perfil saved = perfilRepository.save(perfil);
		if (saved == null) {
			throw new RuntimeException("Erro ao criar perfil");
		}
		// Cria a resposta DTO
		CriarPerfilResponse response = new CriarPerfilResponse();
		response.setId(saved.getId());
		response.setNome(saved.getNome());

		// Log operação
		logOperacoesComponent.log("CRIAR PERFIL", "SUCESSO",
				"Perfil criado com nome: " + perfil.getNome());

		return response;
	}

	@Transactional
	public Perfil atualizar(UUID id, CriarPerfilRequest request) {
		Perfil perfil = perfilRepository.findById(id).orElseThrow(
				() -> new NoSuchElementException("Perfil não encontrado"));

		perfil.setNome(request.getNome());
		Perfil updated = perfilRepository.save(perfil);
		if (updated == null) {
			throw new RuntimeException("Erro ao atualizar perfil");
		}
		// Cria a resposta DTO
		Perfil response = new Perfil();
		response.setId(updated.getId());
		response.setNome(updated.getNome());
		// Atualiza os usuários associados ao perfil, se necessário
		if (perfil.getUsuarios() != null) {
			List<Usuario> usuarios = perfil.getUsuarios().stream()
					.map(usuarioRequest -> {
						Usuario usuario = usuarioRepository
								.findById(usuarioRequest.getId())
								.orElseThrow(() -> new NoSuchElementException(
										"Usuário não encontrado"));
						usuario.setPerfil(perfil);
						return usuario;
					}).collect(Collectors.toList());
			perfil.setUsuarios(usuarios);
			usuarioRepository.saveAll(usuarios);
		}

		logOperacoesComponent.log("ATUALIZAR PERFIL", "SUCESSO",
				"Perfil atualizado: " + perfil.getNome());
		return updated;
	}

	@Transactional
	public void deletar(UUID id) {
		Perfil perfil = perfilRepository.findById(id).orElseThrow(
				() -> new NoSuchElementException("Perfil não encontrado"));

		perfilRepository.delete(perfil);

		logOperacoesComponent.log("DELETAR PERFIL", "SUCESSO",
				"Perfil deletado: " + perfil.getNome());
	}

	public ObterDadosPerfilResponse obterDados(UUID id) {
		Perfil perfil = perfilRepository.findById(id).orElseThrow(
				() -> new NoSuchElementException("Perfil não encontrado"));

		List<ObterDadosUsuarioResponse> usuarios = new ArrayList<>();
		if (perfil.getUsuarios() != null) {
			List<Usuario> usuariosPerfil = perfil.getUsuarios();
			int i = 0;
			while (i < usuariosPerfil.size()) {
				Usuario usuario = usuariosPerfil.get(i);
				usuarios.add(convertToUsuarioResponse(usuario));
				i++;
			}
		}

		return new ObterDadosPerfilResponse(perfil.getId(), perfil.getNome(),
				usuarios);
	}

	public List<ObterDadosPerfilResponse> buscarTodos() {
		List<ObterDadosPerfilResponse> resposta = new ArrayList<>();
		List<Perfil> perfis = perfilRepository.findAll();

		for (Perfil perfil : perfis) {
			// Monta lista de usuários manualmente com while
			List<ObterDadosUsuarioResponse> usuariosDto = new ArrayList<>();
			List<Usuario> usuarios = perfil.getUsuarios() != null
					? perfil.getUsuarios()
					: new ArrayList<>();
			int i = 0;
			while (i < usuarios.size()) {
				Usuario usuario = usuarios.get(i);
				usuariosDto.add(convertToUsuarioResponse(usuario));
				i++;
			}
			resposta.add(new ObterDadosPerfilResponse(perfil.getId(),
					perfil.getNome(), usuariosDto));
		}
		return resposta;
	}

	private ObterDadosUsuarioResponse convertToUsuarioResponse(
			Usuario usuario) {
		ObterDadosUsuarioResponse response = new ObterDadosUsuarioResponse();
		response.setId(usuario.getId());
		response.setNome(usuario.getNome());
		response.setEmail(usuario.getEmail());
		// Evita NullPointerException caso o perfil não esteja preenchido
		if (usuario.getPerfil() == null) {
			response.setPerfil(null);
			return response;
		}
		// envio para setPerfil
		CriarPerfilResponse perfil = new CriarPerfilResponse();
		perfil.setId(usuario.getPerfil().getId());
		perfil.setNome(usuario.getPerfil().getNome());
		response.setPerfil(perfil != null ? perfil : null);
		return response;
	}
}
