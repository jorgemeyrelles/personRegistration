import React, { useState, useEffect } from "react";
import {
  Box,
  Typography,
  Container,
  Chip,
  Stack,
  Avatar,
  Alert,
} from "@mui/material";
import {
  DataGrid,
  type GridColDef,
  type GridRenderCellParams,
} from "@mui/x-data-grid";
import {
  Timeline as TimelineIcon,
  Computer as ComputerIcon,
  Message as MessageIcon,
  Error as ErrorIcon,
  CheckCircle as CheckCircleIcon,
  Warning as WarningIcon,
  Info as InfoIcon,
} from "@mui/icons-material";
import logsService from "../service/logs";
import type { LogOperacaoResponse, LogMensageriaResponse } from "../types/logs";
import { colors } from "../utils/colors";

// Tipo unificado para logs
interface LogUnificado {
  id: string;
  dataHora: string;
  operacao: string;
  status: string;
  descricao: string;
  tipo: "operacao" | "mensageria";
}

const GerenciarLogs: React.FC = () => {
  const [logs, setLogs] = useState<LogUnificado[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string>("");

  // Carregar logs unificados
  const carregarLogs = async () => {
    setLoading(true);
    setError("");
    try {
      const [logsOperacoes, logsMensageria] = await Promise.all([
        logsService.buscarTodosLogsOperacoes(),
        logsService.buscarTodosLogsMensageria(),
      ]);

      // Unificar os logs adicionando o campo tipo
      const logsUnificados: LogUnificado[] = [
        ...logsOperacoes.map((log: LogOperacaoResponse) => ({
          ...log,
          tipo: "operacao" as const,
        })),
        ...logsMensageria.map((log: LogMensageriaResponse) => ({
          ...log,
          tipo: "mensageria" as const,
        })),
      ];

      // Ordenar por data/hora mais recente primeiro
      logsUnificados.sort(
        (a, b) =>
          new Date(b.dataHora).getTime() - new Date(a.dataHora).getTime()
      );

      setLogs(logsUnificados);
    } catch (error: any) {
      console.error("Erro ao carregar logs:", error);
      setError("Erro ao carregar logs");
    } finally {
      setLoading(false);
    }
  };

  // Carregar dados na inicialização
  useEffect(() => {
    carregarLogs();
  }, []);

  const formatarDataHora = (dataHora: string) => {
    return new Date(dataHora).toLocaleString("pt-BR");
  };

  const getStatusColor = (status: string) => {
    switch (status.toLowerCase()) {
      case "sucesso":
        return "success";
      case "erro":
        return "error";
      case "warning":
      case "aviso":
        return "warning";
      case "info":
      case "informação":
        return "info";
      default:
        return "default";
    }
  };

  const getStatusIcon = (status: string) => {
    switch (status.toLowerCase()) {
      case "sucesso":
        return <CheckCircleIcon fontSize="small" />;
      case "erro":
        return <ErrorIcon fontSize="small" />;
      case "warning":
      case "aviso":
        return <WarningIcon fontSize="small" />;
      case "info":
      case "informação":
      default:
        return <InfoIcon fontSize="small" />;
    }
  };

  const getTipoIcon = (tipo: string) => {
    return tipo === "operacao" ? (
      <ComputerIcon fontSize="small" />
    ) : (
      <MessageIcon fontSize="small" />
    );
  };

  // Definir colunas do DataGrid
  const columns: GridColDef[] = [
    {
      field: "tipo",
      headerName: "Tipo",
      width: 120,
      renderCell: (params: GridRenderCellParams) => (
        <Chip
          icon={getTipoIcon(params.value)}
          label={params.value === "operacao" ? "Operação" : "Mensageria"}
          size="small"
          color={params.value === "operacao" ? "primary" : "secondary"}
          variant="outlined"
        />
      ),
    },
    {
      field: "dataHora",
      headerName: "Data/Hora",
      width: 180,
      renderCell: (params: GridRenderCellParams) =>
        formatarDataHora(params.value),
    },
    {
      field: "operacao",
      headerName: "Operação",
      width: 200,
      flex: 1,
    },
    {
      field: "status",
      headerName: "Status",
      width: 120,
      renderCell: (params: GridRenderCellParams) => (
        <Chip
          icon={getStatusIcon(params.value)}
          label={params.value}
          size="small"
          color={getStatusColor(params.value) as any}
          variant="outlined"
        />
      ),
    },
    {
      field: "descricao",
      headerName: "Descrição",
      width: 300,
      flex: 2,
    },
  ];

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
        <Stack direction="row" alignItems="center" spacing={2}>
          <Avatar sx={{ bgcolor: colors.primary.main }}>
            <TimelineIcon />
          </Avatar>
          <Box>
            <Typography
              color="text.primary"
              variant="h4"
              component="h1"
              gutterBottom
            >
              Logs do Sistema
            </Typography>
            <Typography variant="body1" color="text.secondary">
              {logs.length} logs encontrados (operações e mensageria)
            </Typography>
          </Box>
        </Stack>
      </Box>

      {/* Área de conteúdo com scroll */}
      <Box
        sx={{
          flexGrow: 1,
          overflow: "hidden",
          display: "flex",
          flexDirection: "column",
        }}
      >
        {/* Alertas */}
        {error && (
          <Alert severity="error" sx={{ mb: 2 }}>
            {error}
          </Alert>
        )}

        {/* DataGrid */}
        <Box sx={{ height: "100%", width: "100%" }}>
          <DataGrid
            showToolbar
            rows={logs}
            columns={columns}
            loading={loading}
            initialState={{
              pagination: {
                paginationModel: { pageSize: 25 },
              },
            }}
            pageSizeOptions={[10, 25, 50, 100]}
            disableRowSelectionOnClick
            sx={{
              border: 1,
              borderColor: "divider",
              "& .MuiDataGrid-cell": {
                borderColor: "divider",
              },
              "& .MuiDataGrid-columnHeaders": {
                backgroundColor: colors.grey[50],
                borderColor: "divider",
              },
            }}
            localeText={{
              noRowsLabel: "Nenhum log encontrado",
              noResultsOverlayLabel: "Nenhum resultado encontrado.",
              toolbarDensity: "Densidade",
              toolbarDensityCompact: "Compacto",
              toolbarDensityStandard: "Padrão",
              toolbarDensityComfortable: "Confortável",
              toolbarExport: "Exportar",
              toolbarExportCSV: "Baixar como CSV",
              toolbarColumns: "Colunas",
              toolbarFilters: "Filtros",
              filterPanelAddFilter: "Adicionar filtro",
              filterPanelOperator: "Operador",
              filterPanelOperatorAnd: "E",
              filterPanelOperatorOr: "Ou",
              filterPanelColumns: "Colunas",
              filterPanelInputLabel: "Valor",
              filterPanelInputPlaceholder: "Valor do filtro",
              filterOperatorContains: "contém",
              filterOperatorEquals: "igual",
              filterOperatorStartsWith: "começa com",
              filterOperatorEndsWith: "termina com",
              footerRowSelected: (count) =>
                count !== 1
                  ? `${count.toLocaleString()} linhas selecionadas`
                  : `${count.toLocaleString()} linha selecionada`,
              footerTotalRows: "Total de linhas:",
              footerTotalVisibleRows: (visibleCount, totalCount) =>
                `${visibleCount.toLocaleString()} de ${totalCount.toLocaleString()}`,
            }}
          />
        </Box>
      </Box>
    </Container>
  );
};

export default GerenciarLogs;
