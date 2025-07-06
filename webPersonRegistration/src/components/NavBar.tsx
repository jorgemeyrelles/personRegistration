import React, { useState } from "react";
import {
  AppBar,
  Toolbar,
  Typography,
  IconButton,
  Box,
  Menu,
  MenuItem,
  Divider,
  Avatar,
  ListItemIcon,
  ListItemText,
} from "@mui/material";
import {
  Settings as SettingsIcon,
  Person as PersonIcon,
  Timeline as TimelineIcon,
  Logout as LogoutIcon,
  AccountCircle as AccountCircleIcon,
} from "@mui/icons-material";
import { useNavigate } from "react-router-dom";
import { colors, withOpacity } from "../utils/colors";
import { useAuth } from "../hooks/useAuth";

const NavBar: React.FC = () => {
  const navigate = useNavigate();
  const { usuario, logout } = useAuth();
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const open = Boolean(anchorEl);

  const handleHomeClick = () => {
    navigate("/");
  };

  const handleLogsClick = () => {
    navigate("/logs");
  };

  const handleSettingsClick = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };

  const handleCloseMenu = () => {
    setAnchorEl(null);
  };

  const handleLogout = () => {
    // Limpar dados de autenticação dos cookies
    logout();

    // Fechar menu
    handleCloseMenu();

    // Redirecionar para login
    navigate("/login");
  };

  // Função para obter iniciais do nome
  const getInitials = (nome: string): string => {
    return nome
      .split(" ")
      .map((part) => part.charAt(0))
      .join("")
      .toUpperCase()
      .substring(0, 2);
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
            aria-controls={open ? "user-menu" : undefined}
            aria-haspopup="true"
            aria-expanded={open ? "true" : undefined}
          >
            <SettingsIcon />
          </IconButton>

          {/* Menu Dropdown do Usuário */}
          <Menu
            id="user-menu"
            anchorEl={anchorEl}
            open={open}
            onClose={handleCloseMenu}
            onClick={handleCloseMenu}
            PaperProps={{
              elevation: 8,
              sx: {
                mt: 1.5,
                minWidth: 250,
                borderRadius: 2,
                backgroundColor: colors.background.paper,
                boxShadow: colors.shadows.heavy,
                "&::before": {
                  content: '""',
                  display: "block",
                  position: "absolute",
                  top: 0,
                  right: 14,
                  width: 10,
                  height: 10,
                  bgcolor: colors.background.paper,
                  transform: "translateY(-50%) rotate(45deg)",
                  zIndex: 0,
                },
              },
            }}
            transformOrigin={{ horizontal: "right", vertical: "top" }}
            anchorOrigin={{ horizontal: "right", vertical: "bottom" }}
          >
            {/* Informações do Usuário */}
            <Box sx={{ px: 2, py: 1.5 }}>
              <Box sx={{ display: "flex", alignItems: "center", gap: 1.5 }}>
                <Avatar
                  sx={{
                    width: 40,
                    height: 40,
                    bgcolor: colors.primary.main,
                    color: colors.text.onPrimary,
                    fontSize: "0.9rem",
                    fontWeight: "bold",
                  }}
                >
                  {usuario ? getInitials(usuario.nome) : "U"}
                </Avatar>
                <Box sx={{ flex: 1, minWidth: 0 }}>
                  <Typography
                    variant="subtitle2"
                    sx={{
                      fontWeight: 600,
                      color: colors.text.primary,
                      overflow: "hidden",
                      textOverflow: "ellipsis",
                      whiteSpace: "nowrap",
                    }}
                  >
                    {usuario?.nome || "Usuário"}
                  </Typography>
                  <Typography
                    variant="caption"
                    sx={{
                      color: colors.text.secondary,
                      overflow: "hidden",
                      textOverflow: "ellipsis",
                      whiteSpace: "nowrap",
                      display: "block",
                    }}
                  >
                    {usuario?.email || "email@exemplo.com"}
                  </Typography>
                </Box>
              </Box>
            </Box>

            <Divider sx={{ borderColor: colors.grey[200] }} />

            {/* Opção de Perfil */}
            <MenuItem
              onClick={handleCloseMenu}
              sx={{
                py: 1,
                "&:hover": {
                  backgroundColor: withOpacity(colors.primary.main, 0.08),
                },
              }}
            >
              <ListItemIcon>
                <AccountCircleIcon
                  sx={{
                    fontSize: 20,
                    color: colors.text.secondary,
                  }}
                />
              </ListItemIcon>
              <ListItemText
                primary="Meu Perfil"
                primaryTypographyProps={{
                  fontSize: "0.9rem",
                  color: colors.text.primary,
                }}
              />
            </MenuItem>

            <Divider sx={{ borderColor: colors.grey[200] }} />

            {/* Botão de Logout */}
            {usuario?.nomePerfil === "ADMIN" && (
              <MenuItem
                onClick={handleLogout}
                sx={{
                  py: 1,
                  "&:hover": {
                    backgroundColor: withOpacity(colors.error.main, 0.08),
                  },
                }}
              >
                <ListItemIcon>
                  <LogoutIcon
                    sx={{
                      fontSize: 20,
                      color: colors.error.main,
                    }}
                  />
                </ListItemIcon>
                <ListItemText
                  primary="Sair"
                  primaryTypographyProps={{
                    fontSize: "0.9rem",
                    color: colors.error.main,
                    fontWeight: 500,
                  }}
                />
              </MenuItem>
            )}
          </Menu>
        </Box>
      </Toolbar>
    </AppBar>
  );
};

export default NavBar;
