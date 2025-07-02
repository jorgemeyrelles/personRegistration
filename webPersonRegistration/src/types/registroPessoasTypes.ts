// Tipos específicos para Registro de Pessoas conforme API

// Tipos para usuário e perfil (relacionamentos)
export interface PerfilUsuario {
  id: string;
  nome: string;
}

export interface UsuarioRegistro {
  id: string;
  nome: string;
  email: string;
  perfil: PerfilUsuario;
}

// Tipo para request de criação de registro de pessoa
export interface RegistroPessoaRequest {
  nome: string;
  telefone: string;
  cpf: string;
  numero?: string; // Opcional
  complemento?: string; // Opcional
  cep: string;
  bairro: string;
  nomeMunicipio: string;
  nomeEstado: string;
  latitude?: string; // Opcional
  longitude?: string; // Opcional
  usuarioId: string;
}

// Tipo para response de registro de pessoa (individual)
export interface RegistroPessoaResponse {
  id: string; // UUID conforme API
  nome: string;
  telefone: string;
  cpf: string;
  numero?: string; // Opcional
  complemento?: string; // Opcional
  cep: string;
  bairro: string;
  nomeMunicipio: string;
  nomeEstado: string;
  usuarioId: string;
  latitude?: string; // Opcional
  longitude?: string; // Opcional
  usuario: UsuarioRegistro;
}

// Tipo para lista de registros de pessoas
export type RegistroPessoaListResponse = RegistroPessoaResponse[];

// Tipo para atualização de registro de pessoa
export interface RegistroPessoaUpdateRequest {
  nome?: string;
  telefone?: string;
  cpf?: string;
  numero?: string;
  complemento?: string;
  cep?: string;
  bairro?: string;
  nomeMunicipio?: string;
  nomeEstado?: string;
  latitude?: string;
  longitude?: string;
  usuarioId?: string;
}

// Tipo para response de atualização (mesmo que response normal)
export type RegistroPessoaUpdateResponse = RegistroPessoaResponse;

// Tipo para formulário do componente (campos opcionais para UX)
export interface RegistroPessoaFormData {
  nome: string;
  telefone: string;
  cpf: string;
  numero?: string; // Opcional
  complemento?: string; // Opcional
  cep: string;
  bairro: string;
  nomeMunicipio: string;
  nomeEstado: string;
  latitude?: string; // Opcional
  longitude?: string; // Opcional
  usuarioId?: string; // Pode ser opcional no formulário
}

// Tipo para verificação de CPF
export interface CPFExisteResponse {
  existe: boolean;
  mensagem?: string;
}
