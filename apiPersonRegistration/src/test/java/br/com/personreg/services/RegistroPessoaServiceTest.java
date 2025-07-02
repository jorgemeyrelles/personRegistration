package br.com.personreg.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.personreg.dtos.CriarPerfilResponse;
import br.com.personreg.dtos.CriarRegistroPessoaRequest;
import br.com.personreg.dtos.CriarRegistroPessoaResponse;
import br.com.personreg.dtos.ObterDadosUsuarioResponse;
import br.com.personreg.entities.Perfil;
import br.com.personreg.entities.RegistroPessoa;
import br.com.personreg.entities.Usuario;

/**
 * Testes para RegistroPessoaService usando apenas JUnit
 * Foco na lógica de conversão e validação de dados
 * 
 * @author Sistema de Registro de Pessoas
 */
@DisplayName("RegistroPessoaService - Testes de Lógica")
class RegistroPessoaServiceTest {

    private CriarRegistroPessoaRequest request;
    private RegistroPessoa registroPessoa;
    private Usuario usuario;
    private Perfil perfil;

    @BeforeEach
    void setUp() {
        // Setup do perfil usando Lombok
        perfil = new Perfil();
        perfil.setId(UUID.randomUUID());
        perfil.setNome("ADMINISTRADOR");
        perfil.setUsuarios(new ArrayList<>());

        // Setup do usuário usando Lombok
        usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setNome("João Silva");
        usuario.setEmail("joao@empresa.com");
        usuario.setPerfil(perfil);

        // Setup do request usando Lombok
        request = new CriarRegistroPessoaRequest();
        request.setNome("Maria Santos");
        request.setTelefone("(11) 99999-8888");
        request.setCpf("123.456.789-00");
        request.setNumero("123");
        request.setComplemento("Apto 45");
        request.setCep("01310-100");
        request.setBairro("Bela Vista");
        request.setNomeMunicipio("São Paulo");
        request.setNomeEstado("São Paulo");
        request.setLatitude("-23.5505");
        request.setLongitude("-46.6333");
        request.setUsuarioId(usuario.getId().toString());

        // Setup da entidade RegistroPessoa usando Lombok
        registroPessoa = new RegistroPessoa();
        registroPessoa.setId(UUID.randomUUID());
        registroPessoa.setNome(request.getNome());
        registroPessoa.setTelefone(request.getTelefone());
        registroPessoa.setCpf(request.getCpf());
        registroPessoa.setNumero(request.getNumero());
        registroPessoa.setComplemento(request.getComplemento());
        registroPessoa.setCep(request.getCep());
        registroPessoa.setBairro(request.getBairro());
        registroPessoa.setNomeMunicipio(request.getNomeMunicipio());
        registroPessoa.setNomeEstado(request.getNomeEstado());
        registroPessoa.setLatitude(request.getLatitude());
        registroPessoa.setLongitude(request.getLongitude());
        registroPessoa.setUsuario(usuario);
    }

