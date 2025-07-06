import { Box } from "@mui/material";
import { colors } from "../utils/colors";
import Footer from "../components/Footer";
import Body from "../components/Body";
import GerenciarRegistro from "../components/GerenciarRegistro";

const Register: React.FC = () => {
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
      <Body>
        <GerenciarRegistro />
      </Body>

      {/* Footer sempre no final */}
      <Footer />
    </Box>
  );
};

export default Register;
