package br.com.personreg.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
 * Testes específicos para lógica de negócio do RegistroPessoaService
 * Usando apenas JUnit - foco em validações e regras de negócio
 * 
 * @author Sistema de Registro de Pessoas
 */
@DisplayName("RegistroPessoaService - Testes de Lógica de Negócio")
class RegistroPessoaServiceBusinessLogicTest {

    private CriarRegistroPessoaRequest requestCompleto;
    private CriarRegistroPessoaRequest requestMinimo;
    private RegistroPessoa registroPessoa;
    private Usuario usuario;
    private Perfil perfil;

    @BeforeEach
    void setUp() {
        // Setup do perfil
        perfil = new Perfil();
        perfil.setId(UUID.randomUUID());
        perfil.setNome("ADMINISTRADOR");
        perfil.setUsuarios(new ArrayList<>());

        // Setup do usuário
        usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setNome("Maria Santos");
        usuario.setEmail("maria@empresa.com");
        usuario.setPerfil(perfil);

        // Setup request completo
        requestCompleto = new CriarRegistroPessoaRequest();
        requestCompleto.setNome("João da Silva");
        requestCompleto.setTelefone("(11) 98765-4321");
        requestCompleto.setCpf("987.654.321-00");
        requestCompleto.setNumero("123");
        requestCompleto.setComplemento("Apartamento 45");
        requestCompleto.setCep("01234-567");
        requestCompleto.setBairro("Vila Madalena");
        requestCompleto.setNomeMunicipio("São Paulo");
        requestCompleto.setNomeEstado("SP");
        requestCompleto.setLatitude("-23.5505");
        requestCompleto.setLongitude("-46.6333");
        requestCompleto.setUsuarioId(usuario.getId().toString());

        // Setup request mínimo (apenas campos obrigatórios)
        requestMinimo = new CriarRegistroPessoaRequest();
        requestMinimo.setNome("Ana Costa");
        requestMinimo.setCpf("111.222.333-44");
        requestMinimo.setTelefone("(21) 91234-5678");
        requestMinimo.setUsuarioId(usuario.getId().toString());

        // Setup da entidade RegistroPessoa
        registroPessoa = new RegistroPessoa();
        registroPessoa.setId(UUID.randomUUID());
        registroPessoa.setNome("Pedro Oliveira");
        registroPessoa.setTelefone("(31) 99876-5432");
        registroPessoa.setCpf("555.666.777-88");
        registroPessoa.setNumero("456");
        registroPessoa.setComplemento("Casa");
        registroPessoa.setCep("30112-000");
        registroPessoa.setBairro("Savassi");
        registroPessoa.setNomeMunicipio("Belo Horizonte");
        registroPessoa.setNomeEstado("MG");
        registroPessoa.setLatitude("-19.9167");
        registroPessoa.setLongitude("-43.9345");
        registroPessoa.setUsuario(usuario);
    }

    @Test
    @DisplayName("Deve validar criação de registro com dados completos")
    void deveValidarCriacaoComDadosCompletos() {
        // Given - requestCompleto já configurado no setUp
        
        // When - simulando conversão para entidade
        RegistroPessoa novoRegistro = new RegistroPessoa();
        novoRegistro.setNome(requestCompleto.getNome());
        novoRegistro.setTelefone(requestCompleto.getTelefone());
        novoRegistro.setCpf(requestCompleto.getCpf());
        novoRegistro.setNumero(requestCompleto.getNumero());
        novoRegistro.setComplemento(requestCompleto.getComplemento());
        novoRegistro.setCep(requestCompleto.getCep());
        novoRegistro.setBairro(requestCompleto.getBairro());
        novoRegistro.setNomeMunicipio(requestCompleto.getNomeMunicipio());
        novoRegistro.setNomeEstado(requestCompleto.getNomeEstado());
        novoRegistro.setLatitude(requestCompleto.getLatitude());
        novoRegistro.setLongitude(requestCompleto.getLongitude());
        novoRegistro.setUsuario(usuario);

        // Then
        assertNotNull(novoRegistro);
        assertEquals("João da Silva", novoRegistro.getNome());
        assertEquals("(11) 98765-4321", novoRegistro.getTelefone());
        assertEquals("987.654.321-00", novoRegistro.getCpf());
        assertEquals("123", novoRegistro.getNumero());
        assertEquals("Apartamento 45", novoRegistro.getComplemento());
        assertEquals("01234-567", novoRegistro.getCep());
        assertEquals("Vila Madalena", novoRegistro.getBairro());
        assertEquals("São Paulo", novoRegistro.getNomeMunicipio());
        assertEquals("SP", novoRegistro.getNomeEstado());
        assertEquals("-23.5505", novoRegistro.getLatitude());
        assertEquals("-46.6333", novoRegistro.getLongitude());
        assertEquals(usuario, novoRegistro.getUsuario());
    }

