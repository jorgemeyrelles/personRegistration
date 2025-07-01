import React from "react";
import { Container, Typography, Divider, Box } from "@mui/material";
import { colors } from "../utils/colors";

const Footer: React.FC = () => {
  const currentYear = new Date().getFullYear();

  return (
    <Container
      component="footer"
      maxWidth={false}
      sx={{
        position: "fixed",
        bottom: 0,
        left: 0,
        right: 0,
        backgroundColor: colors.background.footer,
        color: colors.text.onSecondary,
        py: 2,
        px: 3,
        width: "100%",
        zIndex: 1000,
      }}
    >
      <Divider
        sx={{
          mb: 2,
          borderColor: colors.grey[600],
        }}
      />

      <Box
        sx={{
          display: "flex",
          flexDirection: { xs: "column", sm: "row" },
          justifyContent: { xs: "center", sm: "space-between" },
          alignItems: "center",
          gap: 2,
          maxWidth: "1200px",
          margin: "0 auto",
        }}
      >
        {/* Texto de direitos autorais */}
        <Typography
          variant="body2"
          sx={{
            color: colors.text.onSecondary,
            textAlign: { xs: "center", sm: "left" },
            fontSize: "0.875rem",
          }}
        >
          © {currentYear} Sistema de Registro de Pessoas. Todos os direitos
          reservados.
        </Typography>

        {/* Informações adicionais */}
        <Typography
          variant="body2"
          sx={{
            color: colors.grey[400],
            textAlign: { xs: "center", sm: "right" },
            fontSize: "0.75rem",
          }}
        >
          Desenvolvido com React + Material UI + TypeScript
        </Typography>
      </Box>
    </Container>
  );
};

export default Footer;
