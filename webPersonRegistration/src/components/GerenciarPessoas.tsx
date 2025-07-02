import React, { useState, useEffect } from "react";
import {
  Box,
  Button,
  Card,
  CardContent,
  CardActions,
  Typography,
  Grid,
  IconButton,
  Chip,
  Stack,
  Avatar,
  Alert,
  Container,
  TextField,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
} from "@mui/material";
import {
  Add as AddIcon,
  Delete as DeleteIcon,
  Edit as EditIcon,
  Person as PersonIcon,
  Phone as PhoneIcon,
  LocationOn as LocationIcon,
  Search as SearchIcon,
  FilterList as FilterIcon,
} from "@mui/icons-material";
import CriarRegistroPessoaModal from "../components/CriarRegistroPessoaModal";
import DeletarRegistroPessoaModal from "../components/DeletarRegistroPessoaModal";
import registroPessoaService from "../service/registroPessoas";
import type { RegistroPessoaResponse } from "../types/registroPessoasTypes";

const GerenciarPessoas: React.FC = () => {
  const [pessoas, setPessoas] = useState<RegistroPessoaResponse[]>([]);
  const [pessoasFiltradas, setPessoasFiltradas] = useState<
    RegistroPessoaResponse[]
  >([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string>("");

  // Estados dos filtros
  const [filtroNome, setFiltroNome] = useState("");
  const [filtroEstado, setFiltroEstado] = useState("");

  // Estados dos modais
  const [modalCriarOpen, setModalCriarOpen] = useState(false);
  const [modalEditarOpen, setModalEditarOpen] = useState(false);
  const [modalDeletarOpen, setModalDeletarOpen] = useState(false);
  const [pessoaSelecionada, setPessoaSelecionada] =
    useState<RegistroPessoaResponse | null>(null);

  // Carregar lista de pessoas
  const carregarPessoas = async () => {
    setLoading(true);
    setError("");
    try {
      const listaPessoas =
        await registroPessoaService.buscarTodosRegistrosPessoas();
      setPessoas(listaPessoas);
      setPessoasFiltradas(listaPessoas);
    } catch (error: any) {
      console.error("Erro ao carregar pessoas:", error);
      setError("Erro ao carregar lista de pessoas");
    } finally {
      setLoading(false);
    }
  };

  // Aplicar filtros
  const aplicarFiltros = () => {
    let resultado = [...pessoas];

    // Filtrar por nome
    if (filtroNome.trim()) {
      resultado = resultado.filter((pessoa) =>
        pessoa.nome.toLowerCase().includes(filtroNome.toLowerCase())
      );
    }

    // Filtrar por estado
    if (filtroEstado) {
      resultado = resultado.filter(
        (pessoa) =>
          pessoa.nomeEstado.toUpperCase() === filtroEstado.toUpperCase()
      );
    }

    setPessoasFiltradas(resultado);
  };

  // Obter lista de estados únicos
  const obterEstadosUnicos = () => {
    const estados = pessoas
      .map((pessoa) => pessoa.nomeEstado.toUpperCase())
      .filter(Boolean)
      .filter((estado, index, array) => array.indexOf(estado) === index)
      .sort();
    return estados;
  };

  // Aplicar filtros quando os valores mudarem
  useEffect(() => {
    aplicarFiltros();
  }, [filtroNome, filtroEstado, pessoas]);

  // Carregar dados na inicialização
  useEffect(() => {
    carregarPessoas();
  }, []);

  // Funções do modal de criar
  const handleOpenCriarModal = () => {
    setModalCriarOpen(true);
  };

  const handleCloseCriarModal = () => {
    setModalCriarOpen(false);
  };

  const handleCriarSuccess = (novaPessoa: RegistroPessoaResponse) => {
    console.log("Nova pessoa criada:", novaPessoa);
    // Adicionar à lista local
    setPessoas((prev) => [...prev, novaPessoa]);
    // A lista filtrada será atualizada automaticamente pelo useEffect
  };

  // Funções do modal de editar
  const handleOpenEditarModal = (pessoa: RegistroPessoaResponse) => {
    setPessoaSelecionada(pessoa);
    setModalEditarOpen(true);
  };

  const handleCloseEditarModal = () => {
    setModalEditarOpen(false);
    setPessoaSelecionada(null);
  };

  const handleEditarSuccess = (pessoaAtualizada: RegistroPessoaResponse) => {
    console.log("Pessoa atualizada:", pessoaAtualizada);
    // Atualizar na lista local
    setPessoas((prev) =>
      prev.map((p) => (p.id === pessoaAtualizada.id ? pessoaAtualizada : p))
    );
    // A lista filtrada será atualizada automaticamente pelo useEffect
  };

  // Funções do modal de deletar
  const handleOpenDeletarModal = (pessoa: RegistroPessoaResponse) => {
    setPessoaSelecionada(pessoa);
    setModalDeletarOpen(true);
  };

  const handleCloseDeletarModal = () => {
    setModalDeletarOpen(false);
    setPessoaSelecionada(null);
  };

  const handleDeletarSuccess = () => {
    console.log("Pessoa deletada com sucesso!");
    // Remover da lista local
    if (pessoaSelecionada) {
      setPessoas((prev) => prev.filter((p) => p.id !== pessoaSelecionada.id));
    }
    // A lista filtrada será atualizada automaticamente pelo useEffect
  };

  const formatarTelefone = (telefone: string | undefined) => {
    if (!telefone) return "Não informado";
    return telefone;
  };

  const formatarEndereco = (pessoa: RegistroPessoaResponse) => {
    const enderecoParts = [
      pessoa.bairro,
      pessoa.nomeMunicipio,
      pessoa.nomeEstado,
    ].filter(Boolean);

    return enderecoParts.length > 0
      ? enderecoParts.join(", ")
      : "Endereço não informado";
  };

  return (
    <Container
      maxWidth={false}
      disableGutters
      sx={{
        height: "80%",
        display: "flex",
        flexDirection: "column",
        overflow: "hidden",
        padding: 2,
      }}
    >
      {/* Cabeçalho fixo */}
      <Box sx={{ mb: 3, flexShrink: 0 }}>
        <Stack
          direction="row"
          justifyContent="space-between"
          alignItems="flex-start"
          spacing={2}
        >
          <Box>
            <Typography color="text.primary" variant="h4" component="h1" gutterBottom>
              Gerenciar Pessoas
            </Typography>
            <Typography variant="body1" color="text.secondary">
              {pessoasFiltradas.length} de {pessoas.length} pessoa(s)
              encontrada(s)
            </Typography>
          </Box>

          {/* Área de filtros e botão */}
          <Stack direction="row" spacing={2} alignItems="flex-end">
            {/* Filtro por nome */}
            <TextField
              label="Buscar por nome"
              variant="outlined"
              size="small"
              value={filtroNome}
              onChange={(e) => setFiltroNome(e.target.value)}
              InputProps={{
                startAdornment: (
                  <SearchIcon sx={{ mr: 1, color: "text.secondary" }} />
                ),
              }}
              sx={{ minWidth: 200 }}
            />

            {/* Filtro por estado */}
            <FormControl size="small" sx={{ minWidth: 150 }}>
              <InputLabel>Estado</InputLabel>
              <Select
                value={filtroEstado}
                onChange={(e) => setFiltroEstado(e.target.value)}
                label="Estado"
                startAdornment={
                  <FilterIcon sx={{ mr: 1, color: "text.secondary" }} />
                }
              >
                <MenuItem value="">
                  <em>Todos os estados</em>
                </MenuItem>
                {obterEstadosUnicos().map((estado) => (
                  <MenuItem key={estado} value={estado}>
                    {estado}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>

            {/* Botão Nova Pessoa */}
            <Button
              variant="contained"
              startIcon={<AddIcon />}
              onClick={handleOpenCriarModal}
              size="large"
            >
              Nova Pessoa
            </Button>
          </Stack>
        </Stack>
      </Box>

      {/* Área de conteúdo com scroll */}
      <Box
        sx={{
          flexGrow: 1,
          overflow: "auto",
          display: "flex",
          flexDirection: "column",
        }}
      >
        {/* Alertas */}
        {error && (
          <Alert severity="error" sx={{ mb: 3 }}>
            {error}
          </Alert>
        )}

        {loading && (
          <Alert severity="info" sx={{ mb: 3 }}>
            Carregando lista de pessoas...
          </Alert>
        )}

        {/* Lista de pessoas */}
        <Grid container spacing={2}>
          {pessoasFiltradas.map((pessoa) => (
            <Grid size={{ xs: 12, sm: 6, md: 4, lg: 3 }} key={pessoa.id}>
              <Card
                sx={{
                  height: "100%",
                  display: "flex",
                  flexDirection: "column",
                  borderRadius: 3,
                  boxShadow: 2,
                  "&:hover": {
                    boxShadow: 4,
                    transform: "translateY(-2px)",
                    transition: "all 0.2s ease-in-out",
                  },
                }}
              >
                <CardContent sx={{ flexGrow: 1, p: 2 }}>
                  {/* Nome e Avatar */}
                  <Box sx={{ display: "flex", alignItems: "center", mb: 1.5 }}>
                    <Avatar
                      sx={{
                        bgcolor: "primary.main",
                        mr: 1.5,
                        width: 36,
                        height: 36,
                      }}
                    >
                      <PersonIcon fontSize="small" />
                    </Avatar>
                    <Box sx={{ minWidth: 0, flex: 1 }}>
                      <Typography
                        variant="subtitle1"
                        component="h2"
                        noWrap
                        sx={{ fontWeight: 600 }}
                      >
                        {pessoa.nome}
                      </Typography>
                      <Chip
                        label={pessoa.cpf}
                        size="small"
                        variant="outlined"
                        color="primary"
                        sx={{ fontSize: "0.7rem", height: 20 }}
                      />
                    </Box>
                  </Box>

                  {/* Informações de contato */}
                  <Stack spacing={0.5}>
                    <Box sx={{ display: "flex", alignItems: "center" }}>
                      <PhoneIcon
                        fontSize="small"
                        sx={{ mr: 1, color: "text.secondary", fontSize: 16 }}
                      />
                      <Typography
                        variant="body2"
                        color="text.secondary"
                        sx={{ fontSize: "0.8rem" }}
                      >
                        {formatarTelefone(pessoa.telefone)}
                      </Typography>
                    </Box>

                    <Box sx={{ display: "flex", alignItems: "center" }}>
                      <LocationIcon
                        fontSize="small"
                        sx={{ mr: 1, color: "text.secondary", fontSize: 16 }}
                      />
                      <Typography
                        variant="body2"
                        color="text.secondary"
                        noWrap
                        sx={{ fontSize: "0.8rem" }}
                      >
                        {formatarEndereco(pessoa)}
                      </Typography>
                    </Box>

                    {pessoa.cep && (
                      <Typography
                        variant="body2"
                        color="text.secondary"
                        sx={{ fontSize: "0.8rem" }}
                      >
                        <strong>CEP:</strong> {pessoa.cep}
                      </Typography>
                    )}
                  </Stack>

                  {/* Informações do usuário associado */}
                  {/* {pessoa.usuario && (
                    <Box
                      sx={{ mt: 2, p: 1, bgcolor: "grey.50", borderRadius: 1 }}
                    >
                      <Typography variant="caption" color="text.secondary">
                        <strong>Usuário:</strong> {pessoa.usuario.nome}
                      </Typography>
                      <br />
                      <Typography variant="caption" color="text.secondary">
                        <strong>Perfil:</strong> {pessoa.usuario.perfil.nome}
                      </Typography>
                    </Box>
                  )} */}
                </CardContent>

                <CardActions sx={{ justifyContent: "flex-end", p: 1, pt: 0 }}>
                  <IconButton
                    color="primary"
                    onClick={() => handleOpenEditarModal(pessoa)}
                    aria-label={`Editar ${pessoa.nome}`}
                    title={`Editar ${pessoa.nome}`}
                    size="small"
                  >
                    <EditIcon fontSize="small" />
                  </IconButton>
                  <IconButton
                    color="error"
                    onClick={() => handleOpenDeletarModal(pessoa)}
                    aria-label={`Deletar ${pessoa.nome}`}
                    title={`Deletar ${pessoa.nome}`}
                    size="small"
                  >
                    <DeleteIcon fontSize="small" />
                  </IconButton>
                </CardActions>
              </Card>
            </Grid>
          ))}
        </Grid>

        {/* Mensagem quando não há pessoas */}
        {!loading && pessoasFiltradas.length === 0 && pessoas.length > 0 && (
          <Box
            sx={{
              textAlign: "center",
              py: 8,
              px: 3,
              bgcolor: "grey.50",
              borderRadius: 2,
              mt: 3,
            }}
          >
            <SearchIcon sx={{ fontSize: 64, color: "grey.400", mb: 2 }} />
            <Typography variant="h6" color="text.secondary" gutterBottom>
              Nenhuma pessoa encontrada
            </Typography>
            <Typography variant="body2" color="text.secondary" sx={{ mb: 3 }}>
              Tente ajustar os filtros ou limpar a busca para ver mais
              resultados.
            </Typography>
            <Button
              variant="outlined"
              onClick={() => {
                setFiltroNome("");
                setFiltroEstado("");
              }}
            >
              Limpar Filtros
            </Button>
          </Box>
        )}

        {/* Mensagem quando não há pessoas cadastradas */}
        {!loading && pessoas.length === 0 && (
          <Box
            sx={{
              textAlign: "center",
              py: 8,
              px: 3,
              bgcolor: "grey.50",
              borderRadius: 2,
              mt: 3,
            }}
          >
            <PersonIcon sx={{ fontSize: 64, color: "grey.400", mb: 2 }} />
            <Typography variant="h6" color="text.secondary" gutterBottom>
              Nenhuma pessoa cadastrada
            </Typography>
            <Typography variant="body2" color="text.secondary" sx={{ mb: 3 }}>
              Clique no botão "Nova Pessoa" para cadastrar a primeira pessoa.
            </Typography>
            <Button
              variant="contained"
              startIcon={<AddIcon />}
              onClick={handleOpenCriarModal}
            >
              Cadastrar Primeira Pessoa
            </Button>
          </Box>
        )}
      </Box>

      {/* Modal de Criar Pessoa */}
      <CriarRegistroPessoaModal
        open={modalCriarOpen}
        onClose={handleCloseCriarModal}
        onSuccess={handleCriarSuccess}
        usuarioId="user-example-123" // Em um caso real, pegar do contexto/auth
      />

      {/* Modal de Editar Pessoa */}
      <CriarRegistroPessoaModal
        open={modalEditarOpen}
        onClose={handleCloseEditarModal}
        onSuccess={handleEditarSuccess}
        usuarioId="user-example-123" // Em um caso real, pegar do contexto/auth
        pessoaParaEditar={pessoaSelecionada}
      />

      {/* Modal de Deletar Pessoa */}
      <DeletarRegistroPessoaModal
        open={modalDeletarOpen}
        onClose={handleCloseDeletarModal}
        onSuccess={handleDeletarSuccess}
        pessoa={pessoaSelecionada}
      />
    </Container>
  );
};

export default GerenciarPessoas;