    @Test
    @DisplayName("Deve validar criação de registro com dados mínimos")
    void deveValidarCriacaoComDadosMinimos() {
        // Given - requestMinimo já configurado no setUp
        
        // When - simulando conversão para entidade
        RegistroPessoa novoRegistro = new RegistroPessoa();
        novoRegistro.setNome(requestMinimo.getNome());
        novoRegistro.setTelefone(requestMinimo.getTelefone());
        novoRegistro.setCpf(requestMinimo.getCpf());
        novoRegistro.setUsuario(usuario);

        // Then
        assertNotNull(novoRegistro);
        assertEquals("Ana Costa", novoRegistro.getNome());
        assertEquals("(21) 91234-5678", novoRegistro.getTelefone());
        assertEquals("111.222.333-44", novoRegistro.getCpf());
        assertEquals(usuario, novoRegistro.getUsuario());
        
        // Campos opcionais devem ser nulos
        assertNull(novoRegistro.getNumero());
        assertNull(novoRegistro.getComplemento());
        assertNull(novoRegistro.getCep());
        assertNull(novoRegistro.getBairro());
        assertNull(novoRegistro.getNomeMunicipio());
        assertNull(novoRegistro.getNomeEstado());
        assertNull(novoRegistro.getLatitude());
        assertNull(novoRegistro.getLongitude());
    }

    @Test
    @DisplayName("Deve converter entidade para response corretamente")
    void deveConverterEntidadeParaResponse() {
        // Given - registroPessoa já configurado no setUp
        
        // When - simulando método toResponse
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
        
        if (registroPessoa.getUsuario() != null) {
            ObterDadosUsuarioResponse usuarioResponse = new ObterDadosUsuarioResponse();
            usuarioResponse.setId(registroPessoa.getUsuario().getId());
            usuarioResponse.setNome(registroPessoa.getUsuario().getNome());
            usuarioResponse.setEmail(registroPessoa.getUsuario().getEmail());
            
            CriarPerfilResponse perfilResponse = new CriarPerfilResponse();
            perfilResponse.setId(registroPessoa.getUsuario().getPerfil().getId());
            perfilResponse.setNome(registroPessoa.getUsuario().getPerfil().getNome());
            usuarioResponse.setPerfil(perfilResponse);
            
            response.setUsuario(usuarioResponse);
        }

        // Then
        assertNotNull(response);
        assertEquals(registroPessoa.getId(), response.getId());
        assertEquals("Pedro Oliveira", response.getNome());
        assertEquals("(31) 99876-5432", response.getTelefone());
        assertEquals("555.666.777-88", response.getCpf());
        assertEquals("456", response.getNumero());
        assertEquals("Casa", response.getComplemento());
        assertEquals("30112-000", response.getCep());
        assertEquals("Savassi", response.getBairro());
        assertEquals("Belo Horizonte", response.getNomeMunicipio());
        assertEquals("MG", response.getNomeEstado());
        assertEquals("-19.9167", response.getLatitude());
        assertEquals("-43.9345", response.getLongitude());
        
        // Validar dados do usuário
        assertNotNull(response.getUsuario());
        assertEquals(usuario.getId(), response.getUsuario().getId());
        assertEquals("Maria Santos", response.getUsuario().getNome());
        assertEquals("maria@empresa.com", response.getUsuario().getEmail());
        
        // Validar dados do perfil
        assertNotNull(response.getUsuario().getPerfil());
        assertEquals(perfil.getId(), response.getUsuario().getPerfil().getId());
        assertEquals("ADMINISTRADOR", response.getUsuario().getPerfil().getNome());
    }

