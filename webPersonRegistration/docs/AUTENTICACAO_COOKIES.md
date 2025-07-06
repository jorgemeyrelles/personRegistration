# 🔐 Sistema de Autenticação com Cookies Seguros

Este documento descreve como usar o sistema de autenticação baseado em cookies seguros implementado no projeto.

## 📋 Visão Geral

O sistema de autenticação foi projetado para ser:

- **Seguro**: Usa cookies com proteções CSRF e configurações de segurança
- **Simples**: Hook personalizado facilita o uso
- **Flexível**: Funciona em desenvolvimento (localhost) e produção (HTTPS)
- **Persistente**: Mantém o login entre sessões do navegador

## 🏗️ Arquitetura

### Componentes Principais

1. **`/src/utils/cookies.ts`** - Utilitários para gerenciamento de cookies
2. **`/src/hooks/useAuth.ts`** - Hook personalizado para autenticação
3. **`/src/components/GerenciarLogin.tsx`** - Componente de interface de login
4. **`/src/examples/`** - Exemplos de uso e implementação

### Fluxo de Autenticação

```
[Login] → [Validação API] → [Cookies Seguros] → [Estado Reativo] → [Dashboard]
   ↓
[Logout] → [Limpar Cookies] → [Limpar Estado] → [Tela Login]
```

## 🚀 Como Usar

### 1. Hook useAuth (Recomendado)

```tsx
import { useAuth } from "../hooks/useAuth";
import GerenciarLogin from "../components/GerenciarLogin";

const MinhaApp = () => {
  const { isAuthenticated, usuario, login, logout } = useAuth();

  if (isAuthenticated) {
    return (
      <div>
        <h1>Olá, {usuario?.nome}!</h1>
        <button onClick={logout}>Logout</button>
      </div>
    );
  }

  return (
    <GerenciarLogin
      onLoginSuccess={(dados) => login(dados, 24)} // 24 horas
      onRegistrarClick={() => console.log("Registro")}
    />
  );
};
```

### 2. Uso Direto dos Utilitários de Cookies

```tsx
import { authCookies } from "../utils/cookies";

// Salvar dados de autenticação
authCookies.setAuthData(token, userData, 24); // 24 horas

// Verificar se está autenticado
const isAuth = authCookies.isAuthenticated();

// Obter token
const token = authCookies.getToken();

// Obter dados do usuário
const user = authCookies.getUserData();

// Fazer logout
authCookies.clearAuthData();
```

## 🔒 Configurações de Segurança

### Cookies Seguros

Os cookies são configurados com as seguintes proteções:

- **`Secure`**: Apenas HTTPS em produção (auto-detecta localhost)
- **`SameSite=Strict`**: Proteção contra ataques CSRF
- **`Path=/`**: Disponível em toda a aplicação
- **`Max-Age`**: Expiração automática configurável

### Exemplo de Cookie Resultante

```
authToken=eyJhbGciOiJIUzI1NiJ9...; Max-Age=86400; Path=/; Secure; SameSite=Strict
userData={"id":1,"nome":"João"}; Max-Age=86400; Path=/; Secure; SameSite=Strict
```

## 📊 API do Hook useAuth

### Retorno

```typescript
interface UseAuthReturn {
  dadosUsuario: UsuarioToken | null; // Dados completos
  token: string | null; // Token de autenticação
  usuario: UsuarioResponse | null; // Dados do usuário
  isAuthenticated: boolean; // Status de autenticação
  isLoading: boolean; // Carregando dados
  login: (dados, horas?) => void; // Fazer login
  logout: () => void; // Fazer logout
  reloadAuth: () => void; // Recarregar do cookie
}
```

### Métodos

#### `login(dadosUsuario, expirationHours?)`

- **Parâmetros**:
  - `dadosUsuario`: Objeto com token e dados do usuário
  - `expirationHours`: Horas até expirar (padrão: 24)
- **Descrição**: Salva dados em cookies seguros e atualiza estado

#### `logout()`

