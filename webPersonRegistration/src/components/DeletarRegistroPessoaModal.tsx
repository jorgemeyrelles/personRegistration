import React, { useState } from 'react';
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  Typography,
  Box,
  Alert,
  CircularProgress,
  IconButton,
  Avatar,
  Stack
} from '@mui/material';
import {
  Close as CloseIcon,
  Warning as WarningIcon,
  Person as PersonIcon
} from '@mui/icons-material';
import registroPessoaService from '../service/registroPessoas';
import type { RegistroPessoaResponse } from '../types/registroPessoasTypes';

interface DeletarRegistroPessoaModalProps {
  open: boolean;
  onClose: () => void;
  onSuccess?: () => void;
  pessoa: RegistroPessoaResponse | null;
  token?: string;
}

const DeletarRegistroPessoaModal: React.FC<DeletarRegistroPessoaModalProps> = ({
  open,
  onClose,
  onSuccess,
  pessoa,
  token
}) => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string>('');
  const [success, setSuccess] = useState<string>('');

  const handleDelete = async () => {
    if (!pessoa?.id) {
      setError('ID da pessoa não encontrado');
      return;
    }

    setLoading(true);
    setError('');
    setSuccess('');

    try {
      await registroPessoaService.deletarRegistroPessoa(pessoa.id, token);
      
      setSuccess('Registro deletado com sucesso!');
      
      // Chamar callback de sucesso se fornecido
      if (onSuccess) {
        onSuccess();
      }

      // Fechar modal após 1.5 segundos
      setTimeout(() => {
        handleClose();
      }, 1500);

    } catch (error: any) {
      console.error('Erro ao deletar registro de pessoa:', error);
      setError(error.message || 'Erro ao deletar registro de pessoa');
    } finally {
      setLoading(false);
    }
  };

  const handleClose = () => {
    setError('');
    setSuccess('');
    onClose();
  };

  if (!pessoa) {
    return null;
  }

  return (
    <Dialog 
      open={open} 
      onClose={handleClose}
      maxWidth="sm"
      fullWidth
      PaperProps={{
        sx: { borderRadius: 2 }
      }}
    >
      <DialogTitle>
        <Box display="flex" justifyContent="space-between" alignItems="center">
          <Box display="flex" alignItems="center" gap={1}>
            <Avatar sx={{ bgcolor: 'error.main', width: 32, height: 32 }}>
              <WarningIcon fontSize="small" />
            </Avatar>
            <Typography variant="h6" component="div">
              Confirmar Exclusão
            </Typography>
          </Box>
          <IconButton
            aria-label="close"
            onClick={handleClose}
            sx={{ color: 'grey.500' }}
            disabled={loading}
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

          {!success && (
            <>
              <Typography variant="body1" gutterBottom>
                Tem certeza que deseja excluir o registro da seguinte pessoa?
              </Typography>

              <Box 
                sx={{ 
                  mt: 2, 
                  p: 2, 
                  border: 1, 
                  borderColor: 'divider', 
                  borderRadius: 1,
                  bgcolor: 'grey.50'
                }}
              >
                <Stack spacing={1}>
                  <Box display="flex" alignItems="center" gap={1}>
                    <Avatar sx={{ bgcolor: 'primary.main', width: 24, height: 24 }}>
                      <PersonIcon fontSize="small" />
                    </Avatar>
                    <Typography variant="subtitle2" component="span">
                      {pessoa.nome}
                    </Typography>
                  </Box>
                  
                  <Typography variant="body2" color="text.secondary">
                    <strong>CPF:</strong> {pessoa.cpf}
                  </Typography>
                  
                  {pessoa.telefone && (
                    <Typography variant="body2" color="text.secondary">
                      <strong>Telefone:</strong> {pessoa.telefone}
                    </Typography>
                  )}
                  
                  <Typography variant="body2" color="text.secondary">
                    <strong>Endereço:</strong> {pessoa.bairro}, {pessoa.nomeMunicipio} - {pessoa.nomeEstado}
                  </Typography>
                  
                  {pessoa.cep && (
                    <Typography variant="body2" color="text.secondary">
                      <strong>CEP:</strong> {pessoa.cep}
                    </Typography>
                  )}
                </Stack>
              </Box>

              <Alert severity="warning" sx={{ mt: 2 }}>
                <Typography variant="body2">
                  <strong>Atenção:</strong> Esta ação não pode ser desfeita. 
                  Todos os dados desta pessoa serão permanentemente removidos do sistema.
                </Typography>
              </Alert>
            </>
          )}
        </Box>
      </DialogContent>

      <DialogActions sx={{ p: 2 }}>
        <Button 
          onClick={handleClose} 
          disabled={loading}
          variant="outlined"
          color="inherit"
        >
          Cancelar
        </Button>
        {!success && (
          <Button 
            onClick={handleDelete} 
            disabled={loading}
            variant="contained"
            color="error"
            startIcon={loading ? <CircularProgress size={20} color="inherit" /> : <WarningIcon />}
          >
            {loading ? 'Excluindo...' : 'Confirmar Exclusão'}
          </Button>
        )}
      </DialogActions>
    </Dialog>
  );
};

export default DeletarRegistroPessoaModal;
