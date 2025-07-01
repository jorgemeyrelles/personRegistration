// Tipos relacionados ao registro de pessoas
export interface RegistroPessoa {
  id?: number;
  nome: string;
  cpf: string;
  telefone?: string;
  email?: string;
  endereco?: string;
  dataNascimento?: string;
  profissao?: string;
  estadoCivil?: string;
  observacoes?: string;
  dataCriacao?: string;
  dataAtualizacao?: string;
}

export interface RegistroPessoaCriar {
  nome: string;
  cpf: string;
  telefone?: string;
  email?: string;
  endereco?: string;
  dataNascimento?: string;
  profissao?: string;
  estadoCivil?: string;
  observacoes?: string;
}

export interface RegistroPessoaAtualizar {
  nome?: string;
  cpf?: string;
  telefone?: string;
  email?: string;
  endereco?: string;
  dataNascimento?: string;
  profissao?: string;
  estadoCivil?: string;
  observacoes?: string;
}

export interface RegistroPessoaResponse {
  id: number;
  nome: string;
  cpf: string;
  telefone?: string;
  email?: string;
  endereco?: string;
  dataNascimento?: string;
  profissao?: string;
  estadoCivil?: string;
  observacoes?: string;
  dataCriacao?: string;
  dataAtualizacao?: string;
}

export interface CPFExisteResponse {
  existe: boolean;
  pessoa?: RegistroPessoaResponse;
}
