# Dockerfile para React + Vite + TypeScript
# Sistema de Registro de Pessoas - Frontend

# Estágio 1: Build da aplicação
FROM node:20-alpine AS builder

# Definir ARGs
ARG VITE_API_BASE_URL=http://localhost:8081
ARG VITE_NODE_ENV=production
ARG NODE_ENV=production

# Instalar dependências do sistema
RUN apk add --no-cache git

# Definir diretório de trabalho
WORKDIR /app

# Copiar arquivos de dependências
COPY package*.json ./

# ✅ ALTERNATIVA: Usar npm ci incluindo devDependencies
RUN npm cache clean --force
RUN npm install -g typescript@5.8.3
RUN npm ci --include=dev --legacy-peer-deps

# Verificação
RUN npx tsc --version
RUN npx vite --version

# Copiar código fonte
COPY . .

# Definir variáveis de ambiente
ENV VITE_API_BASE_URL=$VITE_API_BASE_URL
ENV VITE_NODE_ENV=$VITE_NODE_ENV
ENV NODE_ENV=$NODE_ENV

# Build
RUN npm run build

# Verificar build
RUN ls -la /app/dist

# Estágio 2: Produção
FROM node:20-alpine AS production

# Instalar serve e curl
RUN npm install -g serve@latest
RUN apk add --no-cache curl

# Criar diretório
WORKDIR /app

# Copiar build
COPY --from=builder /app/dist ./dist

# Verificar se dist tem conteúdo
RUN ls -la /app/dist && test -f /app/dist/index.html

# Expor porta
EXPOSE 3031

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:3031/ || exit 1

# Iniciar servidor
CMD ["serve", "-s", "dist", "-l", "3031"]
