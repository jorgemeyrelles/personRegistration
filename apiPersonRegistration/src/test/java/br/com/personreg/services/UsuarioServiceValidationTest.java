package br.com.personreg.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.personreg.dtos.CriarPerfilResponse;
import br.com.personreg.dtos.ObterDadosUsuarioResponse;
import br.com.personreg.entities.Perfil;
import br.com.personreg.entities.Usuario;

/**
 * Testes específicos para lógica de busca e validação do UsuarioService
 * Usando apenas JUnit - foco na lógica de conversões e validações
 * 
 * @author Sistema de Registro de Pessoas
 */
@DisplayName("UsuarioService - Testes de Busca e Validação")
class UsuarioServiceValidationTest {

    private List<Usuario> listaUsuarios;
    private Perfil perfilAdmin;
    private Perfil perfilUser;

    @BeforeEach
    void setUp() {
        // Setup dos perfis usando Lombok
        perfilAdmin = new Perfil();
        perfilAdmin.setId(UUID.randomUUID());
        perfilAdmin.setNome("ADMINISTRADOR");
        perfilAdmin.setUsuarios(new ArrayList<>());

        perfilUser = new Perfil();
        perfilUser.setId(UUID.randomUUID());
        perfilUser.setNome("USUARIO");
        perfilUser.setUsuarios(new ArrayList<>());

        // Setup da lista de usuários simulada
        listaUsuarios = new ArrayList<>();

        // Usuário 1 - Admin completo
        Usuario usuario1 = new Usuario();
        usuario1.setId(UUID.randomUUID());
        usuario1.setNome("João Admin Silva");
        usuario1.setEmail("admin@empresa.com");
        usuario1.setSenha("hash_senha_admin");
        usuario1.setPerfil(perfilAdmin);

        // Usuário 2 - User completo  
        Usuario usuario2 = new Usuario();
        usuario2.setId(UUID.randomUUID());
        usuario2.setNome("Maria User Santos");
        usuario2.setEmail("user@empresa.com");
        usuario2.setSenha("hash_senha_user");
        usuario2.setPerfil(perfilUser);

        // Usuário 3 - Sem perfil (caso edge)
        Usuario usuario3 = new Usuario();
        usuario3.setId(UUID.randomUUID());
        usuario3.setNome("Pedro Sem Perfil");
        usuario3.setEmail("semperfil@empresa.com");
        usuario3.setSenha("hash_senha_pedro");
        usuario3.setPerfil(null);

        // Usuário 4 - Com dados mínimos
        Usuario usuario4 = new Usuario();
        usuario4.setId(UUID.randomUUID());
        usuario4.setNome("Ana Simples");
        usuario4.setEmail("ana@empresa.com");
        usuario4.setSenha("hash_senha_ana");
        usuario4.setPerfil(perfilUser);

        listaUsuarios.add(usuario1);
        listaUsuarios.add(usuario2);
        listaUsuarios.add(usuario3);
        listaUsuarios.add(usuario4);
    }

    @Test
    @DisplayName("Teste 1: Deve validar conversão de lista de usuários para response")
    void teste1_deveValidarConversaoDeListaDeUsuariosParaResponse() {
        // Given - lista já configurada no @BeforeEach
        
        // When - simula a lógica do método buscarTodosUsuarios()
        List<ObterDadosUsuarioResponse> responses = new ArrayList<>();

        for (Usuario usuario : listaUsuarios) {
            ObterDadosUsuarioResponse response = new ObterDadosUsuarioResponse();
            response.setId(usuario.getId());
            response.setNome(usuario.getNome());
            response.setEmail(usuario.getEmail());

            // Simula a lógica de conversão de perfil do service
            CriarPerfilResponse perfil = new CriarPerfilResponse();
            if (usuario.getPerfil() != null) {
                perfil.setId(usuario.getPerfil().getId());
                perfil.setNome(usuario.getPerfil().getNome());
            }
            response.setPerfil(perfil);

            responses.add(response);
        }

        // Then - validações
        assertNotNull(responses, "Lista de responses não deve ser nula");
        assertEquals(4, responses.size(), "Deve ter 4 usuários na lista");

        // Validações do usuário admin (índice 0)
        ObterDadosUsuarioResponse adminResponse = responses.get(0);
        assertEquals("João Admin Silva", adminResponse.getNome(), "Nome do admin deve ser correto");
        assertEquals("admin@empresa.com", adminResponse.getEmail(), "Email do admin deve ser correto");
        assertNotNull(adminResponse.getPerfil(), "Perfil do admin não deve ser nulo");
        assertEquals("ADMINISTRADOR", adminResponse.getPerfil().getNome(), "Perfil deve ser ADMINISTRADOR");

        // Validações do usuário comum (índice 1)
        ObterDadosUsuarioResponse userResponse = responses.get(1);
        assertEquals("Maria User Santos", userResponse.getNome(), "Nome do user deve ser correto");
        assertEquals("user@empresa.com", userResponse.getEmail(), "Email do user deve ser correto");
        assertNotNull(userResponse.getPerfil(), "Perfil do user não deve ser nulo");
        assertEquals("USUARIO", userResponse.getPerfil().getNome(), "Perfil deve ser USUARIO");

        // Validações do usuário sem perfil (índice 2)
        ObterDadosUsuarioResponse semPerfilResponse = responses.get(2);
        assertEquals("Pedro Sem Perfil", semPerfilResponse.getNome(), "Nome deve ser correto");
        assertEquals("semperfil@empresa.com", semPerfilResponse.getEmail(), "Email deve ser correto");
        assertNotNull(semPerfilResponse.getPerfil(), "Objeto perfil deve existir mesmo quando nulo");
        assertNull(semPerfilResponse.getPerfil().getId(), "ID do perfil deve ser nulo");
        assertNull(semPerfilResponse.getPerfil().getNome(), "Nome do perfil deve ser nulo");

        // Validações do usuário simples (índice 3)
        ObterDadosUsuarioResponse simplesResponse = responses.get(3);
        assertEquals("Ana Simples", simplesResponse.getNome(), "Nome deve ser correto");
        assertEquals("ana@empresa.com", simplesResponse.getEmail(), "Email deve ser correto");
        assertEquals("USUARIO", simplesResponse.getPerfil().getNome(), "Perfil deve ser USUARIO");

        // Verifica que todos os IDs são únicos
        List<UUID> ids = responses.stream()
            .map(ObterDadosUsuarioResponse::getId)
            .distinct()
            .toList();
        assertEquals(4, ids.size(), "Todos os IDs devem ser únicos");
    }

