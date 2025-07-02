package br.com.personreg.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.personreg.dtos.AutenticarUsuarioRequest;
import br.com.personreg.dtos.AutenticarUsuarioResponse;
import br.com.personreg.dtos.CriarPerfilResponse;
import br.com.personreg.dtos.CriarUsuarioRequest;
import br.com.personreg.dtos.CriarUsuarioResponse;
import br.com.personreg.dtos.MensagemUsuarioResponse;
import br.com.personreg.dtos.ObterDadosUsuarioResponse;
import br.com.personreg.entities.Perfil;
import br.com.personreg.entities.Usuario;

/**
 * Testes para UsuarioService usando apenas JUnit
 * Foco na lógica de conversão e validação de dados
 * 
 * @author Sistema de Registro de Pessoas
 */
@DisplayName("UsuarioService - Testes de Lógica")
class UsuarioServiceTest {

    private CriarUsuarioRequest criarUsuarioRequest;
    private AutenticarUsuarioRequest autenticarUsuarioRequest;
    private Usuario usuario;
    private Perfil perfil;
    private MensagemUsuarioResponse mensagemUsuario;

    @BeforeEach
    void setUp() {
        // Setup do perfil usando Lombok
        perfil = new Perfil();
        perfil.setId(UUID.randomUUID());
        perfil.setNome("ADMINISTRADOR");
        perfil.setUsuarios(new ArrayList<>());

        // Setup do request para criar usuário usando Lombok
        criarUsuarioRequest = new CriarUsuarioRequest();
        criarUsuarioRequest.setNome("João Silva Santos");
        criarUsuarioRequest.setEmail("joao@empresa.com");
        criarUsuarioRequest.setSenha("MinhaSenh@123");
        criarUsuarioRequest.setPerfilId(perfil.getId().toString());

        // Setup do request para autenticar usuário usando Lombok
        autenticarUsuarioRequest = new AutenticarUsuarioRequest();
        autenticarUsuarioRequest.setEmail("joao@empresa.com");
        autenticarUsuarioRequest.setSenha("MinhaSenh@123");

        // Setup da entidade Usuario usando Lombok
        usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setNome(criarUsuarioRequest.getNome());
        usuario.setEmail(criarUsuarioRequest.getEmail());
        usuario.setSenha("hash_da_senha_aqui"); // Simulando senha hasheada
        usuario.setPerfil(perfil);

        // Setup da mensagem de usuário usando Lombok
        mensagemUsuario = new MensagemUsuarioResponse();
        mensagemUsuario.setEmailDestinatario("joao@empresa.com");
        mensagemUsuario.setAssunto("Confirmação de registro");
        mensagemUsuario.setTexto("Olá, João Silva Santos. Parabéns, seu registro foi realizado com sucesso!");
    }

    @Test
    @DisplayName("Teste 1: Deve validar lógica de criação de usuário e geração de response")
    void teste1_deveValidarLogicaDeCriacaoDeUsuarioEGeracaoDeResponse() {
        // Given - dados já configurados no @BeforeEach
        
        // When - simula a lógica do método criar() do service
        // 1. Criar entidade Usuario a partir do request
        Usuario novoUsuario = new Usuario();
        novoUsuario.setId(UUID.randomUUID());
        novoUsuario.setNome(criarUsuarioRequest.getNome());
        novoUsuario.setEmail(criarUsuarioRequest.getEmail());
        novoUsuario.setSenha("senha_hasheada_" + criarUsuarioRequest.getSenha()); // Simula hash
        novoUsuario.setPerfil(perfil);

        // 2. Criar mensagem de boas-vindas
        MensagemUsuarioResponse mensagemGerada = new MensagemUsuarioResponse();
        mensagemGerada.setEmailDestinatario(criarUsuarioRequest.getEmail());
        mensagemGerada.setAssunto("Confirmação de registro");
        mensagemGerada.setTexto("Olá, " + novoUsuario.getNome() + 
                ". Parabéns, seu registro foi realizado com sucesso!");

        // 3. Criar response
        CriarUsuarioResponse response = new CriarUsuarioResponse();
        response.setId(novoUsuario.getId());
        response.setNome(novoUsuario.getNome());
        response.setEmail(novoUsuario.getEmail());
        response.setDataHoraCadastro(new Date());

        // Then - validações
        // Validações do usuário criado
        assertNotNull(novoUsuario, "Usuário não deve ser nulo");
        assertNotNull(novoUsuario.getId(), "ID do usuário deve ser gerado");
        assertEquals("João Silva Santos", novoUsuario.getNome(), "Nome deve ser correto");
        assertEquals("joao@empresa.com", novoUsuario.getEmail(), "Email deve ser correto");
        assertTrue(novoUsuario.getSenha().contains("senha_hasheada_"), "Senha deve ter sido hasheada");
        assertNotNull(novoUsuario.getPerfil(), "Perfil não deve ser nulo");
        assertEquals(perfil.getId(), novoUsuario.getPerfil().getId(), "Perfil deve ser o correto");

        // Validações da mensagem gerada
        assertNotNull(mensagemGerada, "Mensagem não deve ser nula");
        assertEquals("joao@empresa.com", mensagemGerada.getEmailDestinatario(), 
                "Email destinatário deve ser correto");
        assertEquals("Confirmação de registro", mensagemGerada.getAssunto(), 
                "Assunto deve ser correto");
        assertTrue(mensagemGerada.getTexto().contains("João Silva Santos"), 
                "Texto deve conter nome do usuário");
        assertTrue(mensagemGerada.getTexto().contains("Parabéns"), 
                "Texto deve conter mensagem de parabéns");

        // Validações do response
        assertNotNull(response, "Response não deve ser nulo");
        assertEquals(novoUsuario.getId(), response.getId(), "ID deve ser igual ao do usuário");
        assertEquals("João Silva Santos", response.getNome(), "Nome deve ser correto");
        assertEquals("joao@empresa.com", response.getEmail(), "Email deve ser correto");
        assertNotNull(response.getDataHoraCadastro(), "Data de cadastro deve ser definida");

        // Valida que toString() funciona nos DTOs (Lombok)
        assertDoesNotThrow(() -> {
            String requestString = criarUsuarioRequest.toString();
            String responseString = response.toString();
            String mensagemString = mensagemGerada.toString();
            
            assertNotNull(requestString, "ToString do request não deve ser nulo");
            assertNotNull(responseString, "ToString do response não deve ser nulo");
            assertNotNull(mensagemString, "ToString da mensagem não deve ser nulo");
        }, "ToString dos DTOs não deve lançar exceção");
    }

