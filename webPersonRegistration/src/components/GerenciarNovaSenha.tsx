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
  IconButton,
  InputAdornment,
  Stepper,
  Step,
  StepLabel,
  useTheme,
  useMediaQuery,
} from "@mui/material";
import { useNavigate } from "react-router-dom";
import {
  Visibility,
  VisibilityOff,
  Email,
  Lock,
  LockReset,
  CheckCircle,
  Security,
} from "@mui/icons-material";
import { colors, withOpacity } from "../utils/colors";
import { buscarTodosUsuarios, atualizarSenha } from "../service/usuarios";
import type { UsuarioResponse } from "../types/usuario";

const GerenciarNovaSenha: React.FC = () => {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down("sm"));
  const navigate = useNavigate();

  // Estados do fluxo
  const [etapaAtual, setEtapaAtual] = useState(0);
  const [usuarioEncontrado, setUsuarioEncontrado] =
    useState<UsuarioResponse | null>(null);

  // Estados do formulário
  const [email, setEmail] = useState("");
  const [novaSenha, setNovaSenha] = useState("");
  const [confirmarSenha, setConfirmarSenha] = useState("");
  const [mostrarNovaSenha, setMostrarNovaSenha] = useState(false);
  const [mostrarConfirmarSenha, setMostrarConfirmarSenha] = useState(false);
  const [loading, setLoading] = useState(false);
  const [erro, setErro] = useState<string | null>(null);
  const [sucesso, setSucesso] = useState<string | null>(null);

  // Validações
  const [errosValidacao, setErrosValidacao] = useState({
    email: "",
    novaSenha: "",
    confirmarSenha: "",
  });

  // Etapas do processo
  const etapas = ["Verificar Email", "Nova Senha", "Concluído"];

  // Função para validar email
  const validarEmail = (email: string): boolean => {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(email);
  };

  // Função para validar senha forte
  const validarSenhaForte = (senha: string): boolean => {
    // Pelo menos 8 caracteres, 1 maiúscula, 1 minúscula, 1 número e 1 caractere especial
    const regex =
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    return regex.test(senha);
  };

  // Função para validar email (etapa 1)
  const validarEtapaEmail = (): boolean => {
    const novosErros = {
      email: "",
      novaSenha: "",
      confirmarSenha: "",
    };

    if (!email.trim()) {
      novosErros.email = "Email é obrigatório";
    } else if (!validarEmail(email)) {
      novosErros.email = "Email inválido";
    }

    setErrosValidacao(novosErros);
    return !novosErros.email;
  };

  // Função para validar senhas (etapa 2)
  const validarEtapaSenhas = (): boolean => {
    const novosErros = {
      email: "",
      novaSenha: "",
      confirmarSenha: "",
    };

    if (!novaSenha) {
      novosErros.novaSenha = "Nova senha é obrigatória";
    } else if (!validarSenhaForte(novaSenha)) {
      novosErros.novaSenha =
        "Senha deve ter pelo menos 8 caracteres, incluindo maiúscula, minúscula, número e caractere especial";
    }

    if (!confirmarSenha) {
      novosErros.confirmarSenha = "Confirmação de senha é obrigatória";
    } else if (novaSenha !== confirmarSenha) {
      novosErros.confirmarSenha = "Senhas não coincidem";
    }

    setErrosValidacao(novosErros);
    return !novosErros.novaSenha && !novosErros.confirmarSenha;
  };

  // Função para verificar se email existe (etapa 1)
  const handleVerificarEmail = async (e: React.FormEvent) => {
    e.preventDefault();
    setErro(null);
    setSucesso(null);

    if (!validarEtapaEmail()) {
      return;
    }

    setLoading(true);

    try {
      // Buscar todos os usuários e verificar se o email existe
      const usuarios = await buscarTodosUsuarios();
      const usuario = usuarios.find(
        (u) => u.email.toLowerCase() === email.toLowerCase()
      );

      if (!usuario) {
        setErro("Email não encontrado no sistema");
        setLoading(false);
        return;
      }

      setUsuarioEncontrado(usuario);
      setSucesso(`Email encontrado! Usuário: ${usuario.nome}`);

      // Avançar para próxima etapa após 1.5s
      setTimeout(() => {
        setEtapaAtual(1);
        setSucesso(null);
      }, 1500);
    } catch (error) {
      console.error("❌ Erro ao verificar email:", error);
      const mensagemErro =
        error instanceof Error
          ? error.message
          : "Erro ao verificar email. Tente novamente.";
      setErro(mensagemErro);
    } finally {
      setLoading(false);
    }
  };

  // Função para alterar senha (etapa 2)
  const handleAlterarSenha = async (e: React.FormEvent) => {
    e.preventDefault();
    setErro(null);
    setSucesso(null);

    if (!validarEtapaSenhas()) {
      return;
    }

    setLoading(true);

    try {
      if (!usuarioEncontrado?.email) {
        throw new Error("Email do usuário não encontrado");
      }

      console.log(usuarioEncontrado, novaSenha);

      // Chamar o serviço real de atualização de senha
      const resultado = await atualizarSenha({
        email: usuarioEncontrado.email,
        senha: novaSenha,
      });

      setSucesso(
        `Senha alterada com sucesso! Atualização em: ${new Date(
          resultado.dataHoraAtualizacao
        ).toLocaleString()}`
      );
      handleVoltarLogin();
      // Avançar para etapa final após 1.5s
      setTimeout(() => {
        setEtapaAtual(2);
        setSucesso(null);
      }, 1500);
    } catch (error) {
      console.error("❌ Erro ao alterar senha:", error);
      const mensagemErro =
        error instanceof Error
          ? error.message
          : "Erro ao alterar senha. Tente novamente.";
      setErro(mensagemErro);
    } finally {
      setLoading(false);
    }
  };

  // Função para voltar ao login
  const handleVoltarLogin = () => {
    navigate("/login");
  };

  // Função para voltar etapa
  const handleVoltarEtapa = () => {
    if (etapaAtual > 0) {
      setEtapaAtual(etapaAtual - 1);
      setErro(null);
      setSucesso(null);
    }
  };

  // Função para alternar visibilidade da nova senha
  const handleToggleNovaSenha = () => {
    setMostrarNovaSenha(!mostrarNovaSenha);
  };

  // Função para alternar visibilidade da confirmação de senha
  const handleToggleConfirmarSenha = () => {
    setMostrarConfirmarSenha(!mostrarConfirmarSenha);
  };

  // Renderizar conteúdo baseado na etapa
  const renderConteudoEtapa = () => {
    switch (etapaAtual) {
      case 0:
        return (
          <Box component="form" onSubmit={handleVerificarEmail} noValidate>
            <Typography
              variant="subtitle2"
              color={colors.text.primary}
              sx={{ mb: 1, fontSize: "0.9rem" }}
            >
              Informe seu email para recuperar a senha
            </Typography>
            <Typography
              variant="caption"
              color={colors.text.secondary}
              sx={{ mb: 2, fontSize: "0.75rem" }}
            >
              Digite o email cadastrado no sistema para continuar
            </Typography>

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
              sx={{ marginBottom: 2 }}
              InputProps={{
                startAdornment: (
                  <InputAdornment position="start">
                    <Email sx={{ color: colors.primary.main, fontSize: 18 }} />
                  </InputAdornment>
                ),
              }}
            />

            <Button
              type="submit"
              fullWidth
              variant="contained"
              size="medium"
              disabled={loading}
              sx={{
                height: 40,
                background: colors.gradients.primary,
                color: colors.text.onPrimary,
                fontWeight: "bold",
                fontSize: "0.9rem",
                marginBottom: 1.5,
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
                  <CircularProgress size={16} color="inherit" />
                ) : (
                  <LockReset sx={{ fontSize: 16 }} />
                )
              }
            >
              {loading ? "Verificando..." : "Verificar Email"}
            </Button>
          </Box>
        );

      case 1:
        return (
          <Box component="form" onSubmit={handleAlterarSenha} noValidate>
            <Typography
              variant="subtitle2"
              color={colors.text.primary}
              sx={{ mb: 0.8, fontSize: "0.9rem" }}
            >
              Definir nova senha
            </Typography>
            <Typography
              variant="caption"
              color={colors.text.secondary}
              sx={{ mb: 0.5, fontSize: "0.75rem" }}
            >
              Usuário: <strong>{usuarioEncontrado?.nome}</strong>
            </Typography>
            <Typography
              variant="caption"
              color={colors.text.secondary}
              sx={{ mb: 2, fontSize: "0.75rem" }}
            >
              Email: <strong>{usuarioEncontrado?.email}</strong>
            </Typography>

            <TextField
              fullWidth
              label="Nova Senha"
              type={mostrarNovaSenha ? "text" : "password"}
              value={novaSenha}
              onChange={(e) => setNovaSenha(e.target.value)}
              error={!!errosValidacao.novaSenha}
              disabled={loading}
              autoComplete="new-password"
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
                      onClick={handleToggleNovaSenha}
                      disabled={loading}
                      edge="end"
                      size="small"
                    >
                      {mostrarNovaSenha ? <VisibilityOff /> : <Visibility />}
                    </IconButton>
                  </InputAdornment>
                ),
              }}
            />

            <TextField
              fullWidth
              label="Confirmar Nova Senha"
              type={mostrarConfirmarSenha ? "text" : "password"}
              value={confirmarSenha}
              onChange={(e) => setConfirmarSenha(e.target.value)}
              error={!!errosValidacao.confirmarSenha}
              disabled={loading}
              autoComplete="new-password"
              size="small"
              sx={{ marginBottom: 1.5 }}
              InputProps={{
                startAdornment: (
                  <InputAdornment position="start">
                    <Security
                      sx={{ color: colors.primary.main, fontSize: 18 }}
                    />
                  </InputAdornment>
                ),
                endAdornment: (
                  <InputAdornment position="end">
                    <IconButton
                      onClick={handleToggleConfirmarSenha}
                      disabled={loading}
                      edge="end"
                      size="small"
                    >
                      {mostrarConfirmarSenha ? (
                        <VisibilityOff />
                      ) : (
                        <Visibility />
                      )}
                    </IconButton>
                  </InputAdornment>
                ),
              }}
            />

            {/* Dicas de senha forte */}
            <Box
              sx={{
                background: colors.background.default,
                padding: 1,
                borderRadius: 1,
                marginBottom: 1.5,
              }}
            >
              <Typography
                variant="caption"
                color={colors.text.secondary}
                sx={{ fontSize: "0.65rem" }}
              >
                <strong>Requisitos:</strong> 8+ chars, 1 maiúscula, 1 minúscula,
                1 número, 1 especial (@$!%*?&)
              </Typography>
            </Box>

            <Box sx={{ display: "flex", gap: 1 }}>
              <Button
                fullWidth
                variant="outlined"
                size="small"
                onClick={handleVoltarEtapa}
                disabled={loading}
                sx={{
                  height: 36,
                  borderColor: colors.grey[400],
                  color: colors.grey[600],
                  fontWeight: "bold",
                  fontSize: "0.8rem",
                  "&:hover": {
                    borderColor: colors.grey[600],
                    color: colors.grey[800],
                    backgroundColor: withOpacity(colors.grey[400], 0.04),
                  },
                }}
              >
                Voltar
              </Button>

              <Button
                type="submit"
                fullWidth
                variant="contained"
                size="small"
                disabled={loading}
                sx={{
                  height: 36,
                  background: colors.gradients.primary,
                  color: colors.text.onPrimary,
                  fontWeight: "bold",
                  fontSize: "0.8rem",
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
                    <LockReset sx={{ fontSize: 14 }} />
                  )
                }
              >
                {loading ? "Alterando..." : "Alterar Senha"}
              </Button>
            </Box>
          </Box>
        );

      case 2:
        return (
          <Box sx={{ textAlign: "center" }}>
            <Box
              sx={{
                width: 60,
                height: 60,
                borderRadius: "50%",
                background: colors.success.main,
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                margin: "0 auto 12px auto",
                boxShadow: colors.shadows.medium,
              }}
            >
              <CheckCircle
                sx={{ fontSize: 30, color: colors.text.onPrimary }}
              />
            </Box>

            <Typography
              variant="h6"
              color={colors.text.primary}
              sx={{ mb: 0.5, fontSize: "1.1rem" }}
            >
              Senha Alterada!
            </Typography>
            <Typography
              variant="caption"
              color={colors.text.secondary}
              sx={{ mb: 2, fontSize: "0.8rem" }}
            >
              Sua senha foi alterada com sucesso. Você pode fazer login com a
              nova senha.
            </Typography>

            <Button
              fullWidth
              variant="contained"
              size="medium"
              onClick={handleVoltarLogin}
              sx={{
                height: 40,
                background: colors.gradients.primary,
                color: colors.text.onPrimary,
                fontWeight: "bold",
                fontSize: "0.9rem",
                "&:hover": {
                  background: colors.primary.dark,
                  transform: "translateY(-1px)",
                  boxShadow: colors.shadows.medium,
                },
              }}
              startIcon={<CheckCircle sx={{ fontSize: 16 }} />}
            >
              Fazer Login
            </Button>
          </Box>
        );

      default:
        return null;
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
        <CardContent sx={{ padding: isMobile ? 1 : 1.8 }}>
          {/* Header */}
          <Box sx={{ textAlign: "center", marginBottom: 1.5 }}>
            <Box
              sx={{
                width: 40,
                height: 40,
                borderRadius: "50%",
                background: colors.gradients.secondary,
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                margin: "0 auto 6px auto",
                boxShadow: colors.shadows.medium,
              }}
            >
              <LockReset sx={{ fontSize: 20, color: colors.text.onPrimary }} />
            </Box>
            <Typography
              variant="subtitle2"
              fontWeight="bold"
              color={colors.text.primary}
              sx={{ marginBottom: 0.2, fontSize: "1rem" }}
            >
              Recuperar Senha
            </Typography>
            <Typography
              variant="caption"
              color={colors.text.secondary}
              sx={{ fontSize: "0.7rem" }}
            >
              Siga os passos para redefinir sua senha
            </Typography>
          </Box>

          {/* Stepper */}
          <Stepper activeStep={etapaAtual} sx={{ marginBottom: 2 }}>
            {etapas.map((label) => (
              <Step key={label}>
                <StepLabel
                  sx={{ "& .MuiStepLabel-label": { fontSize: "0.75rem" } }}
                >
                  {label}
                </StepLabel>
              </Step>
            ))}
          </Stepper>

          {/* Alertas */}
          {erro && (
            <Alert
              severity="error"
              sx={{ marginBottom: 1.5, fontSize: "0.75rem", py: 0.4 }}
              onClose={() => setErro(null)}
            >
              {erro}
            </Alert>
          )}

          {sucesso && (
            <Alert
              severity="success"
              sx={{ marginBottom: 1.5, fontSize: "0.75rem", py: 0.4 }}
              onClose={() => setSucesso(null)}
            >
              {sucesso}
            </Alert>
          )}

          {/* Conteúdo da Etapa */}
          {renderConteudoEtapa()}

          {/* Botão Voltar ao Login (apenas nas etapas 0 e 1) */}
          {etapaAtual < 2 && (
            <>
              <Box sx={{ textAlign: "center", marginTop: 1.5 }}>
                <Button
                  variant="text"
                  size="small"
                  onClick={handleVoltarLogin}
                  disabled={loading}
                  sx={{
                    color: colors.primary.main,
                    fontSize: "0.8rem",
                    "&:hover": {
                      backgroundColor: withOpacity(colors.primary.main, 0.04),
                    },
                  }}
                >
                  Voltar ao Login
                </Button>
              </Box>
            </>
          )}

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

export default GerenciarNovaSenha;
