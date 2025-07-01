// Tipos relacionados aos usuários
export interface Usuario {
  id?: number;
  nome: string;
  email: string;
  senha?: string;
  dataCriacao?: string;
  dataAtualizacao?: string;
}

export interface UsuarioAutenticacao {
  email: string;
  senha: string;
}

export interface UsuarioResponse {
  id: number;
  nome: string;
  email: string;
  dataCriacao?: string;
  dataAtualizacao?: string;
}

export interface UsuarioToken {
  token: string;
  usuario: UsuarioResponse;
  expiresIn?: number;
}

// Tipos para respostas da API
export interface ApiResponse<T> {
  data: T;
  message?: string;
  success: boolean;
}

export interface ApiError {
  message: string;
  status: number;
  errors?: string[];
}