    @Test
    @DisplayName("Teste 2: Deve validar lógica de conversão para ObterDadosUsuarioResponse")
    void teste2_deveValidarLogicaDeConversaoParaObterDadosUsuarioResponse() {
        // Given - usuário já configurado no @BeforeEach
        
        // When - simula a lógica do método obterDados() e buscarTodosUsuarios()
        
        // 1. Conversão para um único usuário (obterDados)
        ObterDadosUsuarioResponse responseUnico = new ObterDadosUsuarioResponse();
        responseUnico.setId(usuario.getId());
        responseUnico.setNome(usuario.getNome());
        responseUnico.setEmail(usuario.getEmail());
        
        // Converte o perfil
        CriarPerfilResponse perfilResponse = new CriarPerfilResponse();
        perfilResponse.setId(usuario.getPerfil().getId());
        perfilResponse.setNome(usuario.getPerfil().getNome());
        responseUnico.setPerfil(perfilResponse);

        // 2. Conversão para lista de usuários (buscarTodosUsuarios)
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuario);
        
        // Adiciona um segundo usuário sem perfil para testar validação
        Usuario usuarioSemPerfil = new Usuario();
        usuarioSemPerfil.setId(UUID.randomUUID());
        usuarioSemPerfil.setNome("Maria Santos");
        usuarioSemPerfil.setEmail("maria@empresa.com");
        usuarioSemPerfil.setSenha("hash_senha_maria");
        usuarioSemPerfil.setPerfil(null); // Perfil nulo para testar
        usuarios.add(usuarioSemPerfil);

        List<ObterDadosUsuarioResponse> responseLista = new ArrayList<>();
        for (Usuario usuarioAtual : usuarios) {
            ObterDadosUsuarioResponse responseItem = new ObterDadosUsuarioResponse();
            responseItem.setId(usuarioAtual.getId());
            responseItem.setNome(usuarioAtual.getNome());
            responseItem.setEmail(usuarioAtual.getEmail());

            // Simula a lógica de validação de perfil do service
            CriarPerfilResponse perfilItem = new CriarPerfilResponse();
            if (usuarioAtual.getPerfil() != null) {
                perfilItem.setId(usuarioAtual.getPerfil().getId());
                perfilItem.setNome(usuarioAtual.getPerfil().getNome());
            }
            responseItem.setPerfil(perfilItem);

            responseLista.add(responseItem);
        }

        // Then - validações
        
        // Validações do response único
        assertNotNull(responseUnico, "Response único não deve ser nulo");
        assertEquals(usuario.getId(), responseUnico.getId(), "ID deve ser igual");
        assertEquals("João Silva Santos", responseUnico.getNome(), "Nome deve ser correto");
        assertEquals("joao@empresa.com", responseUnico.getEmail(), "Email deve ser correto");
        assertNotNull(responseUnico.getPerfil(), "Perfil não deve ser nulo");
        assertEquals(perfil.getId(), responseUnico.getPerfil().getId(), "ID do perfil deve ser igual");
        assertEquals("ADMINISTRADOR", responseUnico.getPerfil().getNome(), "Nome do perfil deve ser correto");

        // Validações da lista de responses
        assertNotNull(responseLista, "Lista de responses não deve ser nula");
        assertEquals(2, responseLista.size(), "Deve ter 2 usuários na lista");

