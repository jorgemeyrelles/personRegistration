import React from "react";
import { Box, Grid } from "@mui/material";
import NavBar from "../components/NavBar";
import Body from "../components/Body";
import Footer from "../components/Footer";
import GerenciarLogs from "../components/GerenciarLogs";
import { colors } from "../utils/colors";

const Logs: React.FC = () => {
  return (
    <Box
      sx={{
        width: "100vw",
        height: "100vh",
        display: "flex",
        flexDirection: "column",
        backgroundColor: colors.background.default,
        overflow: "hidden",
      }}
    >
      {/* NavBar sempre no topo */}
      <NavBar />

      {/* Body principal com conteúdo */}
      <Body>
        <GerenciarLogs />
      </Body>

      {/* Footer sempre no final */}
      <Footer />
    </Box>
  );
};

export default Logs;
