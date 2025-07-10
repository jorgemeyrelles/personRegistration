import React from "react";
import { Container, ContainerProps } from "@mui/material";
import { colors } from "../utils/colors";

interface BodyProps extends Omit<ContainerProps, "children"> {
  children: React.ReactNode;
  props?: any; // Permite passar propriedades adicionais, se necessário
}

const Body: React.FC<BodyProps> = ({ children, props }) => {
  return (
    <Container
      {...props}
      component="main"
      maxWidth={false}
      sx={{
        flexGrow: 1,
        backgroundColor: colors.background.default,
        width: "100%",
        height: "100%",
        display: "flex",
        flexDirection: "column",
        justifyContent: "center", // Centraliza verticalmente
        alignItems: "center", // Centraliza horizontalmente
        overflow: "auto", // Permite scroll se necessário
        padding: 0,
        margin: 0,
      }}
    >
      {children}
    </Container>
  );
};

export default Body;