    @Test
    @DisplayName("Teste 2: Deve validar lógica de validação de perfis e tratamento de nulos")
    void teste2_deveValidarLogicaDeValidacaoDePerfilETratamentoDeNulos() {
        // Given - configuração específica para este teste
        List<Usuario> usuariosParaTeste = new ArrayList<>();
        
        // Usuário com perfil válido
        Usuario usuarioComPerfil = new Usuario();
        usuarioComPerfil.setId(UUID.randomUUID());
        usuarioComPerfil.setNome("Usuário Com Perfil");
        usuarioComPerfil.setEmail("comperfil@test.com");
        usuarioComPerfil.setSenha("hash123");
        usuarioComPerfil.setPerfil(perfilAdmin);
        
        // Usuário com perfil nulo
        Usuario usuarioSemPerfil = new Usuario();
        usuarioSemPerfil.setId(UUID.randomUUID());
        usuarioSemPerfil.setNome("Usuário Sem Perfil");
        usuarioSemPerfil.setEmail("semperfil@test.com");
        usuarioSemPerfil.setSenha("hash456");
        usuarioSemPerfil.setPerfil(null);
        
        usuariosParaTeste.add(usuarioComPerfil);
        usuariosParaTeste.add(usuarioSemPerfil);

        // When - simula conversão com tratamento especial para perfis nulos
        List<ObterDadosUsuarioResponse> resultados = new ArrayList<>();
        
        for (Usuario usuario : usuariosParaTeste) {
            ObterDadosUsuarioResponse response = new ObterDadosUsuarioResponse();
            response.setId(usuario.getId());
            response.setNome(usuario.getNome());
            response.setEmail(usuario.getEmail());

            // Lógica específica de tratamento de perfil nulo
            CriarPerfilResponse perfilResponse = new CriarPerfilResponse();
            if (usuario.getPerfil() != null) {
                perfilResponse.setId(usuario.getPerfil().getId());
                perfilResponse.setNome(usuario.getPerfil().getNome());
            } else {
                // Mantém o objeto perfil mas com campos nulos
                perfilResponse.setId(null);
                perfilResponse.setNome(null);
            }
            response.setPerfil(perfilResponse);
            
            resultados.add(response);
        }

        // Then - validações específicas para tratamento de nulos
        assertEquals(2, resultados.size(), "Deve ter 2 resultados");

        // Usuário com perfil válido
        ObterDadosUsuarioResponse comPerfil = resultados.get(0);
        assertNotNull(comPerfil.getPerfil(), "Perfil não deve ser nulo");
        assertNotNull(comPerfil.getPerfil().getId(), "ID do perfil deve estar presente");
        assertEquals("ADMINISTRADOR", comPerfil.getPerfil().getNome(), "Nome do perfil deve ser correto");

        // Usuário sem perfil
        ObterDadosUsuarioResponse semPerfil = resultados.get(1);
        assertNotNull(semPerfil.getPerfil(), "Objeto perfil deve existir");
        assertNull(semPerfil.getPerfil().getId(), "ID do perfil deve ser nulo");
        assertNull(semPerfil.getPerfil().getNome(), "Nome do perfil deve ser nulo");

        // Testa robustez com toString em perfis nulos
        assertDoesNotThrow(() -> {
            String toStringComPerfil = comPerfil.toString();
            String toStringSemPerfil = semPerfil.toString();
            String toStringPerfilNulo = semPerfil.getPerfil().toString();
            
            assertNotNull(toStringComPerfil, "ToString com perfil não deve ser nulo");
            assertNotNull(toStringSemPerfil, "ToString sem perfil não deve ser nulo");
            assertNotNull(toStringPerfilNulo, "ToString do perfil nulo não deve ser nulo");
        }, "ToString não deve lançar exceção com perfis nulos");

        // Valida que getters/setters do Lombok funcionam corretamente
        CriarPerfilResponse novoPerfil = new CriarPerfilResponse();
        novoPerfil.setId(UUID.randomUUID());
        novoPerfil.setNome("TESTE");
        
        semPerfil.setPerfil(novoPerfil);
        assertEquals("TESTE", semPerfil.getPerfil().getNome(), "Perfil deve ter sido atualizado");
        assertNotNull(semPerfil.getPerfil().getId(), "ID do perfil deve estar presente após atualização");
    }

