/**
 * Utilitários para gerenciamento seguro de cookies
 */

// Configurações padrão para cookies seguros
const DEFAULT_COOKIE_OPTIONS = {
  secure: true, // HTTPS obrigatório em produção
  sameSite: "Strict" as const, // Proteção CSRF
  path: "/", // Disponível em toda a aplicação
};

/**
 * Define um cookie com opções de segurança
 * @param name Nome do cookie
 * @param value Valor do cookie
 * @param options Opções adicionais do cookie
 */
export const setCookie = (
  name: string,
  value: string,
  options: {
    maxAge?: number; // Tempo de vida em segundos
    expires?: Date; // Data de expiração
    secure?: boolean; // HTTPS obrigatório
    sameSite?: "Strict" | "Lax" | "None"; // Proteção CSRF
    path?: string; // Caminho onde o cookie é válido
    httpOnly?: boolean; // Não acessível via JavaScript (só para cookies do servidor)
  } = {}
): void => {
  // Em desenvolvimento (localhost), secure pode ser false
  const isLocalhost =
    window.location.hostname === "localhost" ||
    window.location.hostname === "127.0.0.1";

  const finalOptions = {
    ...DEFAULT_COOKIE_OPTIONS,
    ...options,
    secure: isLocalhost
      ? false
      : options.secure ?? DEFAULT_COOKIE_OPTIONS.secure,
  };

  let cookieString = `${encodeURIComponent(name)}=${encodeURIComponent(value)}`;

  if (finalOptions.maxAge !== undefined) {
    cookieString += `; Max-Age=${finalOptions.maxAge}`;
  }

  if (finalOptions.expires) {
    cookieString += `; Expires=${finalOptions.expires.toUTCString()}`;
  }

  if (finalOptions.path) {
    cookieString += `; Path=${finalOptions.path}`;
  }

  if (finalOptions.secure) {
    cookieString += "; Secure";
  }

  if (finalOptions.sameSite) {
    cookieString += `; SameSite=${finalOptions.sameSite}`;
  }

  if (finalOptions.httpOnly) {
    cookieString += "; HttpOnly";
  }

  document.cookie = cookieString;
};

/**
 * Obtém o valor de um cookie
 * @param name Nome do cookie
 * @returns Valor do cookie ou null se não encontrado
 */
export const getCookie = (name: string): string | null => {
  const encodedName = encodeURIComponent(name);
  const cookies = document.cookie.split(";");

  for (let cookie of cookies) {
    const [cookieName, cookieValue] = cookie.trim().split("=");

    if (cookieName === encodedName) {
      return decodeURIComponent(cookieValue || "");
    }
  }

  return null;
};

/**
 * Remove um cookie
 * @param name Nome do cookie
 * @param path Caminho do cookie (deve ser o mesmo usado ao definir)
 */
export const removeCookie = (name: string, path: string = "/"): void => {
  setCookie(name, "", {
    maxAge: 0, // Expira imediatamente
    path: path,
  });
};

/**
 * Verifica se um cookie existe
 * @param name Nome do cookie
 * @returns true se o cookie existe, false caso contrário
 */
export const hasCookie = (name: string): boolean => {
  return getCookie(name) !== null;
};

/**
 * Utilitários específicos para autenticação
 */
export const authCookies = {
  // Nome dos cookies de autenticação
  TOKEN_KEY: "authToken",
  USER_KEY: "userData",

  /**
   * Salva os dados de autenticação em cookies seguros
   * @param token Token de autenticação
   * @param userData Dados do usuário (será serializado em JSON)
   * @param expirationHours Horas até expirar (padrão: 24h)
   */
  setAuthData: (
    token: string,
    userData: object,
    expirationHours: number = 24
  ): void => {
    const maxAge = expirationHours * 60 * 60; // Converte horas para segundos

    // Salva o token
    setCookie(authCookies.TOKEN_KEY, token, {
      maxAge,
      sameSite: "Strict",
    });

    // Salva os dados do usuário (serializados)
    setCookie(authCookies.USER_KEY, JSON.stringify(userData), {
      maxAge,
      sameSite: "Strict",
    });
  },

  /**
   * Obtém o token de autenticação
   * @returns Token ou null se não encontrado
   */
  getToken: (): string | null => {
    return getCookie(authCookies.TOKEN_KEY);
  },

  /**
   * Obtém os dados do usuário
   * @returns Dados do usuário ou null se não encontrado
   */
  getUserData: <T = any>(): T | null => {
    const userData = getCookie(authCookies.USER_KEY);

    if (!userData) {
      return null;
    }

    try {
      return JSON.parse(userData) as T;
    } catch (error) {
      console.error("Erro ao parsear dados do usuário do cookie:", error);
      return null;
    }
  },

  /**
   * Remove todos os dados de autenticação
   */
  clearAuthData: (): void => {
    removeCookie(authCookies.TOKEN_KEY);
    removeCookie(authCookies.USER_KEY);
  },

  /**
   * Verifica se o usuário está autenticado
   * @returns true se há token válido, false caso contrário
   */
  isAuthenticated: (): boolean => {
    const token = authCookies.getToken();
    return !!token && token.trim().length > 0;
  },
};
