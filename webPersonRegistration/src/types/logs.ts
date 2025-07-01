// Tipos relacionados aos logs do sistema
export interface LogOperacao {
  id: string;
  dataHora: string;
  operacao: string;
  status: string;
  descricao: string;
}

export interface LogMensageria {
  id: string;
  dataHora: string;
  operacao: string;
  status: string;
  descricao: string;
}

export interface LogOperacaoResponse {
  id: string;
  dataHora: string;
  operacao: string;
  status: string;
  descricao: string;
}

export interface LogMensageriaResponse {
  id: string;
  dataHora: string;
  operacao: string;
  status: string;
  descricao: string;
}

export interface LogsFiltro {
  dataInicio?: string;
  dataFim?: string;
  usuario?: string;
  operacao?: string;
  status?: string;
  limite?: number;
  pagina?: number;
}
