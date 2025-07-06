/**
 * Hook personalizado para gerenciar autenticação com cookies
 */

import { useState, useEffect, useCallback } from "react";
import type { UsuarioToken, UsuarioResponse } from "../types/usuario";
import { authCookies } from "../utils/cookies";

interface UseAuthReturn {
  /** Dados completos do usuário autenticado */
  dadosUsuario: UsuarioToken | null;
  /** Token de autenticação */
  token: string | null;
  /** Dados do usuário */
  usuario: UsuarioResponse | null;
  /** Se o usuário está autenticado */
  isAuthenticated: boolean;
  /** Se está carregando dados de autenticação */
  isLoading: boolean;
  /** Função para fazer login */
  login: (dadosUsuario: UsuarioToken, expirationHours?: number) => void;
  /** Função para fazer logout */
  logout: () => void;
  /** Recarregar dados de autenticação dos cookies */
  reloadAuth: () => void;
}

/**
 * Hook para gerenciar autenticação usando cookies seguros
 *
 * @example
 * ```tsx
 * import { useAuth } from '../hooks/useAuth';
 *
 * const LoginPage = () => {
 *   const { login, isAuthenticated, usuario } = useAuth();
 *
 *   const handleLoginSuccess = (dados: UsuarioToken) => {
 *     login(dados, 24); // 24 horas de duração
 *   };
 *
 *   if (isAuthenticated) {
 *     return <div>Olá, {usuario?.nome}!</div>;
 *   }
 *
 *   return <GerenciarLogin onLoginSuccess={handleLoginSuccess} />;
 * };
 * ```
 */
export const useAuth = (): UseAuthReturn => {
  const [dadosUsuario, setDadosUsuario] = useState<UsuarioToken | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  /**
   * Carrega dados de autenticação dos cookies
   */
  const loadAuthFromCookies = useCallback(() => {
    setIsLoading(true);

    try {
      const token = authCookies.getToken();
      const userData = authCookies.getUserData<UsuarioResponse>();

      if (token && userData) {
        setDadosUsuario({
          token,
          usuario: userData,
        });
      } else {
        setDadosUsuario(null);
      }
    } catch (error) {
      console.error(
        "Erro ao carregar dados de autenticação dos cookies:",
        error
      );
      setDadosUsuario(null);
      // Limpar cookies corrompidos
      authCookies.clearAuthData();
    } finally {
      setIsLoading(false);
    }
  }, []);

  /**
   * Carrega dados de autenticação na inicialização
   */
  useEffect(() => {
    loadAuthFromCookies();
  }, [loadAuthFromCookies]);

  /**
   * Faz login salvando dados em cookies seguros
   */
  const login = useCallback(
    (newDadosUsuario: UsuarioToken, expirationHours: number = 24) => {
      try {
        // Salvar em cookies
        authCookies.setAuthData(
          newDadosUsuario.token,
          newDadosUsuario.usuario,
          expirationHours
        );

        // Atualizar estado
        setDadosUsuario(newDadosUsuario);
      } catch (error) {
        console.error("Erro ao fazer login:", error);
        throw new Error("Falha ao salvar dados de autenticação");
      }
    },
    []
  );

  /**
   * Faz logout removendo dados dos cookies
   */
  const logout = useCallback(() => {
    try {
      // Limpar cookies
      authCookies.clearAuthData();

      // Limpar estado
      setDadosUsuario(null);
    } catch (error) {
      console.error("Erro ao fazer logout:", error);
    }
  }, []);

  /**
   * Recarrega dados de autenticação dos cookies
   */
  const reloadAuth = useCallback(() => {
    loadAuthFromCookies();
  }, [loadAuthFromCookies]);

  return {
    dadosUsuario,
    token: dadosUsuario?.token || null,
    usuario: dadosUsuario?.usuario || null,
    isAuthenticated: !!dadosUsuario && authCookies.isAuthenticated(),
    isLoading,
    login,
    logout,
    reloadAuth,
  };
};

export default useAuth;
