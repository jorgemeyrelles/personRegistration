package br.com.personreg.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.personreg.components.JwtTokenComponent;
import br.com.personreg.components.RabbitMQProducerComponent;
import br.com.personreg.components.SHA256Component;
import br.com.personreg.dtos.AutenticarUsuarioRequest;
import br.com.personreg.dtos.AutenticarUsuarioResponse;
import br.com.personreg.dtos.CriarPerfilResponse;
import br.com.personreg.dtos.CriarUsuarioRequest;
import br.com.personreg.dtos.CriarUsuarioResponse;
import br.com.personreg.dtos.MensagemUsuarioResponse;
import br.com.personreg.dtos.ObterDadosUsuarioResponse;
import br.com.personreg.entities.Perfil;
import br.com.personreg.entities.Usuario;
import br.com.personreg.exceptions.AcessoNegadoException;
import br.com.personreg.exceptions.EmailJaCadastradoException;
import br.com.personreg.repositories.PerfilRepository;
import br.com.personreg.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired // injeção de dependência
	private UsuarioRepository usuarioRepository;

	@Autowired // injeção de dependência
	private PerfilRepository perfilRepository;

	@Autowired // injeção de dependência
	private SHA256Component sha256Component;

	@Autowired // injeção de dependência
	private JwtTokenComponent jwtTokenComponent;

	@Autowired // injeção de dependência
	private RabbitMQProducerComponent rabbitMQProducerComponent;

	/*
	 * Método para criar um usuário no sistema
	 */
	public CriarUsuarioResponse criar(CriarUsuarioRequest request)
			throws Exception {

		// verificar se o já existe um usuário cadastrado
		// no banco de dados com o email informado
		if (usuarioRepository.findByEmail(request.getEmail()) != null)
			throw new EmailJaCadastradoException();

		// capturar os dados do usuário enviados na requisição (REQUEST)
		Usuario usuario = new Usuario();

		usuario.setId(UUID.randomUUID());
		usuario.setNome(request.getNome());
		usuario.setEmail(request.getEmail());
		usuario.setSenha(sha256Component.hash(request.getSenha()));
		Perfil perfil = perfilRepository
				.findById(UUID.fromString(request.getPerfilId()))
				.orElseThrow(() -> new Exception("Perfil não encontrado"));
		usuario.setPerfil(perfil);

		// gravar o usuário no banco de dados
		usuarioRepository.save(usuario);

		// escrever uma mensagem de boas vindas para o usuário
		MensagemUsuarioResponse mensagem = new MensagemUsuarioResponse();
		mensagem.setEmailDestinatario(request.getEmail());
		mensagem.setAssunto("Confirmação de registro");
		mensagem.setTexto("Olá, " + usuario.getNome()
				+ ". Parabéns, seu registro foi realizado com sucesso!");

		// enviando a mensagem para a fila
		rabbitMQProducerComponent.send(mensagem);

		// retornando os dados do usuário
		CriarUsuarioResponse response = new CriarUsuarioResponse();
		response.setId(usuario.getId());
		response.setNome(usuario.getNome());
		response.setEmail(usuario.getEmail());
		response.setDataHoraCadastro(new Date());

		return response;
	}

	/*
	 * Método para autenticar um usuário no sistema
	 */
	public AutenticarUsuarioResponse autenticar(
			AutenticarUsuarioRequest request) throws Exception {

		// consultando o usuário no banco de dados através do email e da senha
		Usuario usuario = usuarioRepository.findByEmailAndSenha(
				request.getEmail(), sha256Component.hash(request.getSenha()));

		// verificando se o usuário não foi encontrado
		if (usuario == null)
			throw new AcessoNegadoException();

		// retornando os dados do usuário autenticado
		AutenticarUsuarioResponse response = new AutenticarUsuarioResponse();
		response.setId(usuario.getId());
		response.setNome(usuario.getNome());
		response.setEmail(usuario.getEmail());
		response.setDataHoraAcesso(new Date());
		response.setTokenAcesso(jwtTokenComponent.generateToken(usuario));
		response.setDataHoraExpiracao(jwtTokenComponent.getExpirationDate());
		response.setNomePerfil(usuario.getPerfil().getNome());

		return response;
	}

	/*
	 * Método para consultar os dados de um usuário através do email
	 */
	public ObterDadosUsuarioResponse obterDados(String token) throws Exception {

		// extrair o email do usuário contido no TOKEN
		String email = jwtTokenComponent.getEmailFromToken(token);

		// consultando os dados do usuário no banco através do email
		Usuario usuario = usuarioRepository.findByEmail(email);

		// verificando se o usuário não foi encontrado
		if (usuario == null)
			throw new AcessoNegadoException();

		// retornando os dados do usuário
		ObterDadosUsuarioResponse response = new ObterDadosUsuarioResponse();
		response.setId(usuario.getId());
		response.setNome(usuario.getNome());
		response.setEmail(usuario.getEmail());
		CriarPerfilResponse perfil = new CriarPerfilResponse();
		perfil.setId(usuario.getPerfil().getId());
		perfil.setNome(usuario.getPerfil().getNome());
		response.setPerfil(perfil);

		return response;
	}

	public List<ObterDadosUsuarioResponse> buscarTodosUsuarios()
			throws Exception {
		// extrair o email do usuário contido no TOKEN
		// String email = jwtTokenComponent.getEmailFromToken(token);

		// consultando os dados do usuário no banco através do email
		// Usuario usuarioCheck = usuarioRepository.findByEmail(email);

		// verificando se o usuário não foi encontrado
		//if (usuarioCheck == null) {
		//	throw new AcessoNegadoException();
		//}

		List<Usuario> usuarios = usuarioRepository.findAll();
		List<ObterDadosUsuarioResponse> responses = new ArrayList<>();

		for (Usuario usuario : usuarios) {
			ObterDadosUsuarioResponse response = new ObterDadosUsuarioResponse();
			response.setId(usuario.getId());
			response.setNome(usuario.getNome());
			response.setEmail(usuario.getEmail());

			CriarPerfilResponse perfil = new CriarPerfilResponse();
			if (usuario.getPerfil() != null) {
				perfil.setId(usuario.getPerfil().getId());
				perfil.setNome(usuario.getPerfil().getNome());
			}
			response.setPerfil(perfil);

			responses.add(response);
		}
		return responses;
	}

	public void deletarUsuario(UUID id) throws Exception {
		// Busca o usuário pelo ID
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new Exception("Usuário não encontrado"));

		// Deleta o usuário do banco de dados
		usuarioRepository.delete(usuario);
	}
}
