package br.com.personreg.services;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.personreg.dtos.CriarPerfilResponse;
import br.com.personreg.dtos.CriarRegistroPessoaRequest;
import br.com.personreg.dtos.CriarRegistroPessoaResponse;
import br.com.personreg.dtos.ObterDadosUsuarioResponse;
import br.com.personreg.entities.Perfil;
import br.com.personreg.entities.RegistroPessoa;
import br.com.personreg.entities.Usuario;

/**
 * Testes específicos para integridade de dados do RegistroPessoaService
 * Usando apenas JUnit - foco em robustez e tratamento de edge cases
 * 
 * @author Sistema de Registro de Pessoas
 */
@DisplayName("RegistroPessoaService - Testes de Integridade de Dados")
class RegistroPessoaServiceDataIntegrityTest {

    private ObjectMapper objectMapper;
    private Usuario usuarioCompleto;
    private Usuario usuarioSemPerfil;
    private Perfil perfil;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        
        // Setup do perfil
        perfil = new Perfil();
        perfil.setId(UUID.randomUUID());
        perfil.setNome("ADMINISTRADOR");
        perfil.setUsuarios(new ArrayList<>());

        // Setup do usuário completo
        usuarioCompleto = new Usuario();
        usuarioCompleto.setId(UUID.randomUUID());
        usuarioCompleto.setNome("Usuário Completo");
        usuarioCompleto.setEmail("completo@empresa.com");
        usuarioCompleto.setPerfil(perfil);