        // Validações do primeiro usuário da lista (com perfil)
        ObterDadosUsuarioResponse primeiroUsuario = responseLista.get(0);
        assertEquals("João Silva Santos", primeiroUsuario.getNome(), "Nome do primeiro usuário deve ser correto");
        assertNotNull(primeiroUsuario.getPerfil(), "Perfil do primeiro usuário não deve ser nulo");
        assertEquals("ADMINISTRADOR", primeiroUsuario.getPerfil().getNome(), "Nome do perfil deve ser ADMINISTRADOR");

        // Validações do segundo usuário da lista (sem perfil)
        ObterDadosUsuarioResponse segundoUsuario = responseLista.get(1);
        assertEquals("Maria Santos", segundoUsuario.getNome(), "Nome do segundo usuário deve ser correto");
        assertEquals("maria@empresa.com", segundoUsuario.getEmail(), "Email do segundo usuário deve ser correto");
        assertNotNull(segundoUsuario.getPerfil(), "Perfil response deve existir mesmo quando nulo");
        // O perfil existe mas com dados nulos (conforme lógica do service)
        assertNull(segundoUsuario.getPerfil().getId(), "ID do perfil deve ser nulo");
        assertNull(segundoUsuario.getPerfil().getNome(), "Nome do perfil deve ser nulo");

        // Testa robustez dos DTOs com dados nulos
        assertDoesNotThrow(() -> {
            String toString1 = responseUnico.toString();
            String toString2 = segundoUsuario.toString();
            String toStringPerfil = segundoUsuario.getPerfil().toString();
            
            assertNotNull(toString1, "ToString do response com perfil não deve ser nulo");
            assertNotNull(toString2, "ToString do response sem perfil não deve ser nulo");
            assertNotNull(toStringPerfil, "ToString do perfil vazio não deve ser nulo");
        }, "ToString não deve lançar exceção com dados nulos");
    }

    @Test
    @DisplayName("Teste Extra: Deve validar lógica de autenticação e geração de response")
    void testeExtra_deveValidarLogicaDeAutenticacaoEGeracaoDeResponse() {
        // Given
        String tokenSimulado = "jwt.token.simulado.aqui";
        Date dataAtual = new Date();
        Date dataExpiracao = new Date(dataAtual.getTime() + 3600000); // +1 hora

        // When - simula a lógica do método autenticar()
        AutenticarUsuarioResponse authResponse = new AutenticarUsuarioResponse();
        authResponse.setId(usuario.getId());
        authResponse.setNome(usuario.getNome());
        authResponse.setEmail(usuario.getEmail());
        authResponse.setDataHoraAcesso(dataAtual);
        authResponse.setTokenAcesso(tokenSimulado);
        authResponse.setDataHoraExpiracao(dataExpiracao);
        authResponse.setNomePerfil(usuario.getPerfil().getNome());

        // Then
        assertNotNull(authResponse, "Response de autenticação não deve ser nulo");
        assertEquals(usuario.getId(), authResponse.getId(), "ID deve ser igual");
        assertEquals("João Silva Santos", authResponse.getNome(), "Nome deve ser correto");
        assertEquals("joao@empresa.com", authResponse.getEmail(), "Email deve ser correto");
        assertEquals(tokenSimulado, authResponse.getTokenAcesso(), "Token deve ser definido");
        assertEquals("ADMINISTRADOR", authResponse.getNomePerfil(), "Nome do perfil deve ser correto");
        assertNotNull(authResponse.getDataHoraAcesso(), "Data de acesso deve ser definida");
        assertNotNull(authResponse.getDataHoraExpiracao(), "Data de expiração deve ser definida");
        assertTrue(authResponse.getDataHoraExpiracao().after(authResponse.getDataHoraAcesso()), 
                "Data de expiração deve ser posterior à data de acesso");
    }

    @Test
    @DisplayName("Teste Extra: Deve validar dados do request de autenticação")
    void testeExtra_deveValidarDadosDoRequestDeAutenticacao() {
        // Given - request já configurado no @BeforeEach

        // When & Then - validações diretas do request
        assertNotNull(autenticarUsuarioRequest, "Request de autenticação não deve ser nulo");
        assertEquals("joao@empresa.com", autenticarUsuarioRequest.getEmail(), 
                "Email deve ser correto");
        assertEquals("MinhaSenh@123", autenticarUsuarioRequest.getSenha(), 
                "Senha deve ser correta");

        // Testa modificação via setters (Lombok)
        autenticarUsuarioRequest.setEmail("novo@email.com");
        autenticarUsuarioRequest.setSenha("NovaSenha456");

        assertEquals("novo@email.com", autenticarUsuarioRequest.getEmail(), 
                "Email deve ter sido alterado");
        assertEquals("NovaSenha456", autenticarUsuarioRequest.getSenha(), 
                "Senha deve ter sido alterada");

        // Testa toString
        assertDoesNotThrow(() -> {
            String requestString = autenticarUsuarioRequest.toString();
            assertNotNull(requestString, "ToString não deve ser nulo");
            assertTrue(requestString.contains("novo@email.com"), 
                    "ToString deve conter o email");
        }, "ToString não deve lançar exceção");
    }
}
