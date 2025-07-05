// Configuração centralizada da API

export const API_BASE_URL =
  import.meta.env.VITE_API_BASE_URL || "http://localhost:8081";

export const API_CONFIG = {
  baseURL: API_BASE_URL,
  timeout: 10000,
  headers: {
    "Content-Type": "application/json",
  },
};

// Configurações condicionais baseadas no ambiente
if (import.meta.env.DEV) {
  console.log("🌐 API Configuration:", API_CONFIG);
}

export default API_CONFIG;
