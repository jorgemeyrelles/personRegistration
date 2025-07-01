import type {
  PerfilCriar,
  PerfilAtualizar,
  PerfilResponse,
} from "../../types/perfil";

const API_BASE_URL =
  import.meta.env.VITE_API_BASE_URL || "http://api_person_registration:8080";

// Serviço para criar perfil
export const criarPerfil = async (
  perfil: PerfilCriar,
  token?: string
): Promise<PerfilResponse> => {
  try {
    const headers: HeadersInit = {
      "Content-Type": "application/json",
    };

    // Se houver token, adiciona ao header de autorização
    if (token) {
      headers.Authorization = `Bearer ${token}`;
    }

    const response = await fetch(`${API_BASE_URL}/api/perfil/criar`, {
      method: "POST",
      headers,
      body: JSON.stringify(perfil),
    });

    if (!response.ok) {
      throw new Error(`Erro ao criar perfil: ${response.statusText}`);
    }

    return await response.json();
  } catch (error) {
    console.error("Erro ao criar perfil:", error);
    throw error;
  }
};

// Serviço para atualizar perfil
export const atualizarPerfil = async (
  id: number,
  dadosAtualizacao: PerfilAtualizar,
  token?: string
): Promise<PerfilResponse> => {
  try {
    const headers: HeadersInit = {
      "Content-Type": "application/json",
    };

    // Se houver token, adiciona ao header de autorização
    if (token) {
      headers.Authorization = `Bearer ${token}`;
    }

    const response = await fetch(`${API_BASE_URL}/api/perfil/atualizar/${id}`, {
      method: "PUT",
      headers,
      body: JSON.stringify(dadosAtualizacao),
    });

    if (!response.ok) {
      throw new Error(`Erro ao atualizar perfil: ${response.statusText}`);
    }

    return await response.json();
  } catch (error) {
    console.error("Erro ao atualizar perfil:", error);
    throw error;
  }
};

// Serviço para obter dados do perfil por ID
export const obterDadosPerfil = async (
  id: number,
  token?: string
): Promise<PerfilResponse> => {
  try {
    const headers: HeadersInit = {
      "Content-Type": "application/json",
    };

    // Se houver token, adiciona ao header de autorização
    if (token) {
      headers.Authorization = `Bearer ${token}`;
    }

    const response = await fetch(
      `${API_BASE_URL}/api/perfil/obter-dados/${id}`,
      {
        method: "GET",
        headers,
      }
    );

    if (!response.ok) {
      throw new Error(`Erro ao obter dados do perfil: ${response.statusText}`);
    }

    return await response.json();
  } catch (error) {
    console.error("Erro ao obter dados do perfil:", error);
    throw error;
  }
};

// Serviço para buscar todos os perfis
export const buscarTodosPerfis = async (
  token?: string
): Promise<PerfilResponse[]> => {
  try {
    const headers: HeadersInit = {
      "Content-Type": "application/json",
    };

    // Se houver token, adiciona ao header de autorização
    if (token) {
      headers.Authorization = `Bearer ${token}`;
    }

    const response = await fetch(`${API_BASE_URL}/api/perfil/buscar-todos`, {
      method: "GET",
      headers,
    });

    if (!response.ok) {
      throw new Error(`Erro ao buscar perfis: ${response.statusText}`);
    }

    return await response.json();
  } catch (error) {
    console.error("Erro ao buscar perfis:", error);
    throw error;
  }
};

// Serviço para deletar perfil
export const deletarPerfil = async (
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

    const response = await fetch(`${API_BASE_URL}/api/perfil/deletar/${id}`, {
      method: "DELETE",
      headers,
    });

    if (!response.ok) {
      throw new Error(`Erro ao deletar perfil: ${response.statusText}`);
    }

    return true;
  } catch (error) {
    console.error("Erro ao deletar perfil:", error);
    throw error;
  }
};

// Exportação padrão com todos os serviços
const perfilService = {
  criarPerfil,
  atualizarPerfil,
  obterDadosPerfil,
  buscarTodosPerfis,
  deletarPerfil,
};

export default perfilService;