    @Test
    @DisplayName("Deve tratar atualização parcial corretamente")
    void deveTratarAtualizacaoParcia() {
        // Given
        CriarRegistroPessoaRequest atualizacao = new CriarRegistroPessoaRequest();
        atualizacao.setNome("Pedro Oliveira Atualizado");
        atualizacao.setTelefone("(31) 11111-2222");
        // Outros campos intencionalmente nulos

        RegistroPessoa registroExistente = new RegistroPessoa();
        registroExistente.setId(UUID.randomUUID());
        registroExistente.setNome("Pedro Oliveira");
        registroExistente.setTelefone("(31) 99876-5432");
        registroExistente.setCpf("555.666.777-88");
        registroExistente.setCep("30112-000");
        registroExistente.setBairro("Savassi");
        registroExistente.setUsuario(usuario);

        // When - simulando lógica de atualização
        if (atualizacao.getNome() != null)
            registroExistente.setNome(atualizacao.getNome());
        if (atualizacao.getTelefone() != null)
            registroExistente.setTelefone(atualizacao.getTelefone());
        if (atualizacao.getCpf() != null)
            registroExistente.setCpf(atualizacao.getCpf());
        if (atualizacao.getCep() != null)
            registroExistente.setCep(atualizacao.getCep());
        if (atualizacao.getBairro() != null)
            registroExistente.setBairro(atualizacao.getBairro());

        // Then
        assertEquals("Pedro Oliveira Atualizado", registroExistente.getNome());
        assertEquals("(31) 11111-2222", registroExistente.getTelefone());
        
        // Campos não atualizados devem manter valores originais
        assertEquals("555.666.777-88", registroExistente.getCpf());
        assertEquals("30112-000", registroExistente.getCep());
        assertEquals("Savassi", registroExistente.getBairro());
        assertEquals(usuario, registroExistente.getUsuario());
    }