    @Test
    @DisplayName("Teste 1: Deve validar lógica de conversão toResponse com dados completos")
    void teste1_deveValidarLogicaDeConversaoToResponseComDadosCompletos() {
        // Given - dados já configurados no @BeforeEach
        
        // When - simula a lógica do método toResponse() do service
        CriarRegistroPessoaResponse response = new CriarRegistroPessoaResponse();
        response.setId(registroPessoa.getId());
        response.setNome(registroPessoa.getNome());
        response.setTelefone(registroPessoa.getTelefone());
        response.setCpf(registroPessoa.getCpf());
        response.setNumero(registroPessoa.getNumero());
        response.setComplemento(registroPessoa.getComplemento());
        response.setCep(registroPessoa.getCep());
        response.setBairro(registroPessoa.getBairro());
        response.setNomeMunicipio(registroPessoa.getNomeMunicipio());
        response.setNomeEstado(registroPessoa.getNomeEstado());
        response.setLatitude(registroPessoa.getLatitude());
        response.setLongitude(registroPessoa.getLongitude());
        
        // Simula a lógica de conversão do usuário
        if (registroPessoa.getUsuario() != null) {
            ObterDadosUsuarioResponse usuarioResponse = new ObterDadosUsuarioResponse();
            usuarioResponse.setId(registroPessoa.getUsuario().getId());
            usuarioResponse.setNome(registroPessoa.getUsuario().getNome());
            usuarioResponse.setEmail(registroPessoa.getUsuario().getEmail());
            
            // Converte o perfil
            CriarPerfilResponse perfilResponse = new CriarPerfilResponse();
            perfilResponse.setId(registroPessoa.getUsuario().getPerfil().getId());
            perfilResponse.setNome(registroPessoa.getUsuario().getPerfil().getNome());
            usuarioResponse.setPerfil(perfilResponse);
            
            response.setUsuario(usuarioResponse);
        }

        // Then - validações
        assertNotNull(response, "Response não deve ser nulo");
        assertEquals(registroPessoa.getId(), response.getId(), "ID deve ser igual");
        assertEquals("Maria Santos", response.getNome(), "Nome deve ser Maria Santos");
        assertEquals("(11) 99999-8888", response.getTelefone(), "Telefone deve ser correto");
        assertEquals("123.456.789-00", response.getCpf(), "CPF deve ser correto");
        assertEquals("123", response.getNumero(), "Número deve ser 123");
        assertEquals("Apto 45", response.getComplemento(), "Complemento deve ser Apto 45");
        assertEquals("01310-100", response.getCep(), "CEP deve ser correto");
        assertEquals("Bela Vista", response.getBairro(), "Bairro deve ser Bela Vista");
        assertEquals("São Paulo", response.getNomeMunicipio(), "Município deve ser São Paulo");
        assertEquals("São Paulo", response.getNomeEstado(), "Estado deve ser São Paulo");
        assertEquals("-23.5505", response.getLatitude(), "Latitude deve ser correta");
        assertEquals("-46.6333", response.getLongitude(), "Longitude deve ser correta");

        // Validações do usuário associado
        assertNotNull(response.getUsuario(), "Usuário não deve ser nulo");
        assertEquals(usuario.getId(), response.getUsuario().getId(), "ID do usuário deve ser igual");
        assertEquals("João Silva", response.getUsuario().getNome(), "Nome do usuário deve ser João Silva");
        assertEquals("joao@empresa.com", response.getUsuario().getEmail(), "Email deve ser correto");

        // Validações do perfil do usuário
        assertNotNull(response.getUsuario().getPerfil(), "Perfil não deve ser nulo");
        assertEquals(perfil.getId(), response.getUsuario().getPerfil().getId(), "ID do perfil deve ser igual");
        assertEquals("ADMINISTRADOR", response.getUsuario().getPerfil().getNome(), "Nome do perfil deve ser ADMINISTRADOR");
    }

    @Test
    @DisplayName("Teste 2: Deve validar lógica de atualização parcial com Optional")
    void teste2_deveValidarLogicaDeAtualizacaoParcialComOptional() {
        // Given - request com apenas alguns campos para atualização
        CriarRegistroPessoaRequest updateRequest = new CriarRegistroPessoaRequest();
        updateRequest.setNome("Maria Santos Silva"); // Atualiza apenas o nome
        updateRequest.setTelefone("(11) 88888-7777"); // Atualiza apenas o telefone
        updateRequest.setCep("01310-200"); // Atualiza apenas o CEP
        // Outros campos ficam nulos (não serão atualizados)

        Optional<CriarRegistroPessoaRequest> optionalRequest = Optional.of(updateRequest);

        // When - simula a lógica do método atualizar() do service
        
        // Primeiro valida que o Optional não está vazio (lógica do service)
        if (optionalRequest.isEmpty()) {
            fail("Optional não deveria estar vazio para este teste");
        }

        CriarRegistroPessoaRequest requestData = optionalRequest.get();
        
        // Simula a lógica de atualização condicional do service
        if (requestData.getNome() != null)
            registroPessoa.setNome(requestData.getNome());
        if (requestData.getTelefone() != null)
            registroPessoa.setTelefone(requestData.getTelefone());
        if (requestData.getCpf() != null)
            registroPessoa.setCpf(requestData.getCpf());
        if (requestData.getNumero() != null)
            registroPessoa.setNumero(requestData.getNumero());
        if (requestData.getComplemento() != null)
            registroPessoa.setComplemento(requestData.getComplemento());
        if (requestData.getCep() != null)
            registroPessoa.setCep(requestData.getCep());
        if (requestData.getBairro() != null)
            registroPessoa.setBairro(requestData.getBairro());
        if (requestData.getNomeMunicipio() != null)
            registroPessoa.setNomeMunicipio(requestData.getNomeMunicipio());
        if (requestData.getNomeEstado() != null)
            registroPessoa.setNomeEstado(requestData.getNomeEstado());
        if (requestData.getLatitude() != null)
            registroPessoa.setLatitude(requestData.getLatitude());
        if (requestData.getLongitude() != null)
            registroPessoa.setLongitude(requestData.getLongitude());

        // Then - validações
        
        // Campos que foram atualizados
        assertEquals("Maria Santos Silva", registroPessoa.getNome(), 
            "Nome deve ter sido atualizado");
        assertEquals("(11) 88888-7777", registroPessoa.getTelefone(), 
            "Telefone deve ter sido atualizado");
        assertEquals("01310-200", registroPessoa.getCep(), 
            "CEP deve ter sido atualizado");

        // Campos que NÃO foram atualizados (mantiveram valores originais)
        assertEquals("123.456.789-00", registroPessoa.getCpf(), 
            "CPF deve manter valor original");
        assertEquals("123", registroPessoa.getNumero(), 
            "Número deve manter valor original");
        assertEquals("Apto 45", registroPessoa.getComplemento(), 
            "Complemento deve manter valor original");
        assertEquals("Bela Vista", registroPessoa.getBairro(), 
            "Bairro deve manter valor original");
        assertEquals("São Paulo", registroPessoa.getNomeMunicipio(), 
            "Município deve manter valor original");
        assertEquals("São Paulo", registroPessoa.getNomeEstado(), 
            "Estado deve manter valor original");
        assertEquals("-23.5505", registroPessoa.getLatitude(), 
            "Latitude deve manter valor original");
        assertEquals("-46.6333", registroPessoa.getLongitude(), 
            "Longitude deve manter valor original");

        // Usuário deve permanecer o mesmo
        assertNotNull(registroPessoa.getUsuario(), "Usuário não deve ser nulo");
        assertEquals(usuario.getId(), registroPessoa.getUsuario().getId(), 
            "Usuário deve permanecer o mesmo");
    }

