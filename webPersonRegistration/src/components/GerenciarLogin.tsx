import React, { useState } from "react";
import {
  Box,
  Card,
  CardContent,
  TextField,
  Button,
  Typography,
  Alert,
  CircularProgress,
  InputAdornment,
  Divider,
  useTheme,
  useMediaQuery,
} from "@mui/material";
import { Link, useNavigate } from "react-router-dom";
import {
  Visibility,
  VisibilityOff,
  Email,
  Lock,
  Login,
  PersonAdd,
} from "@mui/icons-material";
import { colors, withOpacity } from "../utils/colors";
import { autenticarUsuario } from "../service/usuarios";
import { useAuth } from "../hooks/useAuth";
import { IconButton } from "@mui/material";
import type {
  UsuarioAutenticacao,
  UsuarioToken,
  AutenticarUsuarioResponse,
} from "../types/usuario";

interface GerenciarLoginProps {
  onLoginSuccess?: (dadosUsuario: UsuarioToken) => void;
  onRegistrarClick?: () => void;
}

const GerenciarLogin: React.FC<GerenciarLoginProps> = ({
  onLoginSuccess,
  onRegistrarClick,
}) => {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down("sm"));
  const navigate = useNavigate();
  const { login } = useAuth(); // Hook para gerenciar autenticação

  // Estados do formulário
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [mostrarSenha, setMostrarSenha] = useState(false);
  const [loading, setLoading] = useState(false);
  const [erro, setErro] = useState<string | null>(null);
  const [sucesso, setSucesso] = useState<string | null>(null);

  // Validações
  const [errosValidacao, setErrosValidacao] = useState({
    email: "",
    senha: "",
  });

  // Função para validar email
  const validarEmail = (email: string): boolean => {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(email);
  };

  // Função para validar campos
  const validarFormulario = (): boolean => {
    const novosErros = {
      email: "",
      senha: "",
    };

    if (!email.trim()) {
      novosErros.email = "Email é obrigatório";
    } else if (!validarEmail(email)) {
      novosErros.email = "Email inválido";
    }

    if (!senha.trim()) {
      novosErros.senha = "Senha é obrigatória";
    } else if (senha.length < 6) {
      novosErros.senha = "Senha deve ter pelo menos 6 caracteres";
    }

    setErrosValidacao(novosErros);
    return !novosErros.email && !novosErros.senha;
  };

  // Função para fazer login
  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    setErro(null);
    setSucesso(null);

    if (!validarFormulario()) {
      return;
    }

    setLoading(true);

    try {
      const credenciais: UsuarioAutenticacao = {
        email: email.trim(),
        senha: senha,
      };

      const resultado = await autenticarUsuario(credenciais);

      setSucesso("Login realizado com sucesso!");

      // Mapear resposta da API para UsuarioToken
      const dadosUsuario: UsuarioToken = {
        token: resultado.tokenAcesso,
        usuario: {
          id: resultado.id, // Manter como string (UUID)
          nome: resultado.nome,
          email: resultado.email,
          nomePerfil: resultado.nomePerfil, // Incluir perfil do usuário
          dataCriacao: resultado.dataHoraAcesso,
        },
        expiresIn: new Date(resultado.dataHoraExpiracao).getTime() - Date.now(), // Calcular tempo até expiração em ms
      };

      // Salvar dados de autenticação em cookies seguros
      const horasAteExpiracao = Math.max(
        1,
        Math.ceil(dadosUsuario.expiresIn! / (1000 * 60 * 60))
      ); // Converter ms para horas
      login(dadosUsuario, horasAteExpiracao);

      // Chamar callback de sucesso
      if (onLoginSuccess) {
        onLoginSuccess(dadosUsuario);
      }

      // Redirecionar para a home após login bem-sucedido
      setTimeout(() => {
        navigate("/");
      }, 1500); // Aguarda 1.5s para mostrar mensagem de sucesso

      // Limpar formulário
      setEmail("");
      setSenha("");
      setErrosValidacao({ email: "", senha: "" });
    } catch (error) {
      console.error("❌ Erro no login:", error);
      const mensagemErro =
        error instanceof Error
          ? error.message
          : "Erro ao fazer login. Tente novamente.";
      setErro(mensagemErro);
    } finally {
      setLoading(false);
    }
  };

  // Função para alternar visibilidade da senha
  const handleTogglePassword = () => {
    setMostrarSenha(!mostrarSenha);
  };

  // Função para lidar com registro (placeholder)
  const handleRegistrar = () => {
    if (onRegistrarClick) {
      onRegistrarClick();
    } else {
      navigate("/registrar"); // Redireciona para página de registro
    }
  };

  return (
    <Box
      sx={{
        minHeight: "55vh",
        background: colors.gradients.hero,
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        padding: isMobile ? 1 : 2,
      }}
    >
      <Card
        elevation={8}
        sx={{
          maxWidth: 400,
          width: "100%",
          borderRadius: 2,
          background: colors.background.paper,
          boxShadow: colors.shadows.heavy,
        }}
      >
        <CardContent sx={{ padding: isMobile ? 1.5 : 2.5 }}>
          {/* Header */}
          <Box sx={{ textAlign: "center", marginBottom: 2.5 }}>
            <Box
              sx={{
                width: 55,
                height: 55,
                borderRadius: "50%",
                background: colors.gradients.primary,
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                margin: "0 auto 10px auto",
                boxShadow: colors.shadows.medium,
              }}
            >
              <Login sx={{ fontSize: 28, color: colors.text.onPrimary }} />
            </Box>
            <Typography
              variant="h6"
              fontWeight="bold"
              color={colors.text.primary}
              gutterBottom
              sx={{ marginBottom: 0.5 }}
            >
              Bem-vindo
            </Typography>
            <Typography variant="caption" color={colors.text.secondary}>
              Faça login para acessar o sistema
            </Typography>
          </Box>

          {/* Alertas */}
          {erro && (
            <Alert
              severity="error"
              sx={{ marginBottom: 1, fontSize: "0.8rem", py: 0.5 }}
              onClose={() => setErro(null)}
            >
              {erro}
            </Alert>
          )}

          {sucesso && (
            <Alert
              severity="success"
              sx={{ marginBottom: 1, fontSize: "0.8rem", py: 0.5 }}
              onClose={() => setSucesso(null)}
            >
              {sucesso}
            </Alert>
          )}

          {/* Formulário */}
          <Box component="form" onSubmit={handleLogin} noValidate>
            {/* Campo Email */}
            <TextField
              fullWidth
              label="Email"
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              error={!!errosValidacao.email}
              disabled={loading}
              autoComplete="email"
              autoFocus
              size="small"
              sx={{ marginBottom: 1.2 }}
              InputProps={{
                startAdornment: (
                  <InputAdornment position="start">
                    <Email sx={{ color: colors.primary.main, fontSize: 18 }} />
                  </InputAdornment>
                ),
              }}
            />

            {/* Campo Senha */}
            <TextField
              fullWidth
              label="Senha"
              type={mostrarSenha ? "text" : "password"}
              value={senha}
              onChange={(e) => setSenha(e.target.value)}
              error={!!errosValidacao.senha}
              disabled={loading}
              autoComplete="current-password"
              size="small"
              sx={{ marginBottom: 1.5 }}
              InputProps={{
                startAdornment: (
                  <InputAdornment position="start">
                    <Lock sx={{ color: colors.primary.main, fontSize: 18 }} />
                  </InputAdornment>
                ),
                endAdornment: (
                  <InputAdornment position="end">
                    <IconButton
                      onClick={handleTogglePassword}
                      disabled={loading}
                      edge="end"
                      size="small"
                    >
                      {mostrarSenha ? <VisibilityOff /> : <Visibility />}
                    </IconButton>
                  </InputAdornment>
                ),
              }}
            />

            {/* Link Esqueci minha senha */}
            <Box sx={{ textAlign: "right", marginBottom: 1.2 }}>
              <Typography
                variant="caption"
                component={Link}
                to={loading ? "#" : "/recuperar-senha"}
                onClick={loading ? (e: any) => e.preventDefault() : undefined}
                sx={{
                  color: colors.primary.main,
                  cursor: loading ? "not-allowed" : "pointer",
                  textDecoration: "none",
                  fontSize: "0.7rem",
                  "&:hover": {
                    textDecoration: "underline",
                    color: colors.primary.dark,
                  },
                  "&:visited": {
                    color: colors.primary.main,
                  },
                  ...(loading && {
                    color: colors.grey[400],
                    cursor: "not-allowed",
                    pointerEvents: "none",
                  }),
                }}
              >
                Esqueci minha senha
              </Typography>
            </Box>

            {/* Botões lado a lado */}
            <Box sx={{ display: "flex", gap: 1.5, marginBottom: 1.2 }}>
              {/* Botão Login */}
              <Button
                type="submit"
                variant="contained"
                size="medium"
                disabled={loading}
                sx={{
                  flex: 1,
                  height: 38,
                  background: colors.gradients.primary,
                  color: colors.text.onPrimary,
                  fontWeight: "bold",
                  fontSize: "0.85rem",
                  "&:hover": {
                    background: colors.primary.dark,
                    transform: "translateY(-1px)",
                    boxShadow: colors.shadows.medium,
                  },
                  "&:disabled": {
                    background: colors.grey[400],
                  },
                }}
                startIcon={
                  loading ? (
                    <CircularProgress size={14} color="inherit" />
                  ) : (
                    <Login sx={{ fontSize: 16 }} />
                  )
                }
              >
                {loading ? "Entrando..." : "Entrar"}
              </Button>

              {/* Botão Registrar */}
              <Box
                component={Link}
                to="/registrar"
                sx={{
                  flex: 1,
                  height: 38,
                  display: "flex",
                  alignItems: "center",
                  justifyContent: "center",
                  gap: 0.5,
                  border: `1px solid ${colors.primary.main}`,
                  borderRadius: 1,
                  backgroundColor: "transparent",
                  color: colors.primary.main,
                  fontWeight: "bold",
                  fontSize: "0.85rem",
                  textDecoration: "none",
                  cursor: loading ? "not-allowed" : "pointer",
                  transition: "all 0.2s ease-in-out",
                  "&:hover": {
                    borderColor: colors.primary.dark,
                    color: colors.primary.dark,
                    backgroundColor: withOpacity(colors.primary.main, 0.04),
                    textDecoration: "none",
                  },
                  "&:visited": {
                    color: colors.primary.main,
                  },
                  ...(loading && {
                    opacity: 0.6,
                    pointerEvents: "none",
                  }),
                }}
                onClick={loading ? (e: any) => e.preventDefault() : undefined}
              >
                <PersonAdd sx={{ fontSize: 16 }} />
                Registrar
              </Box>
              {/* <Button
                variant="outlined"
                size="medium"
                onClick={handleRegistrar}
                disabled={loading}
                sx={{
                  flex: 1,
                  height: 38,
                  borderColor: colors.primary.main,
                  color: colors.primary.main,
                  fontWeight: "bold",
                  fontSize: "0.85rem",
                  "&:hover": {
                    borderColor: colors.primary.dark,
                    color: colors.primary.dark,
                    backgroundColor: withOpacity(colors.primary.main, 0.04),
                  },
                }}
                startIcon={<PersonAdd sx={{ fontSize: 16 }} />}
              >
                Registrar
              </Button> */}
            </Box>
          </Box>

          {/* Footer */}
          <Box sx={{ textAlign: "center", marginTop: 1.5 }}>
            {/* <Typography
              variant="caption"
              color={colors.text.secondary}
              sx={{ fontSize: "0.7rem" }}
            >
              © 2025 Sistema de Registro de Pessoas
            </Typography> */}
          </Box>
        </CardContent>
      </Card>
    </Box>
  );
};

export default GerenciarLogin;