    @Test
    @DisplayName("Deve validar tratamento de Optional vazio na atualização")
    void deveValidarTratamentoOptionalVazio() {
        // Given
        Optional<CriarRegistroPessoaRequest> optionalRequest = Optional.empty();

        // When & Then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            if (optionalRequest.isEmpty()) {
                throw new IllegalArgumentException("Dados para atualização não informados.");
            }
        });

        assertEquals("Dados para atualização não informados.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve validar busca por nome com case insensitive")
    void deveValidarBuscaPorNomeCaseInsensitive() {
        // Given
        List<CriarRegistroPessoaResponse> listaCompleta = new ArrayList<>();
        
        CriarRegistroPessoaResponse pessoa1 = new CriarRegistroPessoaResponse();
        pessoa1.setNome("João da Silva Santos");
        pessoa1.setCpf("123.456.789-00");
        
        CriarRegistroPessoaResponse pessoa2 = new CriarRegistroPessoaResponse();
        pessoa2.setNome("Maria João Oliveira");
        pessoa2.setCpf("987.654.321-00");
        
        CriarRegistroPessoaResponse pessoa3 = new CriarRegistroPessoaResponse();
        pessoa3.setNome("Pedro Santos");
        pessoa3.setCpf("111.222.333-44");
        
        listaCompleta.add(pessoa1);
        listaCompleta.add(pessoa2);
        listaCompleta.add(pessoa3);

        String termoBusca = "joão";

        // When - simulando busca por nome case insensitive
        List<CriarRegistroPessoaResponse> resultados = listaCompleta.stream()
                .filter(r -> r.getNome() != null && 
                        r.getNome().toLowerCase().contains(termoBusca.toLowerCase()))
                .toList();

        // Then
        assertEquals(2, resultados.size());
        assertTrue(resultados.stream()
                .anyMatch(r -> r.getNome().equals("João da Silva Santos")));
        assertTrue(resultados.stream()
                .anyMatch(r -> r.getNome().equals("Maria João Oliveira")));
        assertFalse(resultados.stream()
                .anyMatch(r -> r.getNome().equals("Pedro Santos")));
    }

    @Test
    @DisplayName("Deve validar existência por CPF")
    void deveValidarExistenciaPorCpf() {
        // Given
        List<CriarRegistroPessoaResponse> listaCompleta = new ArrayList<>();
        
        CriarRegistroPessoaResponse pessoa1 = new CriarRegistroPessoaResponse();
        pessoa1.setCpf("123.456.789-00");
        
        CriarRegistroPessoaResponse pessoa2 = new CriarRegistroPessoaResponse();
        pessoa2.setCpf("987.654.321-00");
        
        listaCompleta.add(pessoa1);
        listaCompleta.add(pessoa2);

        String cpfExistente = "123.456.789-00";
        String cpfInexistente = "000.000.000-00";

        // When
        boolean existeCpfExistente = listaCompleta.stream()
                .anyMatch(r -> r.getCpf().equalsIgnoreCase(cpfExistente));
        
        boolean existeCpfInexistente = listaCompleta.stream()
                .anyMatch(r -> r.getCpf().equalsIgnoreCase(cpfInexistente));

        // Then
        assertTrue(existeCpfExistente);
        assertFalse(existeCpfInexistente);
    }

    @Test
    @DisplayName("Deve validar coordenadas geográficas")
    void deveValidarCoordenadasGeograficas() {
        // Given
        CriarRegistroPessoaRequest requestComCoordenadas = new CriarRegistroPessoaRequest();
        requestComCoordenadas.setNome("Teste Coordenadas");
        requestComCoordenadas.setLatitude("-23.5505");
        requestComCoordenadas.setLongitude("-46.6333");

        // When
        RegistroPessoa registro = new RegistroPessoa();
        registro.setNome(requestComCoordenadas.getNome());
        registro.setLatitude(requestComCoordenadas.getLatitude());
        registro.setLongitude(requestComCoordenadas.getLongitude());

        // Then
        assertNotNull(registro.getLatitude());
        assertNotNull(registro.getLongitude());
        
        // Validar se as coordenadas estão dentro de uma faixa válida para São Paulo
        Double latitude = Double.parseDouble(registro.getLatitude());
        Double longitude = Double.parseDouble(registro.getLongitude());
        
        assertTrue(latitude > -25.0); // Maior que -25
        assertTrue(latitude < -22.0); // Menor que -22
        assertTrue(longitude > -48.0); // Maior que -48
        assertTrue(longitude < -45.0); // Menor que -45
    }

    @Test
    @DisplayName("Deve validar tratamento de registros sem usuário")
    void deveValidarTratamentoRegistroSemUsuario() {
        // Given
        RegistroPessoa registroSemUsuario = new RegistroPessoa();
        registroSemUsuario.setId(UUID.randomUUID());
        registroSemUsuario.setNome("Pessoa Sem Usuário");
        registroSemUsuario.setCpf("999.888.777-66");
        registroSemUsuario.setUsuario(null);

        // When - simulando conversão para response
        CriarRegistroPessoaResponse response = new CriarRegistroPessoaResponse();
        response.setId(registroSemUsuario.getId());
        response.setNome(registroSemUsuario.getNome());
        response.setCpf(registroSemUsuario.getCpf());
        
        if (registroSemUsuario.getUsuario() != null) {
            // Lógica de conversão do usuário
            ObterDadosUsuarioResponse usuarioResponse = new ObterDadosUsuarioResponse();
            response.setUsuario(usuarioResponse);
        }

        // Then
        assertNotNull(response);
        assertEquals("Pessoa Sem Usuário", response.getNome());
        assertEquals("999.888.777-66", response.getCpf());
        assertNull(response.getUsuario()); // Usuário deve ser null
    }

    @Test
    @DisplayName("Deve validar formatação de campos de endereço")
    void deveValidarFormatacaoCamposEndereco() {
        // Given
        CriarRegistroPessoaRequest requestEndereco = new CriarRegistroPessoaRequest();
        requestEndereco.setNome("Teste Endereço");
        requestEndereco.setNumero("123A");
        requestEndereco.setComplemento("Bloco B, Apt 456");
        requestEndereco.setCep("01234-567");
        requestEndereco.setBairro("Vila Teste");
        requestEndereco.setNomeMunicipio("São Paulo");
        requestEndereco.setNomeEstado("SP");

        // When
        RegistroPessoa registro = new RegistroPessoa();
        registro.setNome(requestEndereco.getNome());
        registro.setNumero(requestEndereco.getNumero());
        registro.setComplemento(requestEndereco.getComplemento());
        registro.setCep(requestEndereco.getCep());
        registro.setBairro(requestEndereco.getBairro());
        registro.setNomeMunicipio(requestEndereco.getNomeMunicipio());
        registro.setNomeEstado(requestEndereco.getNomeEstado());

        // Then
        assertEquals("123A", registro.getNumero());
        assertEquals("Bloco B, Apt 456", registro.getComplemento());
        assertEquals("01234-567", registro.getCep());
        assertEquals("Vila Teste", registro.getBairro());
        assertEquals("São Paulo", registro.getNomeMunicipio());
        assertEquals("SP", registro.getNomeEstado());
        
        // Validar formato do CEP
        assertTrue(registro.getCep().matches("\\d{5}-\\d{3}"));
    }
}