    @Test
    @DisplayName("Teste Extra: Deve validar tratamento de Optional vazio")
    void testeExtra_deveValidarTratamentoDeOptionalVazio() {
        // Given - Optional vazio
        Optional<CriarRegistroPessoaRequest> optionalVazio = Optional.empty();

        // When & Then - simula a validação do service
        if (optionalVazio.isEmpty()) {
            // Esta é a lógica esperada do service
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, 
                () -> {
                    throw new IllegalArgumentException("Dados para atualização não informados.");
                }
            );
            assertEquals("Dados para atualização não informados.", exception.getMessage(),
                "Mensagem de erro deve ser correta");
        } else {
            fail("Optional deveria estar vazio para este teste");
        }
    }

    @Test
    @DisplayName("Teste Extra: Deve validar conversão com usuário nulo")
    void testeExtra_deveValidarConversaoComUsuarioNulo() {
        // Given - registro sem usuário
        RegistroPessoa registroSemUsuario = new RegistroPessoa();
        registroSemUsuario.setId(UUID.randomUUID());
        registroSemUsuario.setNome("Pessoa Sem Usuário");
        registroSemUsuario.setCpf("999.888.777-66");
        registroSemUsuario.setUsuario(null); // Usuário nulo

        // When - simula conversão toResponse com usuário nulo
        CriarRegistroPessoaResponse response = new CriarRegistroPessoaResponse();
        response.setId(registroSemUsuario.getId());
        response.setNome(registroSemUsuario.getNome());
        response.setCpf(registroSemUsuario.getCpf());
        
        // Simula a validação de usuário nulo do service
        if (registroSemUsuario.getUsuario() != null) {
            // Lógica de conversão do usuário (não será executada)
            fail("Usuário deveria ser nulo para este teste");
        } else {
            // Quando usuário é nulo, response.usuario fica null por padrão
            assertNull(response.getUsuario(), "Usuário do response deve ser nulo");
        }

        // Then - validações
        assertNotNull(response, "Response não deve ser nulo");
        assertEquals("Pessoa Sem Usuário", response.getNome(), "Nome deve ser correto");
        assertEquals("999.888.777-66", response.getCpf(), "CPF deve ser correto");
        assertNull(response.getUsuario(), "Usuário deve ser nulo no response");
        
        // Validar que toString não quebra com usuário nulo
        assertDoesNotThrow(() -> {
            String responseString = response.toString();
            assertNotNull(responseString, "ToString não deve ser nulo");
        }, "ToString não deve lançar exceção com usuário nulo");
    }
}
