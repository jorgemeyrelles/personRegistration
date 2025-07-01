package br.com.personreg.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.personreg.components.LogOperacoesComponent;
import br.com.personreg.dtos.CriarPerfilResponse;
import br.com.personreg.dtos.CriarRegistroPessoaRequest;
import br.com.personreg.dtos.CriarRegistroPessoaResponse;
import br.com.personreg.dtos.ObterDadosUsuarioResponse;
import br.com.personreg.entities.RegistroPessoa;
import br.com.personreg.entities.Usuario;
import br.com.personreg.repositories.RegistroPessoaRepository;
import br.com.personreg.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistroPessoaService {

	private final RegistroPessoaRepository registroPessoaRepository;
	private final UsuarioRepository usuarioRepository;
	private final LogOperacoesComponent logOperacoesComponent;
	@Autowired
	private RedisCacheService redisCacheService;
	@Autowired
	private ObjectMapper objectMapper;

	private static final String cacheKey = "registro_pessoa_todos";

	@Value("${spring.cache.redis.time-to-live}")
	private long cacheDurationMillis;

	@Transactional
	public CriarRegistroPessoaResponse criar(
			CriarRegistroPessoaRequest request) {
		RegistroPessoa registro = new RegistroPessoa();
		// registro.setId(UUID.randomUUID());
		registro.setNome(request.getNome());
		registro.setTelefone(request.getTelefone());
		registro.setCpf(request.getCpf());
		registro.setNumero(request.getNumero());
		registro.setComplemento(request.getComplemento());
		registro.setCep(request.getCep());
		registro.setBairro(request.getBairro());
		registro.setNomeMunicipio(request.getNomeMunicipio());
		registro.setNomeEstado(request.getNomeEstado());
		registro.setLatitude(request.getLatitude());
		registro.setLongitude(request.getLongitude());

		Usuario usuario = usuarioRepository
				.findById(UUID.fromString(request.getUsuarioId()))
				.orElseThrow(() -> new NoSuchElementException(
						"Usuário não encontrado"));
		registro.setUsuario(usuario);

		RegistroPessoa saved = registroPessoaRepository.save(registro);

		// Atualiza a lista em cache após criar novo registro
		List<CriarRegistroPessoaResponse> cache = redisCacheService
				.getCache(cacheKey, CriarRegistroPessoaResponse.class);
		CriarRegistroPessoaResponse novo = toResponse(saved);
		if (cache != null) {
			cache.add(novo);
			redisCacheService.setCache(cacheKey, cache, cacheDurationMillis,
					TimeUnit.MILLISECONDS);
		}

		logOperacoesComponent.log("CRIAR REGISTRO PESSOA", "SUCESSO",
				"Pessoa criada com nome: " + registro.getNome() + ", CPF: "
						+ registro.getCpf());

		return toResponse(saved);
	}

	@Transactional
	public CriarRegistroPessoaResponse atualizar(UUID id,
			Optional<CriarRegistroPessoaRequest> optionalRequest) {
		RegistroPessoa registro = registroPessoaRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException(
						"Registro de pessoa não encontrado"));

		if (optionalRequest.isEmpty()) {
			throw new IllegalArgumentException(
					"Dados para atualização não informados.");
		}

		CriarRegistroPessoaRequest request = optionalRequest.get();

		if (request.getNome() != null)
			registro.setNome(request.getNome());
		if (request.getTelefone() != null)
			registro.setTelefone(request.getTelefone());
		if (request.getCpf() != null)
			registro.setCpf(request.getCpf());
		if (request.getNumero() != null)
			registro.setNumero(request.getNumero());
		if (request.getComplemento() != null)
			registro.setComplemento(request.getComplemento());
		if (request.getCep() != null)
			registro.setCep(request.getCep());
		if (request.getBairro() != null)
			registro.setBairro(request.getBairro());
		if (request.getNomeMunicipio() != null)
			registro.setNomeMunicipio(request.getNomeMunicipio());
		if (request.getNomeEstado() != null)
			registro.setNomeEstado(request.getNomeEstado());
		if (request.getLatitude() != null)
			registro.setLatitude(request.getLatitude());
		if (request.getLongitude() != null)
			registro.setLongitude(request.getLongitude());
		if (request.getUsuarioId() != null) {
			Usuario usuario = usuarioRepository
					.findById(UUID.fromString(request.getUsuarioId()))
					.orElseThrow(() -> new NoSuchElementException(
							"Usuário não encontrado"));
			registro.setUsuario(usuario);
		}

		RegistroPessoa updated = registroPessoaRepository.save(registro);

		// Atualize a lista em cache
		atualizarCacheComRegistroAtualizado(toResponse(updated));

		logOperacoesComponent.log("ATUALIZAR REGISTRO PESSOA", "SUCESSO",
				"Pessoa atualizada: " + registro.getNome() + ", CPF: "
						+ registro.getCpf());

		return toResponse(updated);
	}

	@Transactional
	public void deletar(UUID id) {
		RegistroPessoa registro = registroPessoaRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException(
						"Registro de pessoa não encontrado"));

		registroPessoaRepository.delete(registro);

		removerRegistroDoCache(id);

		logOperacoesComponent.log("DELETAR REGISTRO PESSOA", "SUCESSO",
				"Registro de pessoa deletado: " + registro.getNome() + ", CPF: "
						+ registro.getCpf());
	}

	public CriarRegistroPessoaResponse obterDados(UUID id) {
		List<CriarRegistroPessoaResponse> resposta = redisCacheService
				.getCache(cacheKey, CriarRegistroPessoaResponse.class);
		if (resposta != null) {
			return resposta.stream().filter(r -> r.getId().equals(id))
					.findFirst().orElseThrow(() -> new NoSuchElementException(
							"Registro de pessoa não encontrado"));
		}
		RegistroPessoa registro = registroPessoaRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException(
						"Registro de pessoa não encontrado"));

		return toResponse(registro);
	}

	public List<CriarRegistroPessoaResponse> buscarTodos() {
		List<CriarRegistroPessoaResponse> resposta = redisCacheService
				.getCache(cacheKey, CriarRegistroPessoaResponse.class);
		if (resposta != null) {
			return resposta;
		}

		List<RegistroPessoa> registros = registroPessoaRepository.findAll();
		resposta = new ArrayList<>();
		for (RegistroPessoa registro : registros) {
			resposta.add(toResponse(registro));
		}
		redisCacheService.setCache(cacheKey, resposta, 30, TimeUnit.MINUTES);
		return resposta;
	}

	public Optional<CriarRegistroPessoaResponse> buscarPorCpf(String cpf) {
		List<?> resposta = redisCacheService.getCache(cacheKey,
				CriarRegistroPessoaResponse.class);
		if (resposta != null) {
			return resposta.stream().map(obj -> {
				if (obj instanceof LinkedHashMap) {
					return objectMapper.convertValue(obj,
							CriarRegistroPessoaResponse.class);
				} else if (obj instanceof CriarRegistroPessoaResponse) {
					return (CriarRegistroPessoaResponse) obj;
				} else {
					return null;
				}
			}).filter(Objects::nonNull)
					.filter(r -> r.getCpf().equalsIgnoreCase(cpf)).findFirst();
		}
		return registroPessoaRepository.findByCpf(cpf).map(this::toResponse);
	}

	public boolean existePorCpf(String cpf) {
		List<CriarRegistroPessoaResponse> resposta = redisCacheService
				.getCache(cacheKey, CriarRegistroPessoaResponse.class);
		if (resposta != null) {
			return resposta.stream()
					.anyMatch(r -> r.getCpf().equalsIgnoreCase(cpf));
		}
		return registroPessoaRepository.existsByCpf(cpf);
	}

	public List<CriarRegistroPessoaResponse> buscarPorNome(String nome) {
		List<?> respostaRedis = redisCacheService.getCache(cacheKey,
				CriarRegistroPessoaResponse.class);
		if (respostaRedis != null) {
			return respostaRedis.stream().map(obj -> {
				if (obj instanceof LinkedHashMap) {
					return objectMapper.convertValue(obj,
							CriarRegistroPessoaResponse.class);
				} else if (obj instanceof CriarRegistroPessoaResponse) {
					return (CriarRegistroPessoaResponse) obj;
				} else {
					return null;
				}
			}).filter(Objects::nonNull)
					.filter(r -> r.getNome() != null && r.getNome()
							.toLowerCase().contains(nome.toLowerCase()))
					.collect(Collectors.toList());
		}
		List<RegistroPessoa> registros = registroPessoaRepository
				.findByNomeContainingIgnoreCase(nome);
		List<CriarRegistroPessoaResponse> resposta = new ArrayList<>();
		for (RegistroPessoa registro : registros) {
			resposta.add(toResponse(registro));
		}
		return resposta;
	}

	public Optional<CriarRegistroPessoaResponse> buscarPorTelefone(
			String telefone) {
		List<?> resposta = redisCacheService.getCache(cacheKey,
				CriarRegistroPessoaResponse.class);
		if (resposta != null) {
			return resposta.stream().map(obj -> {
				if (obj instanceof LinkedHashMap) {
					return objectMapper.convertValue(obj,
							CriarRegistroPessoaResponse.class);
				} else if (obj instanceof CriarRegistroPessoaResponse) {
					return (CriarRegistroPessoaResponse) obj;
				} else {
					return null;
				}
			}).filter(Objects::nonNull).filter(r -> r.getTelefone() != null
					&& r.getTelefone().equals(telefone)).findFirst();
		}
		return registroPessoaRepository.findByTelefone(telefone)
				.map(this::toResponse);
	}

	private void atualizarCacheComRegistroAtualizado(
			CriarRegistroPessoaResponse registroAtualizado) {
		List<?> cachedList = redisCacheService.getCache(cacheKey,
				CriarRegistroPessoaResponse.class);
		if (cachedList == null)
			return;

		List<CriarRegistroPessoaResponse> novaLista = new ArrayList<>();
		for (Object obj : cachedList) {
			CriarRegistroPessoaResponse item;
			if (obj instanceof LinkedHashMap) {
				item = objectMapper.convertValue(obj,
						CriarRegistroPessoaResponse.class);
			} else if (obj instanceof CriarRegistroPessoaResponse) {
				item = (CriarRegistroPessoaResponse) obj;
			} else {
				continue;
			}
			if (item.getId().equals(registroAtualizado.getId())) {
				novaLista.add(registroAtualizado);
			} else {
				novaLista.add(item);
			}
		}
		redisCacheService.setCache(cacheKey, novaLista, 30, TimeUnit.MINUTES);
	}

	private void removerRegistroDoCache(UUID id) {
		List<?> cachedList = redisCacheService.getCache(cacheKey,
				CriarRegistroPessoaResponse.class);
		if (cachedList == null)
			return;

		List<CriarRegistroPessoaResponse> novaLista = new ArrayList<>();
		for (Object obj : cachedList) {
			CriarRegistroPessoaResponse item;
			if (obj instanceof LinkedHashMap) {
				item = objectMapper.convertValue(obj,
						CriarRegistroPessoaResponse.class);
			} else if (obj instanceof CriarRegistroPessoaResponse) {
				item = (CriarRegistroPessoaResponse) obj;
			} else {
				continue;
			}
			if (!item.getId().equals(id)) { // Mantém só os itens diferentes do
											// ID removido
				novaLista.add(item);
			}
		}

		redisCacheService.setCache(cacheKey, novaLista, 30, TimeUnit.MINUTES);
	}

	private CriarRegistroPessoaResponse toResponse(RegistroPessoa registro) {
		CriarRegistroPessoaResponse response = new CriarRegistroPessoaResponse();
		response.setId(registro.getId());
		response.setNome(registro.getNome());
		response.setTelefone(registro.getTelefone());
		response.setCpf(registro.getCpf());
		response.setNumero(registro.getNumero());
		response.setComplemento(registro.getComplemento());
		response.setCep(registro.getCep());
		response.setBairro(registro.getBairro());
		response.setNomeMunicipio(registro.getNomeMunicipio());
		response.setNomeEstado(registro.getNomeEstado());
		response.setLatitude(registro.getLatitude());
		response.setLongitude(registro.getLongitude());
		if (registro.getUsuario() != null) {
			ObterDadosUsuarioResponse usuario = new ObterDadosUsuarioResponse();
			usuario.setId(registro.getUsuario().getId());
			usuario.setNome(registro.getUsuario().getNome());
			usuario.setEmail(registro.getUsuario().getEmail());
			CriarPerfilResponse perfil = new CriarPerfilResponse();
			perfil.setId(registro.getUsuario().getPerfil().getId());
			perfil.setNome(registro.getUsuario().getPerfil().getNome());
			usuario.setPerfil(perfil);
			response.setUsuario(usuario);
		}
		return response;
	}
}
