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
      throw new Error(
        `Erro ao criar registro de pessoa: ${response.statusText}`
      );
    }

    return await response.json();
  } catch (error) {
    console.error("Erro ao criar registro de pessoa:", error);
    throw error;
  }
};

// Serviço para obter registro de pessoa por ID
export const obterRegistroPessoa = async (
  id: string,
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

    const response = await fetch(`${API_BASE_URL}/api/registro-pessoa/${id}`, {
      method: "GET",
      headers,
    });

    if (!response.ok) {
      throw new Error(
        `Erro ao obter registro de pessoa: ${response.statusText}`
      );
    }

    return await response.json();
  } catch (error) {
    console.error("Erro ao obter registro de pessoa:", error);
    throw error;
  }
};

// Serviço para atualizar registro de pessoa
export const atualizarRegistroPessoa = async (
  id: string,
  dadosAtualizacao: RegistroPessoaUpdateRequest,
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

    const response = await fetch(`${API_BASE_URL}/api/registro-pessoa/${id}`, {
      method: "PUT",
      headers,
      body: JSON.stringify(dadosAtualizacao),
    });

    if (!response.ok) {
      throw new Error(
        `Erro ao atualizar registro de pessoa: ${response.statusText}`
      );
    }

    return await response.json();
  } catch (error) {
    console.error("Erro ao atualizar registro de pessoa:", error);
    throw error;
  }
};

// Serviço para deletar registro de pessoa
export const deletarRegistroPessoa = async (
  id: string,
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

    const response = await fetch(`${API_BASE_URL}/api/registro-pessoa/${id}`, {
      method: "DELETE",
      headers,
    });

    if (!response.ok) {
      throw new Error(
        `Erro ao deletar registro de pessoa: ${response.statusText}`
      );
    }

    return true;
  } catch (error) {
    console.error("Erro ao deletar registro de pessoa:", error);
    throw error;
  }
};

// Serviço para buscar todos os registros de pessoas
export const buscarTodosRegistrosPessoas = async (
  token?: string
): Promise<RegistroPessoaListResponse> => {
  try {
    const headers: HeadersInit = {
      "Content-Type": "application/json",
    };

    // Se houver token, adiciona ao header de autorização
    if (token) {
      headers.Authorization = `Bearer ${token}`;
    }

    const response = await fetch(`${API_BASE_URL}/api/registro-pessoa`, {
      method: "GET",
      headers,
    });

    if (!response.ok) {
      throw new Error(
        `Erro ao buscar registros de pessoas: ${response.statusText}`
      );
    }

    return await response.json();
  } catch (error) {
    console.error("Erro ao buscar registros de pessoas:", error);
    throw error;
  }
};

// Serviço para buscar pessoa por telefone
export const buscarPessoaPorTelefone = async (
  telefone: string,
  token?: string
): Promise<RegistroPessoaListResponse> => {
  try {
    const headers: HeadersInit = {
      "Content-Type": "application/json",
    };

    // Se houver token, adiciona ao header de autorização
    if (token) {
      headers.Authorization = `Bearer ${token}`;
    }

    const response = await fetch(
      `${API_BASE_URL}/api/registro-pessoa/telefone/${telefone}`,
      {
        method: "GET",
        headers,
      }
    );

    if (!response.ok) {
      throw new Error(
        `Erro ao buscar pessoa por telefone: ${response.statusText}`
      );
    }

    return await response.json();
  } catch (error) {
    console.error("Erro ao buscar pessoa por telefone:", error);
    throw error;
  }
};

// Serviço para buscar pessoa por nome
export const buscarPessoaPorNome = async (
  nome: string,
  token?: string
): Promise<RegistroPessoaListResponse> => {
  try {
    const headers: HeadersInit = {
      "Content-Type": "application/json",
    };

    // Se houver token, adiciona ao header de autorização
    if (token) {
      headers.Authorization = `Bearer ${token}`;
    }

    const response = await fetch(
      `${API_BASE_URL}/api/registro-pessoa/nome/${nome}`,
      {
        method: "GET",
        headers,
      }
    );

    if (!response.ok) {
      throw new Error(`Erro ao buscar pessoa por nome: ${response.statusText}`);
    }

    return await response.json();
  } catch (error) {
    console.error("Erro ao buscar pessoa por nome:", error);
    throw error;
  }
};

// Serviço para verificar se CPF existe
export const verificarCPFExiste = async (
  cpf: string,
  token?: string
): Promise<CPFExisteResponse> => {
  try {
    const headers: HeadersInit = {
      "Content-Type": "application/json",
    };

    // Se houver token, adiciona ao header de autorização
    if (token) {
      headers.Authorization = `Bearer ${token}`;
    }

    const response = await fetch(
      `${API_BASE_URL}/api/registro-pessoa/existe/cpf/${cpf}`,
      {
        method: "GET",
        headers,
      }
    );

    if (!response.ok) {
      throw new Error(
        `Erro ao verificar se CPF existe: ${response.statusText}`
      );
    }

    return await response.json();
  } catch (error) {
    console.error("Erro ao verificar se CPF existe:", error);
    throw error;
  }
};

// Serviço para buscar pessoa por CPF
export const buscarPessoaPorCPF = async (
  cpf: string,
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

    const response = await fetch(
      `${API_BASE_URL}/api/registro-pessoa/cpf/${cpf}`,
      {
        method: "GET",
        headers,
      }
    );

    if (!response.ok) {
      throw new Error(`Erro ao buscar pessoa por CPF: ${response.statusText}`);
    }

    return await response.json();
  } catch (error) {
    console.error("Erro ao buscar pessoa por CPF:", error);
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
  buscarPessoaPorTelefone,
  buscarPessoaPorNome,
  verificarCPFExiste,
  buscarPessoaPorCPF,
};

export default registroPessoaService;
