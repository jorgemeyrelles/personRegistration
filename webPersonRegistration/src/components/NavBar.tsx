import React from "react";
import { AppBar, Toolbar, Typography, IconButton, Box } from "@mui/material";
import {
  Settings as SettingsIcon,
  Person as PersonIcon,
  Timeline as TimelineIcon,
} from "@mui/icons-material";
import { useNavigate } from "react-router-dom";
import { colors, withOpacity } from "../utils/colors";

const NavBar: React.FC = () => {
  const navigate = useNavigate();

  const handleHomeClick = () => {
    navigate("/");
  };

  const handleLogsClick = () => {
    navigate("/logs");
  };

  const handleSettingsClick = () => {
    console.log("Settings clicked");
    // Aqui você pode adicionar lógica para abrir configurações
  };

  return (
    <AppBar
      position="fixed"
      elevation={0}
      sx={{
        backgroundColor: colors.background.navbar,
        boxShadow: colors.shadows.navbar,
        width: "100%",
        top: 0,
        left: 0,
        right: 0,
        zIndex: 1100,
      }}
    >
      <Toolbar
        style={{ width: "auto" }}
        sx={{ justifyContent: "space-between", width: "100%", px: 2 }}
      >
        {/* Logo/Título à esquerda */}
        <Box
          sx={{
            display: "flex",
            alignItems: "center",
            gap: 2,
            cursor: "pointer",
            "&:hover": {
              opacity: 0.8,
            },
          }}
          onClick={handleHomeClick}
        >
          <PersonIcon
            sx={{
              fontSize: 32,
              color: colors.text.onPrimary,
            }}
          />
          <Typography
            variant="h6"
            component="div"
            sx={{
              fontWeight: 600,
              color: colors.text.onPrimary,
              fontSize: {
                xs: "1.1rem",
                sm: "1.25rem",
              },
            }}
          >
            Sistema de Registro
          </Typography>
        </Box>

        {/* Botões à direita */}
        <Box sx={{ display: "flex", alignItems: "center", gap: 1 }}>
          {/* Botão de Logs */}
          <IconButton
            size="large"
            onClick={handleLogsClick}
            sx={{
              color: colors.text.onPrimary,
              "&:hover": {
                backgroundColor: withOpacity(colors.primary.light, 0.1),
              },
            }}
            aria-label="logs"
          >
            <TimelineIcon />
          </IconButton>

          {/* Botão de configurações */}
          <IconButton
            size="large"
            edge="end"
            onClick={handleSettingsClick}
            sx={{
              color: colors.text.onPrimary,
              "&:hover": {
                backgroundColor: withOpacity(colors.primary.light, 0.1),
              },
            }}
            aria-label="configurações"
          >
            <SettingsIcon />
          </IconButton>
        </Box>
      </Toolbar>
    </AppBar>
  );
};

export default NavBar;
