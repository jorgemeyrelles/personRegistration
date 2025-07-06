import React from "react";
import { BrowserRouter, Routes as RouterRoutes, Route } from "react-router-dom";
import Home from "../pages/Home";
import Logs from "../pages/Logs";
import NotFound from "../pages/NotFound";
import Login from "../pages/Login";
import Register from "../pages/Register";
import RecoverPassword from "../pages/RecoverPassword";
import { ProtectedRoute, PublicRoute } from "../components/RouteGuards";

const Routes: React.FC = () => {
  return (
    <BrowserRouter>
      <RouterRoutes>
        {/* Rotas públicas - redirecionam para / se já autenticado */}
        <Route
          path="/login"
          element={
            <PublicRoute>
              <Login />
            </PublicRoute>
          }
        />

        <Route
          path="/registrar"
          element={
            <PublicRoute>
              <Register />
            </PublicRoute>
          }
        />

        <Route
          path="/recuperar-senha"
          element={
            <PublicRoute>
              <RecoverPassword />
            </PublicRoute>
          }
        />

        {/* Rotas protegidas - redirecionam para /login se não autenticado */}
        <Route
          path="/"
          element={
            <ProtectedRoute>
              <Home />
            </ProtectedRoute>
          }
        />

        <Route
          path="/logs"
          element={
            <ProtectedRoute>
              <Logs />
            </ProtectedRoute>
          }
        />

        {/* Rota 404 - protegida também */}
        <Route
          path="*"
          element={
            <ProtectedRoute>
              <NotFound />
            </ProtectedRoute>
          }
        />
      </RouterRoutes>
    </BrowserRouter>
  );
};

export default Routes;
