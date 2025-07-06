// Tipos relacionados aos usuários
export interface Usuario {
  id?: number;
  nome: string;
  email: string;
  senha?: string;
  perfilId?: string;
  dataCriacao?: string;
  dataAtualizacao?: string;
}

export interface UsuarioAutenticacao {
  email: string;
  senha: string;
}

// Tipo para resposta de autenticação
export interface AutenticarUsuarioResponse {
  id: string;
  nome: string;
  email: string;
  dataHoraAcesso: string;
  tokenAcesso: string;
  dataHoraExpiracao: string;
  nomePerfil: string;
}

// Tipos para atualização de senha
export interface AtualizarSenhaRequest {
  email: string;
  senha: string;
}

export interface AtualizarSenhaResponse {
  id: string;
  email: string;
  dataHoraAtualizacao: string;
}

export interface UsuarioResponse {
  id: number | string; // Aceita tanto number quanto string (UUID)
  nome: string;
  email: string;
  nomePerfil?: string; // Perfil do usuário (ADMIN, USER, etc.)
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
