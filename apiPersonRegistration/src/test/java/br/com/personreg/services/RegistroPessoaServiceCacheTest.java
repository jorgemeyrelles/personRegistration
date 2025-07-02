package br.com.personreg.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.personreg.dtos.CriarRegistroPessoaResponse;

/**
 * Testes específicos para lógica de busca e cache do RegistroPessoaService
 * Usando apenas JUnit - foco na lógica de filtros e conversões
 * 
 * @author Sistema de Registro de Pessoas
 */
@DisplayName("RegistroPessoaService - Testes de Busca e Cache")
class RegistroPessoaServiceCacheTest {

    private List<CriarRegistroPessoaResponse> listaCache;
    private List<Object> listaCacheComLinkedHashMap;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        
        // Setup da lista de cache simulada
        listaCache = new ArrayList<>();
        
        // Primeiro registro
        CriarRegistroPessoaResponse pessoa1 = new CriarRegistroPessoaResponse();
        pessoa1.setId(UUID.randomUUID());
        pessoa1.setNome("João Silva Santos");
        pessoa1.setCpf("123.456.789-00");
        pessoa1.setTelefone("(11) 99999-8888");
        pessoa1.setCep("01310-100");
        pessoa1.setBairro("Centro");
        pessoa1.setNomeMunicipio("São Paulo");
        pessoa1.setNomeEstado("São Paulo");
        
        // Segundo registro
        CriarRegistroPessoaResponse pessoa2 = new CriarRegistroPessoaResponse();
        pessoa2.setId(UUID.randomUUID());
        pessoa2.setNome("Maria Santos Silva");
        pessoa2.setCpf("987.654.321-00");
        pessoa2.setTelefone("(21) 88888-7777");
        pessoa2.setCep("20040-020");
        pessoa2.setBairro("Copacabana");
        pessoa2.setNomeMunicipio("Rio de Janeiro");
        pessoa2.setNomeEstado("Rio de Janeiro");
        
        // Terceiro registro
        CriarRegistroPessoaResponse pessoa3 = new CriarRegistroPessoaResponse();
        pessoa3.setId(UUID.randomUUID());
        pessoa3.setNome("Pedro João Costa");
        pessoa3.setCpf("111.222.333-44");
        pessoa3.setTelefone("(31) 77777-6666");
        pessoa3.setCep("30112-000");
        pessoa3.setBairro("Savassi");
        pessoa3.setNomeMunicipio("Belo Horizonte");
        pessoa3.setNomeEstado("Minas Gerais");
        
        listaCache.add(pessoa1);
        listaCache.add(pessoa2);
        listaCache.add(pessoa3);
        
        // Setup da lista com LinkedHashMap (simula dados vindos do Redis)
        listaCacheComLinkedHashMap = new ArrayList<>();
        listaCacheComLinkedHashMap.add(pessoa1); // Objeto normal
        
        // Simula um LinkedHashMap (como vem do Redis)
        LinkedHashMap<String, Object> mapPessoa2 = new LinkedHashMap<>();
        mapPessoa2.put("id", pessoa2.getId().toString());
        mapPessoa2.put("nome", pessoa2.getNome());
        mapPessoa2.put("cpf", pessoa2.getCpf());
        mapPessoa2.put("telefone", pessoa2.getTelefone());
        mapPessoa2.put("cep", pessoa2.getCep());
        mapPessoa2.put("bairro", pessoa2.getBairro());
        mapPessoa2.put("nomeMunicipio", pessoa2.getNomeMunicipio());
        mapPessoa2.put("nomeEstado", pessoa2.getNomeEstado());
        
