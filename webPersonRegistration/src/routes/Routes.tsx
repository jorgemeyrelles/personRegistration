import React from "react";
import { BrowserRouter, Routes as RouterRoutes, Route } from "react-router-dom";
import Home from "../pages/Home";
import Logs from "../pages/Logs";
import NotFound from "../pages/NotFound";

const Routes: React.FC = () => {
  return (
    <BrowserRouter>
      <RouterRoutes>
        {/* Rota principal - Home */}
        <Route path="/" element={<Home />} />

        {/* Rota de Logs */}
        <Route path="/logs" element={<Logs />} />

        {/* Rota 404 - Not Found */}
        <Route path="*" element={<NotFound />} />
      </RouterRoutes>
    </BrowserRouter>
  );
};

export default Routes;
