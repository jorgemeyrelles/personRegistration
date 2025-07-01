// Tipos relacionados aos perfis
export interface Perfil {
  id?: number;
  nome: string;
  descricao?: string;
  usuarioId?: number;
  telefone?: string;
  endereco?: string;
  dataNascimento?: string;
  foto?: string;
  dataCriacao?: string;
  dataAtualizacao?: string;
}

export interface PerfilCriar {
  nome: string;
  descricao?: string;
  usuarioId?: number;
  telefone?: string;
  endereco?: string;
  dataNascimento?: string;
  foto?: string;
}

export interface PerfilAtualizar {
  nome?: string;
  descricao?: string;
  telefone?: string;
  endereco?: string;
  dataNascimento?: string;
  foto?: string;
}

export interface PerfilResponse {
  id: number;
  nome: string;
  descricao?: string;
  usuarioId?: number;
  telefone?: string;
  endereco?: string;
  dataNascimento?: string;
  foto?: string;
  dataCriacao?: string;
  dataAtualizacao?: string;
}
