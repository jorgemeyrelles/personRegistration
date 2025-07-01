# Dockerfile para Sistema de Registro de Pessoas
# React + TypeScript + Vite + Material-UI

# Estágio 1: Build da aplicação
FROM node:18-alpine AS builder

# Definir diretório de trabalho
WORKDIR /app

# Copiar arquivos de dependências
COPY package.json package-lock.json* ./

# Instalar todas as dependências (incluindo dev dependencies para o build)
RUN npm ci

# Copiar código fonte
COPY . .

# Build da aplicação para produção
RUN npm run build

# Estágio 2: Servidor de produção
FROM nginx:alpine AS production

# Instalar curl para health checks
RUN apk add --no-cache curl

# Remover configuração padrão do nginx
RUN rm -rf /usr/share/nginx/html/*

# Copiar build da aplicação
COPY --from=builder /app/dist /usr/share/nginx/html

# Copiar configuração customizada do nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expor porta 3030
EXPOSE 3030

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:3030/ || exit 1

# Comando para iniciar o nginx
CMD ["nginx", "-g", "daemon off;"]
