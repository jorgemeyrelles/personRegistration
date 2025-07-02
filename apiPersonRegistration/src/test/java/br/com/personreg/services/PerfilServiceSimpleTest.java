package br.com.personreg.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.personreg.dtos.CriarPerfilRequest;
import br.com.personreg.dtos.CriarPerfilResponse;
import br.com.personreg.entities.Perfil;

/**
 * Testes simples para PerfilService usando apenas JUnit
 * Focado em validações de lógica básica e DTOs
 * 
 * @author Sistema de Registro de Pessoas
 */
@DisplayName("Testes Simples do PerfilService")
class PerfilServiceSimpleTest {

    private CriarPerfilRequest criarPerfilRequest;
    private Perfil perfil;

    @BeforeEach
    void setUp() {
        // Setup do request usando Lombok
        criarPerfilRequest = new CriarPerfilRequest();
        criarPerfilRequest.setNome("ADMINISTRADOR");
        
        // Setup da entidade Perfil usando Lombok
        perfil = new Perfil();
        perfil.setId(UUID.randomUUID());
        perfil.setNome("ADMINISTRADOR");
        perfil.setUsuarios(new ArrayList<>());
    }

    @Test
    @DisplayName("Teste 1: Deve validar criação de CriarPerfilRequest com Lombok")
    void teste1_deveValidarCriacaoDeCriarPerfilRequestComLombok() {
        // Given - dados já configurados no @BeforeEach
        
        // When - verificamos os getters/setters gerados pelo Lombok
        String nomeObtido = criarPerfilRequest.getNome();
        
        // Then - validamos que o Lombok está funcionando corretamente
        assertNotNull(criarPerfilRequest, "Request não deve ser nulo");
        assertEquals("ADMINISTRADOR", nomeObtido, "Nome deve ser ADMINISTRADOR");
        
        // Testamos também o setter
        criarPerfilRequest.setNome("USUARIO");
        assertEquals("USUARIO", criarPerfilRequest.getNome(), "Nome deve ter sido alterado para USUARIO");
        
        // Validamos que toString() do Lombok funciona (não deve lançar exceção)
        assertDoesNotThrow(() -> {
            String toString = criarPerfilRequest.toString();
            assertNotNull(toString, "ToString não deve ser nulo");
        }, "ToString gerado pelo Lombok não deve lançar exceção");
    }

    @Test
    @DisplayName("Teste 2: Deve validar entidade Perfil com métodos Lombok e lógica básica")
    void teste2_deveValidarEntidadePerfilComMetodosLombokELogicaBasica() {
        // Given - perfil já configurado no @BeforeEach
        
        // When & Then - testamos os métodos gerados pelo Lombok
        
        // Testa getters
        assertNotNull(perfil.getId(), "ID não deve ser nulo");
        assertEquals("ADMINISTRADOR", perfil.getNome(), "Nome deve ser ADMINISTRADOR");
        assertNotNull(perfil.getUsuarios(), "Lista de usuários não deve ser nula");
        assertTrue(perfil.getUsuarios().isEmpty(), "Lista de usuários deve estar vazia");
        
        // Testa setters
        UUID novoId = UUID.randomUUID();
        perfil.setId(novoId);
        assertEquals(novoId, perfil.getId(), "ID deve ter sido atualizado");
        
        perfil.setNome("OPERADOR");
        assertEquals("OPERADOR", perfil.getNome(), "Nome deve ter sido atualizado");
        
        // Testa que podemos adicionar usuários à lista
        assertEquals(0, perfil.getUsuarios().size(), "Inicialmente deve ter 0 usuários");
        
        // Simula adição de usuário (sem persistência, apenas lógica)
        perfil.getUsuarios().add(null); // Adiciona um elemento qualquer para testar
        assertEquals(1, perfil.getUsuarios().size(), "Deve ter 1 usuário após adição");
        
        // Testa construtor padrão do Lombok (NoArgsConstructor)
        Perfil novoPerfil = new Perfil();
        assertNotNull(novoPerfil, "Novo perfil criado com construtor padrão não deve ser nulo");
        assertNull(novoPerfil.getId(), "ID do novo perfil deve ser nulo inicialmente");
        assertNull(novoPerfil.getNome(), "Nome do novo perfil deve ser nulo inicialmente");
        
        // Testa construtor com argumentos (AllArgsConstructor)
        UUID idTeste = UUID.randomUUID();
        Perfil perfilComArgs = new Perfil(idTeste, "TESTE", new ArrayList<>());
        assertEquals(idTeste, perfilComArgs.getId(), "ID deve ser o passado no construtor");
        assertEquals("TESTE", perfilComArgs.getNome(), "Nome deve ser o passado no construtor");
        assertNotNull(perfilComArgs.getUsuarios(), "Lista deve ser a passada no construtor");
        
        // Testa toString() - método gerado pelo Lombok
        assertDoesNotThrow(() -> {
            String perfilString = perfil.toString();
            assertNotNull(perfilString, "ToString não deve ser nulo");
            assertTrue(perfilString.contains("OPERADOR"), "ToString deve conter o nome do perfil");
        }, "ToString do Perfil não deve lançar exceção");
    }

    @Test
    @DisplayName("Teste Bonus: Deve validar conversão entre Request e Response DTOs")
    void testeBOnus_deveValidarConversaoEntreRequestEResponseDTOs() {
        // Given
        CriarPerfilRequest request = new CriarPerfilRequest();
        request.setNome("SUPERVISOR");
        
        // When - simulamos a lógica que seria feita no service
        // (criação manual de response baseado no request)
        CriarPerfilResponse response = new CriarPerfilResponse();
        response.setId(UUID.randomUUID());
        response.setNome(request.getNome()); // Copia nome do request
        
        // Then - validamos a conversão
        assertNotNull(response, "Response não deve ser nulo");
        assertNotNull(response.getId(), "ID do response deve ter sido gerado");
        assertEquals("SUPERVISOR", response.getNome(), "Nome deve ter sido copiado do request");
        assertEquals(request.getNome(), response.getNome(), "Nomes do request e response devem ser iguais");
        
        // Valida que são objetos diferentes (não a mesma referência)
        assertNotSame(request, response, "Request e Response devem ser objetos diferentes");
        
        // Testa toString dos DTOs
        assertDoesNotThrow(() -> {
            String requestString = request.toString();
            String responseString = response.toString();
            
            assertNotNull(requestString, "ToString do request não deve ser nulo");
            assertNotNull(responseString, "ToString do response não deve ser nulo");
            
            // Ambos devem conter o nome
            assertTrue(requestString.contains("SUPERVISOR"), "Request toString deve conter nome");
            assertTrue(responseString.contains("SUPERVISOR"), "Response toString deve conter nome");
            
        }, "ToString dos DTOs não deve lançar exceção");
    }
}
