import { Box } from "@mui/material";
import { colors } from "../utils/colors";
import Footer from "../components/Footer";
import Body from "../components/Body";
import GerenciarNovaSenha from "../components/GerenciarNovaSenha";

const RecoverPassword: React.FC = () => {
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
      {/* Body principal com conteúdo */}
      <Body style={{ justifyContent: "flex-startcenter" }}>
        <GerenciarNovaSenha />
      </Body>

      {/* Footer sempre no final */}
      <Footer />
    </Box>
  );
};

export default RecoverPassword;