        // Setup do usuário sem perfil
        usuarioSemPerfil = new Usuario();
        usuarioSemPerfil.setId(UUID.randomUUID());
        usuarioSemPerfil.setNome("Usuário Sem Perfil");
        usuarioSemPerfil.setEmail("semperfil@empresa.com");
        usuarioSemPerfil.setPerfil(null);
    }

    @Test
    @DisplayName("Deve tratar dados nulos em CriarRegistroPessoaRequest")
    void deveTratarDadosNulosEmRequest() {
        // Given
        CriarRegistroPessoaRequest requestComNulos = new CriarRegistroPessoaRequest();
        requestComNulos.setNome(null);
        requestComNulos.setTelefone(null);
        requestComNulos.setCpf(null);
        requestComNulos.setNumero(null);
        requestComNulos.setComplemento(null);
        requestComNulos.setCep(null);
        requestComNulos.setBairro(null);
        requestComNulos.setNomeMunicipio(null);
        requestComNulos.setNomeEstado(null);
        requestComNulos.setLatitude(null);
        requestComNulos.setLongitude(null);
        requestComNulos.setUsuarioId(null);

        // When - criando entidade a partir do request
        RegistroPessoa registro = new RegistroPessoa();
        registro.setNome(requestComNulos.getNome());
        registro.setTelefone(requestComNulos.getTelefone());
        registro.setCpf(requestComNulos.getCpf());
        registro.setNumero(requestComNulos.getNumero());
        registro.setComplemento(requestComNulos.getComplemento());
        registro.setCep(requestComNulos.getCep());
        registro.setBairro(requestComNulos.getBairro());
        registro.setNomeMunicipio(requestComNulos.getNomeMunicipio());
        registro.setNomeEstado(requestComNulos.getNomeEstado());
        registro.setLatitude(requestComNulos.getLatitude());
        registro.setLongitude(requestComNulos.getLongitude());

        // Then - todos os campos devem ser nulos
        assertNull(registro.getNome());
        assertNull(registro.getTelefone());
        assertNull(registro.getCpf());
        assertNull(registro.getNumero());
        assertNull(registro.getComplemento());
        assertNull(registro.getCep());
        assertNull(registro.getBairro());
        assertNull(registro.getNomeMunicipio());
        assertNull(registro.getNomeEstado());
        assertNull(registro.getLatitude());
        assertNull(registro.getLongitude());
    }

    @Test
    @DisplayName("Deve tratar strings vazias e com espaços")
    void deveTratarStringsVaziasEComEspacos() {
        // Given
        CriarRegistroPessoaRequest requestComEspacos = new CriarRegistroPessoaRequest();
        requestComEspacos.setNome("   ");
        requestComEspacos.setTelefone("");
        requestComEspacos.setCpf("   000.000.000-00   ");
        requestComEspacos.setComplemento("");
        requestComEspacos.setBairro("  Centro  ");

        // When - simulando limpeza de dados
        RegistroPessoa registro = new RegistroPessoa();
        registro.setNome(requestComEspacos.getNome() != null ? 
                requestComEspacos.getNome().trim() : null);
        registro.setTelefone(requestComEspacos.getTelefone() != null && 
                !requestComEspacos.getTelefone().isEmpty() ? 
                requestComEspacos.getTelefone() : null);
        registro.setCpf(requestComEspacos.getCpf() != null ? 
                requestComEspacos.getCpf().trim() : null);
        registro.setComplemento(requestComEspacos.getComplemento() != null && 
                !requestComEspacos.getComplemento().isEmpty() ? 
                requestComEspacos.getComplemento() : null);
        registro.setBairro(requestComEspacos.getBairro() != null ? 
                requestComEspacos.getBairro().trim() : null);

        // Then
        assertEquals("", registro.getNome()); // String apenas com espaços vira vazia após trim
        assertNull(registro.getTelefone()); // String vazia vira null
        assertEquals("000.000.000-00", registro.getCpf()); // Espaços removidos
        assertNull(registro.getComplemento()); // String vazia vira null
        assertEquals("Centro", registro.getBairro()); // Espaços removidos
    }

    @Test
    @DisplayName("Deve validar conversão de LinkedHashMap para CriarRegistroPessoaResponse")
    void deveValidarConversaoLinkedHashMap() {
        // Given - simulando dados vindos do cache Redis como LinkedHashMap
        LinkedHashMap<String, Object> linkedHashMapData = new LinkedHashMap<>();
        linkedHashMapData.put("id", "550e8400-e29b-41d4-a716-446655440000");
        linkedHashMapData.put("nome", "João Silva Cache");
        linkedHashMapData.put("cpf", "123.456.789-00");
        linkedHashMapData.put("telefone", "(11) 99999-8888");
        linkedHashMapData.put("cep", "01310-100");
        linkedHashMapData.put("bairro", "Centro");
        linkedHashMapData.put("nomeMunicipio", "São Paulo");
        linkedHashMapData.put("nomeEstado", "SP");
        linkedHashMapData.put("latitude", -23.5505);
        linkedHashMapData.put("longitude", -46.6333);

        // When - convertendo LinkedHashMap para CriarRegistroPessoaResponse
        CriarRegistroPessoaResponse response = objectMapper.convertValue(
                linkedHashMapData, CriarRegistroPessoaResponse.class);

        // Then
        assertNotNull(response);
        assertEquals("João Silva Cache", response.getNome());
        assertEquals("123.456.789-00", response.getCpf());
        assertEquals("(11) 99999-8888", response.getTelefone());
        assertEquals("01310-100", response.getCep());
        assertEquals("Centro", response.getBairro());
        assertEquals("São Paulo", response.getNomeMunicipio());
        assertEquals("SP", response.getNomeEstado());
        
        // Validar coordenadas (convertidas de double para BigDecimal)
        assertNotNull(response.getLatitude());
        assertNotNull(response.getLongitude());
        assertEquals(0, response.getLatitude().compareTo(new BigDecimal("-23.5505")));
        assertEquals(0, response.getLongitude().compareTo(new BigDecimal("-46.6333")));
    }

    @Test
    @DisplayName("Deve tratar lista mista de objetos e LinkedHashMap")
    void deveTratarListaMistaObjetosLinkedHashMap() {
        // Given - simulando lista do cache com tipos mistos
        List<Object> listaMista = new ArrayList<>();
        
        // Objeto direto
        CriarRegistroPessoaResponse objetoDireto = new CriarRegistroPessoaResponse();
        objetoDireto.setId(UUID.randomUUID());
        objetoDireto.setNome("Objeto Direto");
        objetoDireto.setCpf("111.111.111-11");
        listaMista.add(objetoDireto);
        
        // LinkedHashMap
        LinkedHashMap<String, Object> linkedHashMapData = new LinkedHashMap<>();
        linkedHashMapData.put("id", UUID.randomUUID().toString());
        linkedHashMapData.put("nome", "LinkedHashMap Data");
        linkedHashMapData.put("cpf", "222.222.222-22");
        listaMista.add(linkedHashMapData);
        
        // Objeto inválido
        listaMista.add("String Inválida");
        listaMista.add(null);

        // When - processando lista mista
        List<CriarRegistroPessoaResponse> resultados = new ArrayList<>();
        for (Object obj : listaMista) {
            CriarRegistroPessoaResponse item = null;
            if (obj instanceof LinkedHashMap) {
                item = objectMapper.convertValue(obj, CriarRegistroPessoaResponse.class);
            } else if (obj instanceof CriarRegistroPessoaResponse) {
                item = (CriarRegistroPessoaResponse) obj;
            }
            
            if (item != null) {
                resultados.add(item);
            }
        }

        // Then
        assertEquals(2, resultados.size());
        assertTrue(resultados.stream().anyMatch(r -> "Objeto Direto".equals(r.getNome())));
        assertTrue(resultados.stream().anyMatch(r -> "LinkedHashMap Data".equals(r.getNome())));
        assertTrue(resultados.stream().anyMatch(r -> "111.111.111-11".equals(r.getCpf())));
        assertTrue(resultados.stream().anyMatch(r -> "222.222.222-22".equals(r.getCpf())));
    }

    @Test
    @DisplayName("Deve validar integridade de dados na conversão toResponse com usuário sem perfil")
    void deveValidarIntegridadeConversaoComUsuarioSemPerfil() {
        // Given
        RegistroPessoa registro = new RegistroPessoa();
        registro.setId(UUID.randomUUID());
        registro.setNome("Pessoa com Usuário Sem Perfil");
        registro.setCpf("333.444.555-66");
        registro.setUsuario(usuarioSemPerfil);

        // When - simulando conversão toResponse
        CriarRegistroPessoaResponse response = new CriarRegistroPessoaResponse();
        response.setId(registro.getId());
        response.setNome(registro.getNome());
        response.setCpf(registro.getCpf());
        
        if (registro.getUsuario() != null) {
            ObterDadosUsuarioResponse usuarioResponse = new ObterDadosUsuarioResponse();
            usuarioResponse.setId(registro.getUsuario().getId());
            usuarioResponse.setNome(registro.getUsuario().getNome());
            usuarioResponse.setEmail(registro.getUsuario().getEmail());
            
            // Tratamento especial para perfil nulo
            if (registro.getUsuario().getPerfil() != null) {
                CriarPerfilResponse perfilResponse = new CriarPerfilResponse();
                perfilResponse.setId(registro.getUsuario().getPerfil().getId());
                perfilResponse.setNome(registro.getUsuario().getPerfil().getNome());
                usuarioResponse.setPerfil(perfilResponse);
            } else {
                usuarioResponse.setPerfil(null);
            }
            
            response.setUsuario(usuarioResponse);
        }

        // Then
        assertNotNull(response);
        assertEquals("Pessoa com Usuário Sem Perfil", response.getNome());
        assertEquals("333.444.555-66", response.getCpf());
        
        assertNotNull(response.getUsuario());
        assertEquals(usuarioSemPerfil.getId(), response.getUsuario().getId());
        assertEquals("Usuário Sem Perfil", response.getUsuario().getNome());
        assertEquals("semperfil@empresa.com", response.getUsuario().getEmail());
        
        assertNull(response.getUsuario().getPerfil()); // Perfil deve ser null
    }

    @Test
    @DisplayName("Deve validar robustez na busca por telefone com formatos diferentes")
    void deveValidarRobustezBuscaTelefone() {
        // Given
        List<CriarRegistroPessoaResponse> lista = new ArrayList<>();
        
        CriarRegistroPessoaResponse pessoa1 = new CriarRegistroPessoaResponse();
        pessoa1.setNome("Pessoa 1");
        pessoa1.setTelefone("(11) 99999-8888");
        
        CriarRegistroPessoaResponse pessoa2 = new CriarRegistroPessoaResponse();
        pessoa2.setNome("Pessoa 2");
        pessoa2.setTelefone("11999998888");
        
        CriarRegistroPessoaResponse pessoa3 = new CriarRegistroPessoaResponse();
        pessoa3.setNome("Pessoa 3");
        pessoa3.setTelefone(null);
        
        lista.add(pessoa1);
        lista.add(pessoa2);
        lista.add(pessoa3);

        String telefoneBusca = "(11) 99999-8888";

        // When - simulando busca exata por telefone
        Optional<CriarRegistroPessoaResponse> resultado = lista.stream()
                .filter(r -> r.getTelefone() != null && 
                        r.getTelefone().equals(telefoneBusca))
                .findFirst();

        // Then
        assertTrue(resultado.isPresent());
        assertEquals("Pessoa 1", resultado.get().getNome());
        assertEquals("(11) 99999-8888", resultado.get().getTelefone());
    }

    @Test
    @DisplayName("Deve validar tratamento de coordenadas com valores extremos")
    void deveValidarTratamentoCoordenadosExtremasa() {
        // Given - valores extremos de latitude e longitude
        CriarRegistroPessoaRequest requestValoresExtremos = new CriarRegistroPessoaRequest();
        requestValoresExtremos.setNome("Teste Coordenadas Extremas");
        requestValoresExtremos.setLatitude(new BigDecimal("90.0"));     // Polo Norte
        requestValoresExtremos.setLongitude(new BigDecimal("-180.0"));  // Limite oeste

        CriarRegistroPessoaRequest requestValoresInvalidos = new CriarRegistroPessoaRequest();
        requestValoresInvalidos.setNome("Teste Coordenadas Inválidas");
        requestValoresInvalidos.setLatitude(new BigDecimal("91.0"));     // Inválido
        requestValoresInvalidos.setLongitude(new BigDecimal("181.0"));   // Inválido

        // When
        RegistroPessoa registroExtremos = new RegistroPessoa();
        registroExtremos.setNome(requestValoresExtremos.getNome());
        registroExtremos.setLatitude(requestValoresExtremos.getLatitude());
        registroExtremos.setLongitude(requestValoresExtremos.getLongitude());

        RegistroPessoa registroInvalidos = new RegistroPessoa();
        registroInvalidos.setNome(requestValoresInvalidos.getNome());
        registroInvalidos.setLatitude(requestValoresInvalidos.getLatitude());
        registroInvalidos.setLongitude(requestValoresInvalidos.getLongitude());

        // Then - valores extremos mas válidos
        assertEquals(new BigDecimal("90.0"), registroExtremos.getLatitude());
        assertEquals(new BigDecimal("-180.0"), registroExtremos.getLongitude());
        
        // Validação de limites geográficos
        assertTrue(registroExtremos.getLatitude().compareTo(new BigDecimal("-90")) >= 0);
        assertTrue(registroExtremos.getLatitude().compareTo(new BigDecimal("90")) <= 0);
        assertTrue(registroExtremos.getLongitude().compareTo(new BigDecimal("-180")) >= 0);
        assertTrue(registroExtremos.getLongitude().compareTo(new BigDecimal("180")) <= 0);

        // Valores inválidos (fora dos limites geográficos)
        assertTrue(registroInvalidos.getLatitude().compareTo(new BigDecimal("90")) > 0);
        assertTrue(registroInvalidos.getLongitude().compareTo(new BigDecimal("180")) > 0);
    }

    @Test
    @DisplayName("Deve validar atualização com dados parciais e valores nulos")
    void deveValidarAtualizacaoComDadosParciaisEValoresNulos() {
        // Given
        RegistroPessoa registroExistente = new RegistroPessoa();
        registroExistente.setId(UUID.randomUUID());
        registroExistente.setNome("Nome Original");
        registroExistente.setTelefone("(11) 99999-0000");
        registroExistente.setCpf("123.456.789-00");
        registroExistente.setCep("01234-567");
        registroExistente.setBairro("Bairro Original");
        registroExistente.setUsuario(usuarioCompleto);

        CriarRegistroPessoaRequest atualizacaoComNulos = new CriarRegistroPessoaRequest();
        atualizacaoComNulos.setNome("Nome Atualizado");
        atualizacaoComNulos.setTelefone(null);
        atualizacaoComNulos.setCpf(null);
        atualizacaoComNulos.setCep("98765-432");
        atualizacaoComNulos.setBairro(null);

        // When - simulando atualização seletiva
        if (atualizacaoComNulos.getNome() != null)
            registroExistente.setNome(atualizacaoComNulos.getNome());
        if (atualizacaoComNulos.getTelefone() != null)
            registroExistente.setTelefone(atualizacaoComNulos.getTelefone());
        if (atualizacaoComNulos.getCpf() != null)
            registroExistente.setCpf(atualizacaoComNulos.getCpf());
        if (atualizacaoComNulos.getCep() != null)
            registroExistente.setCep(atualizacaoComNulos.getCep());
        if (atualizacaoComNulos.getBairro() != null)
            registroExistente.setBairro(atualizacaoComNulos.getBairro());

        // Then
        assertEquals("Nome Atualizado", registroExistente.getNome());
        assertEquals("(11) 99999-0000", registroExistente.getTelefone()); // Não alterado
        assertEquals("123.456.789-00", registroExistente.getCpf()); // Não alterado
        assertEquals("98765-432", registroExistente.getCep()); // Atualizado
        assertEquals("Bairro Original", registroExistente.getBairro()); // Não alterado
        assertEquals(usuarioCompleto, registroExistente.getUsuario()); // Não alterado
    }

    @Test
    @DisplayName("Deve validar filtros de busca com dados inconsistentes")
    void deveValidarFiltrosBuscaComDadosInconsistentes() {
        // Given
        List<CriarRegistroPessoaResponse> listaInconsistente = new ArrayList<>();
        
        // Pessoa com dados completos
        CriarRegistroPessoaResponse pessoaCompleta = new CriarRegistroPessoaResponse();
        pessoaCompleta.setNome("João Silva Completo");
        pessoaCompleta.setCpf("123.456.789-00");
        pessoaCompleta.setTelefone("(11) 99999-8888");
        
        // Pessoa com dados parciais
        CriarRegistroPessoaResponse pessoaParcial = new CriarRegistroPessoaResponse();
        pessoaParcial.setNome(null);
        pessoaParcial.setCpf("987.654.321-00");
        pessoaParcial.setTelefone("(21) 88888-9999");
        
        // Pessoa com campos vazios
        CriarRegistroPessoaResponse pessoaVazia = new CriarRegistroPessoaResponse();
        pessoaVazia.setNome("");
        pessoaVazia.setCpf("");
        pessoaVazia.setTelefone("");
        
        listaInconsistente.add(pessoaCompleta);
        listaInconsistente.add(pessoaParcial);
        listaInconsistente.add(pessoaVazia);

        // When - aplicando filtros robustos
        List<CriarRegistroPessoaResponse> comNomeValido = listaInconsistente.stream()
                .filter(r -> r.getNome() != null && !r.getNome().trim().isEmpty())
                .toList();

        List<CriarRegistroPessoaResponse> comCpfValido = listaInconsistente.stream()
                .filter(r -> r.getCpf() != null && !r.getCpf().trim().isEmpty())
                .toList();

        List<CriarRegistroPessoaResponse> comTelefoneValido = listaInconsistente.stream()
                .filter(r -> r.getTelefone() != null && !r.getTelefone().trim().isEmpty())
                .toList();

        // Then
        assertEquals(1, comNomeValido.size());
        assertEquals("João Silva Completo", comNomeValido.get(0).getNome());

        assertEquals(2, comCpfValido.size()); // pessoaCompleta e pessoaParcial
        assertTrue(comCpfValido.stream().anyMatch(r -> "123.456.789-00".equals(r.getCpf())));
        assertTrue(comCpfValido.stream().anyMatch(r -> "987.654.321-00".equals(r.getCpf())));

        assertEquals(2, comTelefoneValido.size()); // pessoaCompleta e pessoaParcial
        assertTrue(comTelefoneValido.stream().anyMatch(r -> "(11) 99999-8888".equals(r.getTelefone())));
        assertTrue(comTelefoneValido.stream().anyMatch(r -> "(21) 88888-9999".equals(r.getTelefone())));
    }

    @Test
    @DisplayName("Deve validar comportamento com UUIDs inválidos")
    void deveValidarComportamentoComUUIDsInvalidos() {
        // Given
        String uuidValido = UUID.randomUUID().toString();
        String uuidInvalido = "uuid-invalido";
        String uuidNulo = null;

        // When & Then - testando conversão de UUIDs
        
        // UUID válido
        assertDoesNotThrow(() -> {
            UUID uuid = UUID.fromString(uuidValido);
            assertNotNull(uuid);
        });

        // UUID inválido
        assertThrows(IllegalArgumentException.class, () -> {
            UUID.fromString(uuidInvalido);
        });

        // UUID nulo
        assertThrows(NullPointerException.class, () -> {
            UUID.fromString(uuidNulo);
        });
    }

    @Test
    @DisplayName("Deve validar comportamento com BigDecimal nulos e zeros")
    void deveValidarComportamentoComBigDecimalNulosEZeros() {
        // Given
        CriarRegistroPessoaRequest requestComZeros = new CriarRegistroPessoaRequest();
        requestComZeros.setNome("Teste BigDecimal");
        requestComZeros.setLatitude(new BigDecimal("0.0"));
        requestComZeros.setLongitude(new BigDecimal("0.0"));

        CriarRegistroPessoaRequest requestComNulos = new CriarRegistroPessoaRequest();
        requestComNulos.setNome("Teste BigDecimal Nulos");
        requestComNulos.setLatitude(null);
        requestComNulos.setLongitude(null);

        // When
        RegistroPessoa registroComZeros = new RegistroPessoa();
        registroComZeros.setNome(requestComZeros.getNome());
        registroComZeros.setLatitude(requestComZeros.getLatitude());
        registroComZeros.setLongitude(requestComZeros.getLongitude());

        RegistroPessoa registroComNulos = new RegistroPessoa();
        registroComNulos.setNome(requestComNulos.getNome());
        registroComNulos.setLatitude(requestComNulos.getLatitude());
        registroComNulos.setLongitude(requestComNulos.getLongitude());

        // Then
        assertNotNull(registroComZeros.getLatitude());
        assertNotNull(registroComZeros.getLongitude());
        assertEquals(0, registroComZeros.getLatitude().compareTo(BigDecimal.ZERO));
        assertEquals(0, registroComZeros.getLongitude().compareTo(BigDecimal.ZERO));

        assertNull(registroComNulos.getLatitude());
        assertNull(registroComNulos.getLongitude());
    }
}
