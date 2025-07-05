// import { API_BASE_URL } from "../../config/api";

import type {
  Usuario,
  UsuarioAutenticacao,
  UsuarioResponse,
} from "../../types/usuario";

const API_BASE_URL =
  import.meta.env.VITE_API_BASE_URL || "http://localhost:8081";

if (import.meta.env.DEV) {
  console.log("🌐 API Base URL:", API_BASE_URL);
}

// Serviço para criar usuário
export const criarUsuario = async (
  usuario: Usuario
): Promise<UsuarioResponse> => {
  try {
    const response = await fetch(`${API_BASE_URL}/api/usuario/criar`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(usuario),
    });

    if (!response.ok) {
      throw new Error(`Erro ao criar usuário: ${response.statusText}`);
    }

    return await response.json();
  } catch (error) {
    console.error("Erro ao criar usuário:", error);
    throw error;
  }
};

// Serviço para autenticar usuário
export const autenticarUsuario = async (
  credenciais: UsuarioAutenticacao
): Promise<UsuarioResponse> => {
  try {
    const response = await fetch(`${API_BASE_URL}/api/usuario/autenticar`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(credenciais),
    });

    if (!response.ok) {
      throw new Error(`Erro ao autenticar usuário: ${response.statusText}`);
    }

    return await response.json();
  } catch (error) {
    console.error("Erro ao autenticar usuário:", error);
    throw error;
  }
};

// Serviço para obter dados do usuário
export const obterDadosUsuario = async (
  token?: string
): Promise<UsuarioResponse> => {
  try {
    const headers: HeadersInit = {
      "Content-Type": "application/json",
    };

    // Se houver token, adiciona ao header de autorização
    if (token) {
      headers.Authorization = `Bearer ${token}`;
    }

    const response = await fetch(`${API_BASE_URL}/api/usuario/obter-dados`, {
      method: "GET",
      headers,
    });

    if (!response.ok) {
      throw new Error(`Erro ao obter dados do usuário: ${response.statusText}`);
    }

    return await response.json();
  } catch (error) {
    console.error("Erro ao obter dados do usuário:", error);
    throw error;
  }
};

// Serviço para buscar todos os usuários
export const buscarTodosUsuarios = async (
  token?: string
): Promise<UsuarioResponse[]> => {
  try {
    const headers: HeadersInit = {
      "Content-Type": "application/json",
    };

    // Se houver token, adiciona ao header de autorização
    if (token) {
      headers.Authorization = `Bearer ${token}`;
    }

    const response = await fetch(`${API_BASE_URL}/api/usuario/buscar-todos`, {
      method: "GET",
      headers,
    });

    if (!response.ok) {
      throw new Error(`Erro ao buscar usuários: ${response.statusText}`);
    }

    return await response.json();
  } catch (error) {
    console.error("Erro ao buscar usuários:", error);
    throw error;
  }
};

// Serviço para deletar usuário
export const deletarUsuario = async (
  id: number,
  token?: string
): Promise<boolean> => {
  try {
    const headers: HeadersInit = {
      "Content-Type": "application/json",
    };

    // Se houver token, adiciona ao header de autorização
    if (token) {
      headers.Authorization = `Bearer ${token}`;
    }

    const response = await fetch(`${API_BASE_URL}/api/usuario/deletar/${id}`, {
      method: "DELETE",
      headers,
    });

    if (!response.ok) {
      throw new Error(`Erro ao deletar usuário: ${response.statusText}`);
    }

    return true;
  } catch (error) {
    console.error("Erro ao deletar usuário:", error);
    throw error;
  }
};

// Exportação padrão com todos os serviços
const usuarioService = {
  criarUsuario,
  autenticarUsuario,
  obterDadosUsuario,
  buscarTodosUsuarios,
  deletarUsuario,
};

export default usuarioService;