        listaCacheComLinkedHashMap.add(mapPessoa2);
        listaCacheComLinkedHashMap.add(pessoa3); // Objeto normal
    }

    @Test
    @DisplayName("Teste 1: Deve validar lógica de busca por nome no cache")
    void teste1_deveValidarLogicaDeBuscaPorNomeNoCache() {
        // Given - lista de cache já configurada no @BeforeEach
        String termoBusca = "silva";
        
        // When - simula a lógica do método buscarPorNome() quando há cache
        List<CriarRegistroPessoaResponse> resultado = listaCache.stream()
            .filter(Objects::nonNull)
            .filter(r -> r.getNome() != null && 
                        r.getNome().toLowerCase().contains(termoBusca.toLowerCase()))
            .collect(Collectors.toList());

        // Then - validações
        assertNotNull(resultado, "Resultado não deve ser nulo");
        assertEquals(2, resultado.size(), "Deve encontrar 2 pessoas com 'silva' no nome");
        
        // Verifica se as pessoas corretas foram encontradas
        List<String> nomesEncontrados = resultado.stream()
            .map(CriarRegistroPessoaResponse::getNome)
            .collect(Collectors.toList());
        
        assertTrue(nomesEncontrados.contains("João Silva Santos"), 
            "Deve conter João Silva Santos");
        assertTrue(nomesEncontrados.contains("Maria Santos Silva"), 
            "Deve conter Maria Santos Silva");
        assertFalse(nomesEncontrados.contains("Pedro João Costa"), 
            "Não deve conter Pedro João Costa");
    }

    @Test
    @DisplayName("Teste 2: Deve validar conversão de LinkedHashMap para CriarRegistroPessoaResponse")
    void teste2_deveValidarConversaoDeLinkedHashMapParaCriarRegistroPessoaResponse() {
        // Given - lista com mix de objetos e LinkedHashMap
        String cpfBusca = "987.654.321-00";
        
        // When - simula a lógica do método buscarPorCpf() com conversão de LinkedHashMap
        List<CriarRegistroPessoaResponse> listaConvertida = listaCacheComLinkedHashMap.stream()
            .map(obj -> {
                if (obj instanceof LinkedHashMap) {
                    return objectMapper.convertValue(obj, CriarRegistroPessoaResponse.class);
                } else if (obj instanceof CriarRegistroPessoaResponse) {
                    return (CriarRegistroPessoaResponse) obj;
                } else {
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
        
        // Busca por CPF na lista convertida
        CriarRegistroPessoaResponse resultado = listaConvertida.stream()
            .filter(r -> r.getCpf().equalsIgnoreCase(cpfBusca))
            .findFirst()
            .orElse(null);

        // Then - validações
        assertNotNull(listaConvertida, "Lista convertida não deve ser nula");
        assertEquals(3, listaConvertida.size(), "Deve ter 3 elementos após conversão");
        
        // Valida que todos os elementos foram convertidos corretamente
        for (CriarRegistroPessoaResponse pessoa : listaConvertida) {
            assertNotNull(pessoa, "Pessoa não deve ser nula");
            assertNotNull(pessoa.getId(), "ID não deve ser nulo");
            assertNotNull(pessoa.getNome(), "Nome não deve ser nulo");
            assertNotNull(pessoa.getCpf(), "CPF não deve ser nulo");
        }
        
        // Valida busca específica por CPF
        assertNotNull(resultado, "Resultado da busca por CPF não deve ser nulo");
        assertEquals("Maria Santos Silva", resultado.getNome(), 
            "Nome deve ser Maria Santos Silva");
        assertEquals(cpfBusca, resultado.getCpf(), "CPF deve ser o buscado");
        assertEquals("(21) 88888-7777", resultado.getTelefone(), 
            "Telefone deve ter sido convertido corretamente");
        assertEquals("Rio de Janeiro", resultado.getNomeMunicipio(), 
            "Município deve ter sido convertido corretamente");
    }

    @Test
    @DisplayName("Teste Extra: Deve validar lógica de existência por CPF")
    void testeExtra_deveValidarLogicaDeExistenciaPorCpf() {
        // Given
        String cpfExistente = "123.456.789-00";
        String cpfInexistente = "000.000.000-00";
        
        // When - simula a lógica do método existePorCpf()
        boolean existeCpfExistente = listaCache.stream()
            .anyMatch(r -> r.getCpf().equalsIgnoreCase(cpfExistente));
        
        boolean existeCpfInexistente = listaCache.stream()
            .anyMatch(r -> r.getCpf().equalsIgnoreCase(cpfInexistente));

        // Then
        assertTrue(existeCpfExistente, "CPF existente deve retornar true");
        assertFalse(existeCpfInexistente, "CPF inexistente deve retornar false");
    }

    @Test
    @DisplayName("Teste Extra: Deve validar busca por telefone")
    void testeExtra_deveValidarBuscaPorTelefone() {
        // Given
        String telefoneExistente = "(11) 99999-8888";
        String telefoneInexistente = "(85) 00000-0000";
        
        // When - simula a lógica do método buscarPorTelefone()
        CriarRegistroPessoaResponse resultadoExistente = listaCache.stream()
            .filter(r -> r.getTelefone() != null && 
                        r.getTelefone().equals(telefoneExistente))
            .findFirst()
            .orElse(null);
        
        CriarRegistroPessoaResponse resultadoInexistente = listaCache.stream()
            .filter(r -> r.getTelefone() != null && 
                        r.getTelefone().equals(telefoneInexistente))
            .findFirst()
            .orElse(null);

        // Then
        assertNotNull(resultadoExistente, "Deve encontrar pessoa com telefone existente");
        assertEquals("João Silva Santos", resultadoExistente.getNome(), 
            "Nome deve ser João Silva Santos");
        assertEquals(telefoneExistente, resultadoExistente.getTelefone(), 
            "Telefone deve ser o buscado");
        
        assertNull(resultadoInexistente, "Não deve encontrar pessoa com telefone inexistente");
    }

    @Test
    @DisplayName("Teste Extra: Deve validar filtros com dados nulos")
    void testeExtra_deveValidarFiltrosComDadosNulos() {
        // Given - adiciona pessoa com dados nulos
        CriarRegistroPessoaResponse pessoaComDadosNulos = new CriarRegistroPessoaResponse();
        pessoaComDadosNulos.setId(UUID.randomUUID());
        pessoaComDadosNulos.setNome(null); // Nome nulo
        pessoaComDadosNulos.setCpf("555.555.555-55");
        pessoaComDadosNulos.setTelefone(null); // Telefone nulo
        
        listaCache.add(pessoaComDadosNulos);
        
        // When - busca por nome com dados nulos
        List<CriarRegistroPessoaResponse> resultadoBuscaNome = listaCache.stream()
            .filter(Objects::nonNull)
            .filter(r -> r.getNome() != null && 
                        r.getNome().toLowerCase().contains("silva"))
            .collect(Collectors.toList());
        
        // Busca por telefone com dados nulos
        long countTelefoneNulo = listaCache.stream()
            .filter(r -> r.getTelefone() != null)
            .count();

        // Then
        assertEquals(2, resultadoBuscaNome.size(), 
            "Busca por nome deve ignorar registros com nome nulo");
        assertEquals(3, countTelefoneNulo, 
            "Deve ter 3 registros com telefone não nulo (pessoa com telefone nulo é ignorada)");
        
        // Valida que pessoa com dados nulos existe na lista mas é filtrada
        assertEquals(4, listaCache.size(), "Lista deve ter 4 elementos no total");
        assertTrue(listaCache.stream().anyMatch(r -> "555.555.555-55".equals(r.getCpf())), 
            "Pessoa com dados nulos deve estar na lista");
    }
}
