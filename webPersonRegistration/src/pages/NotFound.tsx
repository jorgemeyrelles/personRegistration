import React from "react";
import {
  Box,
  Typography,
  Button,
  Container,
  Stack,
  Avatar,
} from "@mui/material";
import { Home as HomeIcon, Search as SearchIcon } from "@mui/icons-material";
import { useNavigate } from "react-router-dom";
import { colors } from "../utils/colors";

const NotFound: React.FC = () => {
  const navigate = useNavigate();

  const handleGoHome = () => {
    navigate("/");
  };

  const handleGoBack = () => {
    navigate(-1);
  };

  return (
    <Container maxWidth="md">
      <Box
        sx={{
          minHeight: "100vh",
          display: "flex",
          flexDirection: "column",
          justifyContent: "center",
          alignItems: "center",
          textAlign: "center",
          py: 4,
        }}
      >
        {/* Ícone e código 404 */}
        <Avatar
          sx={{
            bgcolor: colors.error.main,
            width: 80,
            height: 80,
            mb: 3,
            fontSize: "2rem",
          }}
        >
          <SearchIcon sx={{ fontSize: "3rem" }} />
        </Avatar>

        {/* Código de erro */}
        <Typography
          variant="h1"
          component="h1"
          sx={{
            fontSize: { xs: "4rem", md: "6rem" },
            fontWeight: "bold",
            color: colors.primary.main,
            mb: 2,
            lineHeight: 1,
          }}
        >
          404
        </Typography>

        {/* Título */}
        <Typography
          variant="h4"
          component="h2"
          gutterBottom
          sx={{
            color: colors.text.primary,
            fontWeight: 600,
            mb: 2,
          }}
        >
          Página não encontrada
        </Typography>

        {/* Descrição */}
        <Typography
          variant="body1"
          color="text.secondary"
          sx={{
            maxWidth: 500,
            mb: 4,
            lineHeight: 1.6,
          }}
        >
          Ops! A página que você está procurando não existe ou foi movida.
          Verifique se o endereço está correto ou navegue para uma das páginas
          disponíveis.
        </Typography>

        {/* Botões de ação */}
        <Stack
          direction={{ xs: "column", sm: "row" }}
          spacing={2}
          sx={{ width: { xs: "100%", sm: "auto" } }}
        >
          <Button
            variant="contained"
            size="large"
            startIcon={<HomeIcon />}
            onClick={handleGoHome}
            sx={{
              backgroundColor: colors.primary.main,
              "&:hover": {
                backgroundColor: colors.primary.dark,
              },
              px: 4,
            }}
          >
            Ir para Home
          </Button>

          <Button
            variant="outlined"
            size="large"
            onClick={handleGoBack}
            sx={{
              borderColor: colors.primary.main,
              color: colors.primary.main,
              "&:hover": {
                borderColor: colors.primary.dark,
                backgroundColor: colors.primary.light + "10",
              },
              px: 4,
            }}
          >
            Voltar
          </Button>
        </Stack>

        {/* Links úteis */}
        <Box sx={{ mt: 6 }}>
          <Typography variant="body2" color="text.secondary" gutterBottom>
            Páginas disponíveis:
          </Typography>
          <Stack direction="row" spacing={3} justifyContent="center">
            <Button
              variant="text"
              onClick={() => navigate("/")}
              sx={{ color: colors.primary.main }}
            >
              Home
            </Button>
            <Button
              variant="text"
              onClick={() => navigate("/logs")}
              sx={{ color: colors.primary.main }}
            >
              Logs
            </Button>
          </Stack>
        </Box>
      </Box>
    </Container>
  );
};

export default NotFound;
