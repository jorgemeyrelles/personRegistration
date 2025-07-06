import React, { useState, useEffect } from "react";
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
  Select,
  MenuItem,
  FormControl,
  InputLabel,
  FormHelperText,
  useTheme,
  useMediaQuery,
} from "@mui/material";
import { useNavigate } from "react-router-dom";
import {
  Visibility,
  VisibilityOff,
  Email,
  Lock,
  Person,
  PersonAdd,
  Security,
  AccountCircle,
} from "@mui/icons-material";
import { colors, withOpacity } from "../utils/colors";
import { criarUsuario } from "../service/usuarios";
import { buscarTodosPerfis } from "../service/perfil";
import type { Usuario } from "../types/usuario";
import type { PerfilResponse } from "../types/perfil";

const GerenciarRegistro: React.FC = () => {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down("sm"));
  const navigate = useNavigate();

  // Estados do formulário
  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [confirmarSenha, setConfirmarSenha] = useState("");
  const [perfilId, setPerfilId] = useState("");
  const [mostrarSenha, setMostrarSenha] = useState(false);
  const [mostrarConfirmarSenha, setMostrarConfirmarSenha] = useState(false);
  const [loading, setLoading] = useState(false);
  const [erro, setErro] = useState<string | null>(null);
  const [sucesso, setSucesso] = useState<string | null>(null);

  // Estados para perfis
  const [perfis, setPerfis] = useState<PerfilResponse[]>([]);
  const [loadingPerfis, setLoadingPerfis] = useState(true);

  // Validações
  const [errosValidacao, setErrosValidacao] = useState({
    nome: "",
    email: "",
    senha: "",
    confirmarSenha: "",
    perfil: "",
  });

  // Carregar perfis ao montar o componente
  useEffect(() => {
    const carregarPerfis = async () => {
      try {
        setLoadingPerfis(true);
        const todosPerfis = await buscarTodosPerfis();

        // Filtrar perfis excluindo ADMIN
        const perfisFiltrados = todosPerfis.filter(
          (perfil) => perfil.nome !== "ADMIN" && perfil.nome !== "admin"
        );

        setPerfis(perfisFiltrados);
      } catch (error) {
        console.error("Erro ao carregar perfis:", error);
        setErro("Erro ao carregar perfis disponíveis");
      } finally {
        setLoadingPerfis(false);
      }
    };

    carregarPerfis();
  }, []);

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

  // Função para validar campos
  const validarFormulario = (): boolean => {
    const novosErros = {
      nome: "",
      email: "",
      senha: "",
      confirmarSenha: "",
      perfil: "",
    };

    // Validação do nome (8-15 caracteres)
    if (!nome.trim()) {
      novosErros.nome = "Nome é obrigatório";
    } else if (nome.trim().length < 8) {
      novosErros.nome = "Nome deve ter pelo menos 8 caracteres";
    } else if (nome.trim().length > 15) {
      novosErros.nome = "Nome deve ter no máximo 15 caracteres";
    }

    // Validação do email
    if (!email.trim()) {
      novosErros.email = "Email é obrigatório";
    } else if (!validarEmail(email)) {
      novosErros.email = "Email inválido";
    }

    // Validação da senha
    if (!senha) {
      novosErros.senha = "Senha é obrigatória";
    } else if (!validarSenhaForte(senha)) {
      novosErros.senha =
        "Senha deve ter pelo menos 8 caracteres, incluindo maiúscula, minúscula, número e caractere especial";
    }

    // Validação da confirmação de senha
    if (!confirmarSenha) {
      novosErros.confirmarSenha = "Confirmação de senha é obrigatória";
    } else if (senha !== confirmarSenha) {
      novosErros.confirmarSenha = "Senhas não coincidem";
    }

    // Validação do perfil
    if (!perfilId) {
      novosErros.perfil = "Perfil é obrigatório";
    }

    setErrosValidacao(novosErros);
    return !Object.values(novosErros).some((erro) => erro !== "");
  };

  // Função para fazer registro
  const handleRegistro = async (e: React.FormEvent) => {
    e.preventDefault();
    setErro(null);
    setSucesso(null);

    if (!validarFormulario()) {
      return;
    }

    setLoading(true);

    try {
      const novoUsuario: Usuario = {
        nome: nome.trim(),
        email: email.trim(),
        senha: senha,
        perfilId: perfilId,
        // Assumindo que o backend espera o ID do perfil
        // Ajustar conforme estrutura da API
      };

      const resultado = await criarUsuario(novoUsuario);

      setSucesso("Usuário registrado com sucesso! Redirecionando...");

      // Limpar formulário
      setNome("");
      setEmail("");
      setSenha("");
      setConfirmarSenha("");
      setPerfilId("");
      setErrosValidacao({
        nome: "",
        email: "",
        senha: "",
        confirmarSenha: "",
        perfil: "",
      });

      // Redirecionar para home após sucesso
      setTimeout(() => {
        navigate("/login");
      }, 2000);
    } catch (error) {
      console.error("❌ Erro no registro:", error);
      const mensagemErro =
        error instanceof Error
          ? error.message
          : "Erro ao registrar usuário. Tente novamente.";
      setErro(mensagemErro);
    } finally {
      setLoading(false);
    }
  };

  // Função para alternar visibilidade da senha
  const handleTogglePassword = () => {
    setMostrarSenha(!mostrarSenha);
  };

  // Função para alternar visibilidade da confirmação de senha
  const handleToggleConfirmPassword = () => {
    setMostrarConfirmarSenha(!mostrarConfirmarSenha);
  };

  // Função para voltar ao login
  const handleVoltarLogin = () => {
    navigate("/login");
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
                width: 45,
                height: 45,
                borderRadius: "50%",
                background: colors.gradients.primary,
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                margin: "0 auto 6px auto",
                boxShadow: colors.shadows.medium,
              }}
            >
              <PersonAdd sx={{ fontSize: 22, color: colors.text.onPrimary }} />
            </Box>
            <Typography
              variant="subtitle1"
              fontWeight="bold"
              color={colors.text.primary}
              sx={{ marginBottom: 0.3, fontSize: "1.1rem" }}
            >
              Criar Conta
            </Typography>
            <Typography
              variant="caption"
              color={colors.text.secondary}
              sx={{ fontSize: "0.75rem" }}
            >
              Preencha os dados para criar sua conta
            </Typography>
          </Box>

          {/* Alertas */}
          {erro && (
            <Alert
              severity="error"
              sx={{ marginBottom: 0.8, fontSize: "0.75rem", py: 0.4 }}
              onClose={() => setErro(null)}
            >
              {erro}
            </Alert>
          )}

          {sucesso && (
            <Alert
              severity="success"
              sx={{ marginBottom: 0.8, fontSize: "0.75rem", py: 0.4 }}
              onClose={() => setSucesso(null)}
            >
              {sucesso}
            </Alert>
          )}

          {/* Formulário */}
          <Box component="form" onSubmit={handleRegistro} noValidate>
            {/* Campo Nome */}
            <TextField
              fullWidth
              label="Nome (8-15 caracteres)"
              value={nome}
              onChange={(e) => setNome(e.target.value)}
              error={!!errosValidacao.nome}
              disabled={loading}
              autoComplete="name"
              autoFocus
              size="small"
              sx={{ marginBottom: 0.8 }}
              InputProps={{
                startAdornment: (
                  <InputAdornment position="start">
                    <Person sx={{ color: colors.primary.main, fontSize: 18 }} />
                  </InputAdornment>
                ),
              }}
            />

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
              size="small"
              sx={{ marginBottom: 0.8 }}
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
              autoComplete="new-password"
              size="small"
              sx={{ marginBottom: 0.8 }}
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

            {/* Campo Confirmar Senha */}
            <TextField
              fullWidth
              label="Confirmar Senha"
              type={mostrarConfirmarSenha ? "text" : "password"}
              value={confirmarSenha}
              onChange={(e) => setConfirmarSenha(e.target.value)}
              error={!!errosValidacao.confirmarSenha}
              disabled={loading}
              autoComplete="new-password"
              size="small"
              sx={{ marginBottom: 0.8 }}
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
                      onClick={handleToggleConfirmPassword}
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

            {/* Campo Perfil */}
            <FormControl
              fullWidth
              error={!!errosValidacao.perfil}
              disabled={loading || loadingPerfis}
              size="small"
              sx={{ marginBottom: 1 }}
            >
              <InputLabel>Perfil</InputLabel>
              <Select
                value={perfilId}
                onChange={(e) => setPerfilId(e.target.value)}
                label="Perfil"
                startAdornment={
                  <InputAdornment position="start">
                    <AccountCircle
                      sx={{ color: colors.primary.main, mr: 1, fontSize: 18 }}
                    />
                  </InputAdornment>
                }
              >
                {loadingPerfis ? (
                  <MenuItem disabled>
                    <CircularProgress size={16} />
                    <Typography sx={{ ml: 1, fontSize: "0.8rem" }}>
                      Carregando perfis...
                    </Typography>
                  </MenuItem>
                ) : perfis.length === 0 ? (
                  <MenuItem disabled>
                    <Typography sx={{ fontSize: "0.8rem" }}>
                      Nenhum perfil disponível
                    </Typography>
                  </MenuItem>
                ) : (
                  perfis.map((perfil) => (
                    <MenuItem key={perfil.id} value={perfil.id?.toString()}>
                      {perfil.nome}
                    </MenuItem>
                  ))
                )}
              </Select>
            </FormControl>

            {/* Dicas de senha forte - versão compacta */}
            <Box
              sx={{
                background: colors.background.default,
                padding: 1,
                borderRadius: 1,
                marginBottom: 1,
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

            {/* Botões lado a lado */}
            <Box sx={{ display: "flex", gap: 1, marginBottom: 0.5 }}>
              {/* Botão Registrar */}
              <Button
                type="submit"
                variant="contained"
                size="small"
                disabled={loading}
                sx={{
                  flex: 1,
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
                    <CircularProgress size={12} color="inherit" />
                  ) : (
                    <PersonAdd sx={{ fontSize: 14 }} />
                  )
                }
              >
                {loading ? "Registrando..." : "Registrar"}
              </Button>

              {/* Botão Voltar ao Login */}
              <Button
                variant="outlined"
                size="small"
                onClick={handleVoltarLogin}
                disabled={loading}
                sx={{
                  flex: 1,
                  height: 36,
                  borderColor: colors.primary.main,
                  color: colors.primary.main,
                  fontWeight: "bold",
                  fontSize: "0.8rem",
                  "&:hover": {
                    borderColor: colors.primary.dark,
                    color: colors.primary.dark,
                    backgroundColor: withOpacity(colors.primary.main, 0.04),
                  },
                }}
                startIcon={<AccountCircle sx={{ fontSize: 14 }} />}
              >
                Login
              </Button>
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

export default GerenciarRegistro;