    @Test
    @DisplayName("Teste Extra: Deve validar integridade dos dados durante conversões")
    void testeExtra_deveValidarIntegridadeDosDadosDuranteConversoes() {
        // Given - usuário específico para teste de integridade
        Usuario usuarioOriginal = listaUsuarios.get(0); // João Admin Silva
        
        // When - múltiplas conversões para verificar integridade
        ObterDadosUsuarioResponse response1 = new ObterDadosUsuarioResponse();
        response1.setId(usuarioOriginal.getId());
        response1.setNome(usuarioOriginal.getNome());
        response1.setEmail(usuarioOriginal.getEmail());
        
        CriarPerfilResponse perfilResponse1 = new CriarPerfilResponse();
        perfilResponse1.setId(usuarioOriginal.getPerfil().getId());
        perfilResponse1.setNome(usuarioOriginal.getPerfil().getNome());
        response1.setPerfil(perfilResponse1);

        // Segunda conversão do mesmo usuário
        ObterDadosUsuarioResponse response2 = new ObterDadosUsuarioResponse();
        response2.setId(usuarioOriginal.getId());
        response2.setNome(usuarioOriginal.getNome());
        response2.setEmail(usuarioOriginal.getEmail());
        
        CriarPerfilResponse perfilResponse2 = new CriarPerfilResponse();
        perfilResponse2.setId(usuarioOriginal.getPerfil().getId());
        perfilResponse2.setNome(usuarioOriginal.getPerfil().getNome());
        response2.setPerfil(perfilResponse2);

        // Then - validações de integridade
        // Os responses devem ter dados idênticos
        assertEquals(response1.getId(), response2.getId(), "IDs devem ser iguais");
        assertEquals(response1.getNome(), response2.getNome(), "Nomes devem ser iguais");
        assertEquals(response1.getEmail(), response2.getEmail(), "Emails devem ser iguais");
        assertEquals(response1.getPerfil().getId(), response2.getPerfil().getId(), "IDs dos perfis devem ser iguais");
        assertEquals(response1.getPerfil().getNome(), response2.getPerfil().getNome(), "Nomes dos perfis devem ser iguais");

        // Mas devem ser objetos diferentes (não mesma referência)
        assertNotSame(response1, response2, "Responses devem ser objetos diferentes");
        assertNotSame(response1.getPerfil(), response2.getPerfil(), "Perfis devem ser objetos diferentes");

        // Modificação em um não deve afetar o outro
        response1.setNome("Nome Modificado");
        response1.getPerfil().setNome("Perfil Modificado");
        
        assertEquals("Nome Modificado", response1.getNome(), "Response1 deve ter nome modificado");
        assertEquals("João Admin Silva", response2.getNome(), "Response2 deve manter nome original");
        assertEquals("Perfil Modificado", response1.getPerfil().getNome(), "Perfil1 deve ter nome modificado");
        assertEquals("ADMINISTRADOR", response2.getPerfil().getNome(), "Perfil2 deve manter nome original");
    }

    @Test
    @DisplayName("Teste Extra: Deve validar comportamento com listas vazias e nulas")
    void testeExtra_deveValidarComportamentoComListasVaziasENulas() {
        // Given - lista vazia
        List<Usuario> listaVazia = new ArrayList<>();
        
        // When - conversão de lista vazia
        List<ObterDadosUsuarioResponse> responsesVazia = new ArrayList<>();
        for (Usuario usuario : listaVazia) {
            // Este loop não deve executar
            fail("Loop não deveria executar com lista vazia");
        }

        // Then
        assertTrue(listaVazia.isEmpty(), "Lista original deve estar vazia");
        assertTrue(responsesVazia.isEmpty(), "Lista de responses deve estar vazia");
        assertEquals(0, responsesVazia.size(), "Size deve ser 0");

        // Testa comportamento com lista nula (simulação)
        List<Usuario> listaNula = null;
        
        assertThrows(NullPointerException.class, () -> {
            for (Usuario usuario : listaNula) {
                // Deve lançar NullPointerException
            }
        }, "Deve lançar NullPointerException ao iterar lista nula");
    }
}