- **Descrição**: Remove todos os cookies de autenticação e limpa estado

#### `reloadAuth()`

- **Descrição**: Recarrega dados de autenticação dos cookies

## 🌍 Ambiente: Desenvolvimento vs Produção

### Desenvolvimento (localhost)

- Cookies **sem** flag `Secure` (permite HTTP)
- Mesmas proteções CSRF e SameSite
- Logs detalhados no console

### Produção (HTTPS)

- Cookies **com** flag `Secure` obrigatória
- Todas as proteções ativas
- Logs minimizados

## 📝 Exemplos Práticos

### 1. Aplicação Simples

```tsx
// App.tsx
import { useAuth } from "./hooks/useAuth";
import Login from "./components/Login";
import Dashboard from "./components/Dashboard";

function App() {
  const { isAuthenticated, isLoading } = useAuth();

  if (isLoading) return <div>Carregando...</div>;

  return isAuthenticated ? <Dashboard /> : <Login />;
}
```

### 2. Com React Router

```tsx
// App.tsx
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { useAuth } from "./hooks/useAuth";

function App() {
  const { isAuthenticated } = useAuth();

  return (
    <BrowserRouter>
      <Routes>
        <Route
          path="/login"
          element={!isAuthenticated ? <Login /> : <Navigate to="/dashboard" />}
        />
        <Route
          path="/dashboard"
          element={isAuthenticated ? <Dashboard /> : <Navigate to="/login" />}
        />
        <Route path="/" element={<Navigate to="/dashboard" />} />
      </Routes>
    </BrowserRouter>
  );
}
```

### 3. Contexto Global

```tsx
// AuthProvider.tsx
import React, { createContext, useContext } from "react";
import { useAuth } from "../hooks/useAuth";

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const auth = useAuth();
  return <AuthContext.Provider value={auth}>{children}</AuthContext.Provider>;
};

export const useAuthContext = () => useContext(AuthContext);
```

## 🐛 Debugging e Troubleshooting

### Verificar Cookies no Navegador

1. Abra DevTools (F12)
2. Vá para aba **Application/Storage**
3. Procure por **Cookies** → **localhost** (ou seu domínio)
4. Verifique se `authToken` e `userData` existem

### Logs de Debug

```tsx
import { authCookies } from "../utils/cookies";

// Verificar status dos cookies
console.log("Token:", authCookies.getToken());
console.log("User:", authCookies.getUserData());
console.log("Authenticated:", authCookies.isAuthenticated());
```

### Problemas Comuns

1. **Cookies não salvam**: Verifique se está em HTTPS em produção
2. **Login não persiste**: Verifique configurações de SameSite
3. **Erro CORS**: Configure backend para aceitar cookies

## 🔄 Migração do localStorage

Se você estava usando localStorage, a migração é simples:

### Antes (localStorage)

```tsx
// Login
localStorage.setItem("authToken", token);
localStorage.setItem("user", JSON.stringify(user));

// Verificar
const token = localStorage.getItem("authToken");
const user = JSON.parse(localStorage.getItem("user") || "{}");

// Logout
localStorage.removeItem("authToken");
localStorage.removeItem("user");
```

### Depois (Cookies Seguros)

```tsx
// Login
authCookies.setAuthData(token, user, 24);

// Verificar
const token = authCookies.getToken();
const user = authCookies.getUserData();

// Logout
authCookies.clearAuthData();
```

## 📚 Referências

- [MDN - HTTP Cookies](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies)
- [OWASP - Session Management](https://owasp.org/www-project-web-security-testing-guide/v42/4-Web_Application_Security_Testing/06-Session_Management_Testing/)
- [SameSite Cookies Explained](https://web.dev/samesite-cookies-explained/)

## 🎯 Próximos Passos

1. **Implementar refresh token** para renovação automática
2. **Adicionar middleware de expiração** no backend
3. **Implementar logout em todas as abas** usando BroadcastChannel
4. **Adicionar criptografia local** para dados sensíveis
5. **Implementar rate limiting** para tentativas de login
