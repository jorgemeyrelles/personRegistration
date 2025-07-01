import usuarioService from "../usuarios";
import type { Usuario, UsuarioAutenticacao } from "../../types/usuario";

// Exemplos de uso dos serviços de usuário

export const exemplosCRUDUsuario = {
  // 1. Criar um novo usuário
  async criarNovoUsuario() {
    const dadosUsuario: Usuario = {
      nome: "João Silva",
      email: "joao.silva@email.com",
      senha: "minhasenha123",
    };

    try {
      const usuarioCriado = await usuarioService.criarUsuario(dadosUsuario);
      console.log("Usuário criado:", usuarioCriado);
      return usuarioCriado;
    } catch (error) {
      console.error("Erro ao criar usuário:", error);
      throw error;
    }
  },

  // 2. Autenticar usuário
  async autenticarUsuario() {
    const credenciais: UsuarioAutenticacao = {
      email: "joao.silva@email.com",
      senha: "minhasenha123",
    };

    try {
      const usuarioAutenticado = await usuarioService.autenticarUsuario(
        credenciais
      );
      console.log("Usuário autenticado:", usuarioAutenticado);
      return usuarioAutenticado;
    } catch (error) {
      console.error("Erro ao autenticar:", error);
      throw error;
    }
  },

  // 3. Obter dados do usuário logado
  async obterDadosUsuario(token?: string) {
    try {
      const dadosUsuario = await usuarioService.obterDadosUsuario(token);
      console.log("Dados do usuário:", dadosUsuario);
      return dadosUsuario;
    } catch (error) {
      console.error("Erro ao obter dados:", error);
      throw error;
    }
  },

  // 4. Buscar todos os usuários
  async buscarTodosUsuarios(token?: string) {
    try {
      const usuarios = await usuarioService.buscarTodosUsuarios(token);
      console.log("Todos os usuários:", usuarios);
      return usuarios;
    } catch (error) {
      console.error("Erro ao buscar usuários:", error);
      throw error;
    }
  },

  // 5. Deletar usuário
  async deletarUsuario(id: number, token?: string) {
    try {
      const sucesso = await usuarioService.deletarUsuario(id, token);
      console.log("Usuário deletado:", sucesso);
      return sucesso;
    } catch (error) {
      console.error("Erro ao deletar usuário:", error);
      throw error;
    }
  },

  // Exemplo de fluxo completo
  async exemploFluxoCompleto() {
    try {
      // 1. Criar usuário
      console.log("1. Criando usuário...");
      const usuarioCriado = await this.criarNovoUsuario();

      // 2. Autenticar
      console.log("2. Autenticando usuário...");
      const usuarioAutenticado = await this.autenticarUsuario();
      console.log("Usuário autenticado:", usuarioAutenticado);

      // 3. Buscar todos (com token se necessário)
      console.log("3. Buscando todos os usuários...");
      await this.buscarTodosUsuarios();

      // 4. Obter dados pessoais
      console.log("4. Obtendo dados do usuário...");
      await this.obterDadosUsuario();

      console.log("Fluxo completo executado com sucesso!");
    } catch (error) {
      console.error("Erro no fluxo completo:", error);
    }
  },
};

export default exemplosCRUDUsuario;
