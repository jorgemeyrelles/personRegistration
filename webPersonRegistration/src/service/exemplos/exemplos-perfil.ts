import perfilService from "../perfil";
import type { PerfilCriar, PerfilAtualizar } from "../../types/perfil";

// Exemplos de uso dos serviços de perfil

export const exemplosCRUDPerfil = {
  // 1. Criar um novo perfil
  async criarNovoPerfil(token?: string) {
    const dadosPerfil: PerfilCriar = {
      nome: "João Silva",
      descricao: "Desenvolvedor Full Stack",
      telefone: "(11) 99999-9999",
      endereco: "Rua das Flores, 123 - São Paulo, SP",
      dataNascimento: "1990-01-15",
      usuarioId: 1,
    };

    try {
      const perfilCriado = await perfilService.criarPerfil(dadosPerfil, token);
      console.log("Perfil criado:", perfilCriado);
      return perfilCriado;
    } catch (error) {
      console.error("Erro ao criar perfil:", error);
      throw error;
    }
  },

  // 2. Atualizar perfil existente
  async atualizarPerfilExistente(id: number, token?: string) {
    const dadosAtualizacao: PerfilAtualizar = {
      nome: "João Silva Santos",
      descricao: "Desenvolvedor Full Stack Senior",
      telefone: "(11) 88888-8888",
      endereco: "Avenida Paulista, 456 - São Paulo, SP",
    };

    try {
      const perfilAtualizado = await perfilService.atualizarPerfil(
        id,
        dadosAtualizacao,
        token
      );
      console.log("Perfil atualizado:", perfilAtualizado);
      return perfilAtualizado;
    } catch (error) {
      console.error("Erro ao atualizar perfil:", error);
      throw error;
    }
  },

  // 3. Obter dados de um perfil específico
  async obterDadosPerfil(id: number, token?: string) {
    try {
      const dadosPerfil = await perfilService.obterDadosPerfil(id, token);
      console.log("Dados do perfil:", dadosPerfil);
      return dadosPerfil;
    } catch (error) {
      console.error("Erro ao obter dados do perfil:", error);
      throw error;
    }
  },

  // 4. Buscar todos os perfis
  async buscarTodosPerfis(token?: string) {
    try {
      const perfis = await perfilService.buscarTodosPerfis(token);
      console.log("Todos os perfis:", perfis);
      return perfis;
    } catch (error) {
      console.error("Erro ao buscar perfis:", error);
      throw error;
    }
  },

  // 5. Deletar perfil
  async deletarPerfil(id: number, token?: string) {
    try {
      const sucesso = await perfilService.deletarPerfil(id, token);
      console.log("Perfil deletado:", sucesso);
      return sucesso;
    } catch (error) {
      console.error("Erro ao deletar perfil:", error);
      throw error;
    }
  },

  // Exemplo de fluxo completo de gerenciamento de perfil
  async exemploFluxoCompletoPerfil(token?: string) {
    try {
      // 1. Criar perfil
      console.log("1. Criando perfil...");
      const perfilCriado = await this.criarNovoPerfil(token);
      const perfilId = perfilCriado.id;

      // 2. Buscar todos os perfis
      console.log("2. Buscando todos os perfis...");
      await this.buscarTodosPerfis(token);

      // 3. Obter dados do perfil criado
      console.log("3. Obtendo dados do perfil específico...");
      await this.obterDadosPerfil(perfilId, token);

      // 4. Atualizar o perfil
      console.log("4. Atualizando perfil...");
      await this.atualizarPerfilExistente(perfilId, token);

      // 5. Verificar se foi atualizado
      console.log("5. Verificando atualização...");
      await this.obterDadosPerfil(perfilId, token);

      console.log("Fluxo completo de perfil executado com sucesso!");
    } catch (error) {
      console.error("Erro no fluxo completo de perfil:", error);
    }
  },
};

export default exemplosCRUDPerfil;
