// import { API_BASE_URL } from "../../config/api";

import type {
  RegistroPessoaRequest,
  RegistroPessoaUpdateRequest,
  RegistroPessoaResponse,
  RegistroPessoaListResponse,
  CPFExisteResponse,
} from "../../types/registroPessoasTypes";

const API_BASE_URL =
  import.meta.env.VITE_API_BASE_URL || "http://localhost:8081";

if (import.meta.env.DEV) {
}

// Serviço para criar registro de pessoa
export const criarRegistroPessoa = async (
  pessoa: RegistroPessoaRequest,
  token?: string
): Promise<RegistroPessoaResponse> => {
  try {
    const headers: HeadersInit = {
      "Content-Type": "application/json",
    };

    // Se houver token, adiciona ao header de autorização
    if (token) {
      headers.Authorization = `Bearer ${token}`;
    }

    const response = await fetch(`${API_BASE_URL}/api/registro-pessoa`, {
      method: "POST",
      headers,
      body: JSON.stringify(pessoa),
    });

    if (!response.ok) {
      const errorData = await response.json().catch(() => ({}));
      throw new Error(
        errorData.message ||
          `Erro HTTP: ${response.status} ${response.statusText}`
      );
    }

    return await response.json();
  } catch (error) {
    console.error("Erro ao criar registro de pessoa:", error);
    throw error;
  }
};

// ✅ CORREÇÃO: Adicionar método que estava faltando
export const buscarTodosRegistrosPessoas = async (
  token?: string
): Promise<RegistroPessoaListResponse> => {
  try {
    const headers: HeadersInit = {
      "Content-Type": "application/json",
    };

    if (token) {
      headers.Authorization = `Bearer ${token}`;
    }

    const response = await fetch(`${API_BASE_URL}/api/registro-pessoa`, {
      method: "GET",
      headers,
    });

    if (!response.ok) {
      throw new Error(`Erro HTTP: ${response.status}`);
    }

    return await response.json();
  } catch (error) {
    console.error("Erro ao buscar registros de pessoas:", error);
    throw error;
  }
};

export const obterRegistroPessoa = async (
  id: string,
  token?: string
): Promise<RegistroPessoaResponse> => {
  try {
    const headers: HeadersInit = {
      "Content-Type": "application/json",
    };

    if (token) {
      headers.Authorization = `Bearer ${token}`;
    }

    const response = await fetch(`${API_BASE_URL}/api/registro-pessoa/${id}`, {
      method: "GET",
      headers,
    });

    if (!response.ok) {
      throw new Error(`Erro HTTP: ${response.status}`);
    }

    return await response.json();
  } catch (error) {
    console.error("Erro ao obter registro de pessoa:", error);
    throw error;
  }
};

export const verificarCPFExiste = async (
  cpf: string,
  token?: string
): Promise<CPFExisteResponse> => {
  try {
    const headers: HeadersInit = {
      "Content-Type": "application/json",
    };

    if (token) {
      headers.Authorization = `Bearer ${token}`;
    }

    const response = await fetch(
      `${API_BASE_URL}/api/registro-pessoa/existe-cpf/${cpf}`,
      {
        method: "GET",
        headers,
      }
    );

    if (!response.ok) {
      throw new Error(`Erro HTTP: ${response.status}`);
    }

    return await response.json();
  } catch (error) {
    console.error("Erro ao verificar CPF:", error);
    throw error;
  }
};

export const atualizarRegistroPessoa = async (
  id: string,
  pessoa: RegistroPessoaUpdateRequest,
  token?: string
): Promise<RegistroPessoaResponse> => {
  try {
    const headers: HeadersInit = {
      "Content-Type": "application/json",
    };

    if (token) {
      headers.Authorization = `Bearer ${token}`;
    }

    const response = await fetch(`${API_BASE_URL}/api/registro-pessoa/${id}`, {
      method: "PUT",
      headers,
      body: JSON.stringify(pessoa),
    });

    if (!response.ok) {
      const errorData = await response.json().catch(() => ({}));
      throw new Error(
        errorData.message ||
          `Erro HTTP: ${response.status} ${response.statusText}`
      );
    }

    return await response.json();
  } catch (error) {
    console.error("Erro ao atualizar registro de pessoa:", error);
    throw error;
  }
};

export const deletarRegistroPessoa = async (
  id: string,
  token?: string
): Promise<void> => {
  try {
    const headers: HeadersInit = {
      "Content-Type": "application/json",
    };

    if (token) {
      headers.Authorization = `Bearer ${token}`;
    }

    const response = await fetch(`${API_BASE_URL}/api/registro-pessoa/${id}`, {
      method: "DELETE",
      headers,
    });

    if (!response.ok) {
      throw new Error(`Erro HTTP: ${response.status}`);
    }
  } catch (error) {
    console.error("Erro ao deletar registro de pessoa:", error);
    throw error;
  }
};

// Exportação padrão com todos os serviços
const registroPessoaService = {
  criarRegistroPessoa,
  obterRegistroPessoa,
  atualizarRegistroPessoa,
  deletarRegistroPessoa,
  buscarTodosRegistrosPessoas,
  verificarCPFExiste,
};

export default registroPessoaService;
