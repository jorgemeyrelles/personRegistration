// Configuração da API baseada no ambiente
const getApiBaseUrl = (): string => {
  // Em produção no Docker com Nginx proxy, usar mesma origem
  if (import.meta.env.PROD) {
    return ""; // Vazio usa a mesma origem
  }

  // Em desenvolvimento, usar a variável de ambiente ou localhost
  return import.meta.env.VITE_API_BASE_URL || "http://localhost:8081";
};

export const API_BASE_URL = getApiBaseUrl();

// Log para debug
console.log("API_BASE_URL:", API_BASE_URL);
console.log("Environment:", {
  NODE_ENV: import.meta.env.NODE_ENV,
  MODE: import.meta.env.MODE,
  PROD: import.meta.env.PROD,
  DEV: import.meta.env.DEV,
  VITE_API_BASE_URL: import.meta.env.VITE_API_BASE_URL,
});
