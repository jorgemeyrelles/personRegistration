import React, { useState } from "react";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Button,
  Box,
  Alert,
  CircularProgress,
  Typography,
  IconButton,
  Stack,
} from "@mui/material";
import { Close as CloseIcon } from "@mui/icons-material";
import registroPessoaService from "../service/registroPessoas";
import { useAuth } from "../hooks/useAuth";
import type {
  RegistroPessoaFormData,
  RegistroPessoaRequest,
  RegistroPessoaResponse,
} from "../types/registroPessoasTypes";

interface CriarRegistroPessoaModalProps {
  open: boolean;
  onClose: () => void;
  onSuccess?: (pessoa: RegistroPessoaResponse) => void;
  token?: string;
  usuarioId?: string; // ID do usuário para associar ao registro
  pessoaParaEditar?: RegistroPessoaResponse | null; // Pessoa para editar (se for edição)
}

const CriarRegistroPessoaModal: React.FC<CriarRegistroPessoaModalProps> = ({
  open,
  onClose,
  onSuccess,
  token,
  usuarioId = "",
  pessoaParaEditar = null,
}) => {
  const { usuario } = useAuth(); // Hook para obter dados do usuário logado
  const isEditing = !!pessoaParaEditar;

  // Usar o ID do usuário logado ou o prop usuarioId como fallback
  const userIdToUse = usuario?.id || usuarioId;
  const [formData, setFormData] = useState<RegistroPessoaFormData>({
    nome: "",
    telefone: "",
    cpf: "",
    numero: "",
    complemento: "",
    cep: "",
    bairro: "",
    nomeMunicipio: "",
    nomeEstado: "",
    latitude: "",
    longitude: "",
    usuarioId: userIdToUse?.toString() || "",
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string>("");
  const [success, setSuccess] = useState<string>("");
  const [cepLoading, setCepLoading] = useState(false);

  // Preencher formulário quando for edição
  React.useEffect(() => {
    if (isEditing && pessoaParaEditar) {
      setFormData({
        nome: pessoaParaEditar.nome || "",
        telefone: pessoaParaEditar.telefone || "",
        cpf: pessoaParaEditar.cpf || "",
        numero: pessoaParaEditar.numero || "",
        complemento: pessoaParaEditar.complemento || "",
        cep: pessoaParaEditar.cep || "",
        bairro: pessoaParaEditar.bairro || "",
        nomeMunicipio: pessoaParaEditar.nomeMunicipio || "",
        nomeEstado: pessoaParaEditar.nomeEstado || "",
        latitude: pessoaParaEditar.latitude?.toString() || "",
        longitude: pessoaParaEditar.longitude?.toString() || "",
        usuarioId: userIdToUse?.toString() || "",
      });
    } else {
      // Limpar formulário quando for criação
      setFormData({
        nome: "",
        telefone: "",
        cpf: "",
        numero: "",
        complemento: "",
        cep: "",
        bairro: "",
        nomeMunicipio: "",
        nomeEstado: "",
        latitude: "",
        longitude: "",
        usuarioId: userIdToUse?.toString() || "",
      });
    }
  }, [isEditing, pessoaParaEditar, userIdToUse]);

  const handleInputChange =
    (field: keyof RegistroPessoaFormData) =>
    (event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
      setFormData((prev) => ({
        ...prev,
        [field]: event.target.value,
      }));
      // Limpar erros quando usuário começar a digitar
      if (error) setError("");
    };

  const formatCPF = (value: string) => {
    // Remove tudo que não é dígito
    const numbers = value.replace(/\D/g, "");

    // Aplica a máscara de CPF
    return numbers
      .slice(0, 11)
      .replace(/(\d{3})(\d)/, "$1.$2")
      .replace(/(\d{3})(\d)/, "$1.$2")
      .replace(/(\d{3})(\d{1,2})/, "$1-$2");
  };

  const formatTelefone = (value: string) => {
    // Remove tudo que não é dígito
    const numbers = value.replace(/\D/g, "");

    // Aplica a máscara de telefone
    if (numbers.length <= 10) {
      return numbers
        .slice(0, 10)
        .replace(/(\d{2})(\d)/, "($1) $2")
        .replace(/(\d{4})(\d)/, "$1-$2");
    } else {
      return numbers
        .slice(0, 11)
        .replace(/(\d{2})(\d)/, "($1) $2")
        .replace(/(\d{5})(\d)/, "$1-$2");
    }
  };

  const handleCPFChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const formatted = formatCPF(event.target.value);
    setFormData((prev) => ({ ...prev, cpf: formatted }));
    if (error) setError("");
  };

  const handleTelefoneChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const formatted = formatTelefone(event.target.value);
    setFormData((prev) => ({ ...prev, telefone: formatted }));
    if (error) setError("");
  };

  // Função para buscar endereço por CEP usando ViaCEP
  const buscarEnderecoPorCep = async (cep: string) => {
    const cepLimpo = cep.replace(/\D/g, "");

    if (cepLimpo.length !== 8) {
      return;
    }

    setCepLoading(true);
    try {
      // Buscar dados do CEP via ViaCEP
      const viaCepResponse = await fetch(
        `https://viacep.com.br/ws/${cepLimpo}/json/`
      );
      const viaCepData = await viaCepResponse.json();

      if (viaCepData.erro) {
        setError("CEP não encontrado");
        return;
      }

      // Buscar coordenadas usando Nominatim (OpenStreetMap)
      const endereco = `${viaCepData.logradouro}, ${viaCepData.bairro}, ${viaCepData.localidade}, ${viaCepData.uf}, Brazil`;
      const nominatimResponse = await fetch(
        `https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(
          endereco
        )}&limit=1`
      );
      const nominatimData = await nominatimResponse.json();

      let latitude = "";
      let longitude = "";

      if (nominatimData.length > 0) {
        latitude = nominatimData[0].lat;
        longitude = nominatimData[0].lon;
      }

      // Atualizar os campos do formulário
      setFormData((prev) => ({
        ...prev,
        bairro: viaCepData.bairro || "",
        nomeMunicipio: viaCepData.localidade || "",
        nomeEstado: viaCepData.uf || "",
        latitude: latitude,
        longitude: longitude,
      }));

      setSuccess("Endereço encontrado e coordenadas obtidas!");

      // Limpar mensagem de sucesso após 3 segundos
      setTimeout(() => {
        setSuccess("");
      }, 3000);
    } catch (error) {
      console.error("Erro ao buscar endereço:", error);
      setError(
        "Erro ao buscar endereço. Verifique sua conexão e tente novamente."
      );
    } finally {
      setCepLoading(false);
    }
  };

  const formatCEP = (value: string) => {
    // Remove tudo que não é dígito
    const numbers = value.replace(/\D/g, "");

    // Aplica a máscara de CEP (00000-000)
    return numbers.slice(0, 8).replace(/(\d{5})(\d)/, "$1-$2");
  };

  const handleCEPChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const formatted = formatCEP(event.target.value);
    setFormData((prev) => ({ ...prev, cep: formatted }));

    // Buscar endereço automaticamente quando CEP estiver completo
    const cepNumeros = formatted.replace(/\D/g, "");
    if (cepNumeros.length === 8) {
      buscarEnderecoPorCep(formatted);
    }

    if (error) setError("");
    if (success) setSuccess("");
  };

  const validateForm = (): boolean => {
    if (!formData.nome.trim()) {
      setError("Nome é obrigatório");
      return false;
    }

    if (!formData.cpf.trim()) {
      setError("CPF é obrigatório");
      return false;
    }

    // Validação básica de CPF (apenas formato)
    const cpfNumbers = formData.cpf.replace(/\D/g, "");
    if (cpfNumbers.length !== 11) {
      setError("CPF deve ter 11 dígitos");
      return false;
    }

    if (!formData.telefone.trim()) {
      setError("Telefone é obrigatório");
      return false;
    }

    if (!formData.cep.trim()) {
      setError("CEP é obrigatório");
      return false;
    }

    if (!formData.bairro.trim()) {
      setError("Bairro é obrigatório");
      return false;
    }

    if (!formData.nomeMunicipio.trim()) {
      setError("Nome do município é obrigatório");
      return false;
    }

    if (!formData.nomeEstado.trim()) {
      setError("Nome do estado é obrigatório");
      return false;
    }

    return true;
  };

  const handleSubmit = async () => {
    if (!validateForm()) return;

    setLoading(true);
    setError("");
    setSuccess("");

    try {
      // Preparar dados para envio, garantindo que usuarioId não seja undefined
      const dadosParaEnvio: RegistroPessoaRequest = {
        nome: formData.nome,
        telefone: formData.telefone,
        cpf: formData.cpf.replace(/\D/g, ""),
        cep: formData.cep,
        bairro: formData.bairro,
        nomeMunicipio: formData.nomeMunicipio,
        nomeEstado: formData.nomeEstado,
        usuarioId: userIdToUse?.toString() || "", // Usar ID do usuário logado
        // Campos opcionais - apenas incluir se tiverem valor
        ...(formData.numero &&
          formData.numero.trim() && { numero: formData.numero }),
        ...(formData.complemento &&
          formData.complemento.trim() && { complemento: formData.complemento }),
        ...(formData.latitude &&
          formData.latitude.trim() && { latitude: formData.latitude }),
        ...(formData.longitude &&
          formData.longitude.trim() && { longitude: formData.longitude }),
      };

      let pessoaResult: RegistroPessoaResponse;

      if (isEditing && pessoaParaEditar) {
        // Atualizar o registro existente
        pessoaResult = await registroPessoaService.atualizarRegistroPessoa(
          pessoaParaEditar.id,
          dadosParaEnvio,
          token
        );
        setSuccess("Registro de pessoa atualizado com sucesso!");
      } else {
        // Criar novo registro
        pessoaResult = await registroPessoaService.criarRegistroPessoa(
          dadosParaEnvio,
          token
        );
        setSuccess("Registro de pessoa criado com sucesso!");
      }

      // Chamar callback de sucesso se fornecido
      if (onSuccess) {
        onSuccess(pessoaResult);
      }

      // Fechar modal após 1.5 segundos
      setTimeout(() => {
        handleClose();
      }, 1500);
    } catch (error: any) {
      console.error(
        `Erro ao ${isEditing ? "atualizar" : "criar"} registro de pessoa:`,
        error
      );
      setError(
        error.message ||
          `Erro ao ${isEditing ? "atualizar" : "criar"} registro de pessoa`
      );
    } finally {
      setLoading(false);
    }
  };

  const handleClose = () => {
    setFormData({
      nome: "",
      telefone: "",
      cpf: "",
      numero: "",
      complemento: "",
      cep: "",
      bairro: "",
      nomeMunicipio: "",
      nomeEstado: "",
      latitude: "",
      longitude: "",
      usuarioId: userIdToUse?.toString() || "",
    });
    setError("");
    setSuccess("");
    onClose();
  };

  return (
    <Dialog
      open={open}
      onClose={handleClose}
      maxWidth="md"
      fullWidth
      PaperProps={{
        sx: { borderRadius: 2 },
      }}
    >
      <DialogTitle>
        <Box display="flex" justifyContent="space-between" alignItems="center">
          <Typography variant="h6" component="div">
            {isEditing
              ? "Editar Registro de Pessoa"
              : "Criar Novo Registro de Pessoa"}
          </Typography>
          <IconButton
            aria-label="close"
            onClick={handleClose}
            sx={{ color: "grey.500" }}
          >
            <CloseIcon />
          </IconButton>
        </Box>
      </DialogTitle>

      <DialogContent dividers>
        <Box sx={{ mt: 1 }}>
          {error && (
            <Alert severity="error" sx={{ mb: 2 }}>
              {error}
            </Alert>
          )}

          {success && (
            <Alert severity="success" sx={{ mb: 2 }}>
              {success}
            </Alert>
          )}

          <Stack spacing={2}>
            {/* Primeira linha: Nome, CPF e Telefone */}
            <Box
              sx={{
                display: "grid",
                gridTemplateColumns: {
                  xs: "1fr", // Mobile: 1 coluna
                  sm: "1fr 1fr", // Tablet: 2 colunas
                  md: "1fr 1fr 1fr", // Desktop: 3 colunas
                },
                gap: 2,
              }}
            >
              <TextField
                fullWidth
                label="Nome Completo *"
                value={formData.nome}
                onChange={handleInputChange("nome")}
                disabled={loading}
                variant="outlined"
                size="small"
              />
              <TextField
                fullWidth
                label="CPF *"
                value={formData.cpf}
                onChange={handleCPFChange}
                disabled={loading}
                variant="outlined"
                placeholder="000.000.000-00"
                inputProps={{ maxLength: 14 }}
                size="small"
              />
              <TextField
                fullWidth
                label="Telefone *"
                value={formData.telefone}
                onChange={handleTelefoneChange}
                disabled={loading}
                variant="outlined"
                placeholder="(11) 99999-9999"
                inputProps={{ maxLength: 15 }}
                size="small"
              />
            </Box>

            {/* Segunda linha: CEP, Número e Complemento */}
            <Box
              sx={{
                display: "grid",
                gridTemplateColumns: {
                  xs: "1fr", // Mobile: 1 coluna
                  sm: "1fr 1fr", // Tablet: 2 colunas
                  md: "1fr 1fr 1fr", // Desktop: 3 colunas
                },
                gap: 2,
              }}
            >
              <TextField
                fullWidth
                label="CEP *"
                value={formData.cep}
                onChange={handleCEPChange}
                disabled={loading}
                variant="outlined"
                placeholder="00000-000"
                inputProps={{ maxLength: 9 }}
                size="small"
                InputProps={{
                  endAdornment: cepLoading ? (
                    <CircularProgress size={16} />
                  ) : null,
                }}
                helperText={
                  cepLoading
                    ? "Buscando..."
                    : "Digite para buscar automaticamente"
                }
              />
              <TextField
                fullWidth
                label="Número"
                value={formData.numero}
                onChange={handleInputChange("numero")}
                disabled={loading}
                variant="outlined"
                size="small"
              />
              <TextField
                fullWidth
                label="Complemento"
                value={formData.complemento}
                onChange={handleInputChange("complemento")}
                disabled={loading}
                variant="outlined"
                size="small"
              />
            </Box>

            {/* Terceira linha: Bairro, Município e Estado */}
            <Box
              sx={{
                display: "grid",
                gridTemplateColumns: {
                  xs: "1fr", // Mobile: 1 coluna
                  sm: "1fr 1fr", // Tablet: 2 colunas
                  md: "1fr 1fr 1fr", // Desktop: 3 colunas
                },
                gap: 2,
              }}
            >
              <TextField
                fullWidth
                label="Bairro *"
                value={formData.bairro}
                onChange={handleInputChange("bairro")}
                disabled={true}
                variant="outlined"
                helperText="Preenchido via CEP"
                size="small"
              />
              <TextField
                fullWidth
                label="Município *"
                value={formData.nomeMunicipio}
                onChange={handleInputChange("nomeMunicipio")}
                disabled={true}
                variant="outlined"
                helperText="Preenchido via CEP"
                size="small"
              />
              <TextField
                fullWidth
                label="Estado *"
                value={formData.nomeEstado}
                onChange={handleInputChange("nomeEstado")}
                disabled={true}
                variant="outlined"
                helperText="Preenchido via CEP"
                size="small"
              />
            </Box>
          </Stack>
        </Box>
      </DialogContent>

      <DialogActions sx={{ p: 2 }}>
        <Button onClick={handleClose} disabled={loading} variant="outlined">
          Cancelar
        </Button>
        <Button
          onClick={handleSubmit}
          disabled={loading}
          variant="contained"
          startIcon={loading ? <CircularProgress size={20} /> : null}
        >
          {loading
            ? isEditing
              ? "Atualizando..."
              : "Criando..."
            : isEditing
            ? "Atualizar Registro"
            : "Criar Registro"}
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default CriarRegistroPessoaModal;
