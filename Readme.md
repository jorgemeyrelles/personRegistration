# 🚀 Sistema de Registro de Pessoas - Full Stack

Sistema completo de gerenciamento de registros de pessoas, desenvolvido com **Spring Boot** (backe## ⭐ Funcionalidades

### 🔐 Sistema de Autenticação Completo

- ✅ **Login Seguro**: Autenticação JWT com Spring Security
- ✅ **Registro de Usuários**: Criação de contas com validações robustas
- ✅ **Recuperação de Senha**: Sistema PATCH para atualização segura de senhas
- ✅ **Cookies Seguros**: Armazenamento seguro de tokens em cookies HTTP-only
- ✅ **Controle de Perfis**: Sistema de permissões baseado em perfis (ADMIN, USER)
- ✅ **Logout Automático**: Limpeza segura de dados de autenticação
- ✅ **Proteção de Rotas**: Guards de autenticação para páginas protegidas

### 👥 Gerenciamento de Pessoas

- ✅ **CRUD Completo**: Criar, listar, editar e deletar registros
- ✅ **Filtros Inteligentes**: Busca em tempo real por nome e estado
- ✅ **Controle por Perfil**: ADMIN vê todos os registros, outros usuários veem apenas os próprios
- ✅ **Validações Robustas**: CPF, telefone, CEP com formatação automática
- ✅ **Integração ViaCEP**: Preenchimento automático de endereços
- ✅ **Interface Responsiva**: Cards compactos e design moderno
- ✅ **Indicadores Visuais**: Chips para mostrar filtros ativos e restrições por perfil

### 🎨 Interface do Usuário Moderna

- ✅ **Material-UI v5**: Design system moderno e responsivo
- ✅ **NavBar Dinâmica**: Menu de usuário com dados do perfil logado
- ✅ **Modais Compactos**: Formulários organizados em layout de 3 colunas
- ✅ **Feedback Visual**: Validações inline sem textos de erro intrusivos
- ✅ **Navegação Intuitiva**: Redirecionamentos automáticos pós-autenticação
- ✅ **Tema Customizado**: Paleta de cores consistente em todo o sistemapeScript** (frontend), utilizando **Docker** para containerização e **PostgreSQL\*\* como banco de dados principal.

## 📋 Índice

- [🎯 Visão Geral](#-visão-geral)
- [🛠️ Tecnologias](#-tecnologias)
- [🏗️ Arquitetura](#-arquitetura)
- [⭐ Funcionalidades](#-funcionalidades)
- [🔧 Pré-requisitos](#-pré-requisitos)
- [🚀 Instalação e Execução](#-instalação-e-execução)
  - [💻 Execução Local](#-execução-local)
  - [🐳 Execução com Docker](#-execução-com-docker)
- [📁 Estrutura do Projeto](#-estrutura-do-projeto)
- [🌐 API Endpoints](#-api-endpoints)
- [⚙️ Variáveis de Ambiente](#-variáveis-de-ambiente)
- [🐳 Docker e Infraestrutura](#-docker-e-infraestrutura)
- [🔬 Desenvolvimento](#-desenvolvimento)
- [🚀 Roadmap e Futuras Features](#-roadmap-e-futuras-features)
- [🤝 Contribuição](#-contribuição)
- [📄 Licença](#-licença)

## 🎯 Visão Geral

O **Sistema de Registro de Pessoas** é uma aplicação full stack moderna e escalável que oferece:

### 🎨 Interface do Usuário

- 📝 **Gerenciamento Completo de Pessoas** com CRUD intuitivo
- � **Busca Inteligente** com filtros por nome e estado em tempo real
- 📊 **Sistema de Logs Unificado** para operações e mensageria
- � **Interface Responsiva** com design moderno e Material-UI
- ⚡ **Validações Inteligentes** de CPF, CEP e telefone
- �️ **Integração ViaCEP** para preenchimento automático de endereços

### 🔧 Funcionalidades Técnicas

- 🔐 **Autenticação JWT** com Spring Security
- 📧 **Sistema de Notificações** via RabbitMQ e MailHog
- � **Cache Distribuído** com Redis
- 📈 **Logs Estruturados** no MongoDB
- 🐳 **Containerização Completa** com Docker Compose
- 🌐 **Proxy Reverso** com Nginx para produção

## 🛠️ Tecnologias

### 🔙 Backend (API Spring Boot)

- **Java 21** - Linguagem principal
- **Spring Boot 3.5.3** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Spring Security + JWT** - Autenticação e autorização
- **Spring AMQP** - Mensageria com RabbitMQ
- **PostgreSQL** - Banco de dados principal
- **MongoDB** - Armazenamento de logs
- **RabbitMQ** - Message broker assíncrono
- **Redis** - Cache distribuído
- **MailHog** - Servidor de email para desenvolvimento
- **OpenAPI 3** - Documentação da API
- **Maven** - Gerenciamento de dependências
- **Docker** - Containerização

### 🎨 Frontend (React + TypeScript)

- **React 18** - Biblioteca UI moderna
- **TypeScript** - Tipagem estática
- **Vite** - Build tool e dev server rápido
- **Material-UI (MUI) v5** - Framework de componentes
- **React Router v6** - Roteamento SPA
- **React Hook Form** - Gerenciamento de formulários
- **Custom Hooks** - useAuth para gerenciamento de autenticação
- **Secure Cookies** - Armazenamento seguro de tokens
- **Responsive Design** - Layout adaptável para mobile/desktop
- **Component Composition** - Arquitetura modular e reutilizável
- **TypeScript Strict** - Tipagem rigorosa em toda aplicação
- **Docker + Nginx** - Containerização e proxy reverso

### Infraestrutura

- **Docker & Docker Compose** - Orquestração
- **PostgreSQL** - Banco principal
- **MongoDB** - Logs e analytics
- **RabbitMQ** - Message broker
- **Redis** - Cache
- **MailHog** - Servidor de email para desenvolvimento
- **Nginx** - Proxy reverso

## 🏗️ Arquitetura

### 📊 Diagrama de Arquitetura

```
┌─────────────────────────────────────────────────────────────────┐
│                        FRONTEND (React)                         │
├─────────────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐              │
│  │    Home     │  │    Logs     │  │   Login     │              │
│  │  (CRUD)     │  │ (DataGrid)  │  │ (Auth)      │              │
│  └─────────────┘  └─────────────┘  └─────────────┘              │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐              │
│  │  Register   │  │  Recover    │  │  NotFound   │              │
│  │  (Sign Up)  │  │ (Password)  │  │   (404)     │              │
│  └─────────────┘  └─────────────┘  └─────────────┘              │
│                                                                 │
│  ┌─────────────────────────────────────────────────────────────┐│
│  │         COMPONENTES, HOOKS & SERVIÇOS                       ││
│  │ • NavBar • GerenciarPessoas • GerenciarLogs • Modals        ││
│  │ • useAuth • RouteGuards • API Services • Secure Cookies     ││
│  └─────────────────────────────────────────────────────────────┘│
└─────────────────────────────────────────────────────────────────┘
                                    │ HTTP/REST API + JWT
                                    ▼
┌─────────────────────────────────────────────────────────────────┐
│                      NGINX (Proxy Reverso)                      │
│                     • Frontend: Port 3031                       │
│                     • API Proxy: /api/* → 8081                  │
└─────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────┐
│                    BACKEND (Spring Boot)                        │
├─────────────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐              │
│  │Controllers  │  │  Services   │  │Repositories │              │
│  │   (REST)    │  │ (Business)  │  │   (Data)    │              │
│  └─────────────┘  └─────────────┘  └─────────────┘              │
│                                                                 │
│  ┌─────────────────────────────────────────────────────────────┐│
│  │ JWT Security • User Management • Password Recovery          ││
│  │ Profile-based Access Control • RabbitMQ • Email • Cache     ││
│  └─────────────────────────────────────────────────────────────┘│
└─────────────────────────────────────────────────────────────────┘
                            │          │         │         │
                    ┌───────▼──┐   ┌───▼───┐   ┌─▼─────┐  ┌▼───────┐
                    │PostgreSQL│   │MongoDB│   │Redis  │  │RabbitMQ│
                    │(Main DB) │   │(Logs) │   │(Cache)│  │(Queue) │
                    └──────────┘   └───────┘   └───────┘  └────────┘
```

### 🔄 Fluxo de Dados

```
1. 👤 Usuário acessa páginas de Login/Registro/Recuperação de Senha
2. 🔐 Sistema autentica e gera JWT com informações de perfil
3. 🍪 Token é armazenado em cookies seguros HTTP-only
4. 🌐 Nginx roteia requisições autenticadas para Backend
5. �️ Spring Security valida JWT tokens em cada requisição
6. 📊 Controllers processam requisições REST com controle de perfil
7. 🔧 Services executam lógica de negócio com filtros por usuário
8. 💾 Repositories persistem dados no PostgreSQL
9. 🎯 Frontend filtra dados baseado no perfil (ADMIN vs USER)
10. 📝 Logs são enviados assincronamente para MongoDB
11. 📧 Notificações são enviadas via RabbitMQ/MailHog
12. ⚡ Cache Redis otimiza consultas frequentes
```

## ⭐ Funcionalidades

### 👥 Gerenciamento de Pessoas

- ✅ **CRUD Completo**: Criar, listar, editar e deletar registros
- ✅ **Busca Inteligente**: Filtros em tempo real por nome e estado
- ✅ **Validações Robustas**: CPF, telefone, CEP com formatação automática
- ✅ **Integração ViaCEP**: Preenchimento automático de endereços
- ✅ **Interface Responsiva**: Cards compactos e design moderno
- ✅ **Paginação**: Navegação eficiente em grandes volumes de dados

### 🔐 Autenticação e Segurança

- ✅ **JWT Authentication**: Tokens seguros com expiração configurável
- ✅ **Spring Security**: Proteção de endpoints e autorização
- ✅ **Validação de Dados**: Bean Validation no backend
- ✅ **CORS Configurado**: Comunicação segura entre frontend e backend
- ✅ **Password Hashing**: Senhas criptografadas com BCrypt

### 📊 Sistema de Logs e Monitoramento

- ✅ **Logs Unificados**: Operações e mensageria em uma única view
- ✅ **DataGrid Avançado**: Tabela com ordenação, paginação e filtros
- ✅ **Categorização Visual**: Chips coloridos para diferentes tipos de log
- ✅ **Busca Avançada**: Filtros por tipo, data e conteúdo
- ✅ **MongoDB Storage**: Armazenamento otimizado para logs

### 📧 Comunicação e Notificações

- ✅ **RabbitMQ Integration**: Mensageria assíncrona confiável
- ✅ **Email Service**: Notificações via MailHog (desenvolvimento)
- ✅ **Event Driven**: Arquitetura orientada a eventos
- ✅ **Message Queues**: Processamento assíncrono de tarefas

### ⚡ Performance e Escalabilidade

- ✅ **Redis Cache**: Cache distribuído para queries frequentes
- ✅ **Connection Pooling**: Otimização de conexões com banco
- ✅ **Lazy Loading**: Carregamento otimizado de dados relacionados
- ✅ **API Pagination**: Paginação eficiente de grandes datasets

## 🔧 Pré-requisitos

### Para Execução Local

- **Java 21+** (JDK)
- **Node.js 18+**
- **PostgreSQL 15+**
- **MongoDB 6+**
- **RabbitMQ 3.12+**
- **Redis 7+**
- **Maven 3.9+**
- **npm ou yarn**

### Para Execução com Docker

- **Docker 24+**
- **Docker Compose 2.20+**

## 🚀 Instalação e Execução

### � Execução Rápida com Docker Hub (Recomendado)

> **Ideal para quem quer testar o sistema rapidamente sem fazer build das imagens**

#### 📋 Pré-requisitos

- **Docker 24+** e **Docker Compose 2.20+**

#### 🚀 Passos de Execução

```bash
# 1. Clone o repositório
git clone https://github.com/jorgemeyrelles/personRegistration.git
cd personProject

# 2. Crie o arquivo docker-compose para imagens públicas
cat > docker-compose.hub.yml << 'EOF'
networks:
  projeto-person-registration:
    driver: bridge

services:
  postgres:
    image: postgres:15-alpine
    container_name: postgres_api_person_registration
    restart: always
    environment:
      POSTGRES_DB: bd_apipersonregistration
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: root
    ports:
      - "5435:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    networks:
      - projeto-person-registration
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U admin -d bd_apipersonregistration"]
      interval: 10s
      timeout: 5s
      retries: 5

  mongodb:
    image: mongo:6-alpine
    container_name: mongodb-container
    ports:
      - "27018:27017"
    volumes:
      - mongodb_data:/data/db
    networks:
      - projeto-person-registration

  rabbitmq:
    image: rabbitmq:3.12-management-alpine
    container_name: rabbitmq-container
    ports:
      - "5672:5672"
      - "15672:15672"
    environment:
      RABBITMQ_DEFAULT_USER: guest
      RABBITMQ_DEFAULT_PASS: guest
    networks:
      - projeto-person-registration

  redis:
    image: redis:7-alpine
    container_name: redis_cache
    ports:
      - "6379:6379"
    networks:
      - projeto-person-registration

  mailhog:
    image: mailhog/mailhog:latest
    container_name: mailhog_container
    ports:
      - "8025:8025"
      - "1025:1025"
    restart: always
    networks:
      - projeto-person-registration

  api-person-registration:
    image: jorgemeyrelles/api-person-registration:latest
    container_name: api_person_registration
    restart: always
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/bd_apipersonregistration
      SPRING_DATASOURCE_USERNAME: admin
      SPRING_DATASOURCE_PASSWORD: root
      SPRING_DATA_MONGODB_URI: mongodb://mongodb-container:27017/log_apiusuarios
      SPRING_RABBITMQ_HOST: rabbitmq-container
      SPRING_DATA_REDIS_HOST: redis
      SPRING_MAIL_HOST: mailhog_container
    ports:
      - "8081:8080"
    depends_on:
      postgres:
        condition: service_healthy
    networks:
      - projeto-person-registration

  web-person-registration:
    image: jorgemeyrelles/web-person-registration:latest
    container_name: web_person_registration
    restart: always
    ports:
      - "3031:3031"
    environment:
      - NODE_ENV=production
      - PORT=3031
    depends_on:
      - api-person-registration
    networks:
      - projeto-person-registration

volumes:
  postgres_data:
  mongodb_data:
EOF

# 3. Execute todo o ambiente
docker-compose -f docker-compose.hub.yml up -d

# 4. Aguarde a inicialização (2-3 minutos)
docker-compose -f docker-compose.hub.yml logs -f api-person-registration

# 5. Aguarde ver a mensagem: "Started PersonRegistrationApplication"
# Ctrl+C para sair dos logs
```

#### ✅ Verificação da Instalação

```bash
# Verificar status dos containers
docker-compose -f docker-compose.hub.yml ps

# Testar conectividade da API
curl http://localhost:8081/api/health || echo "API ainda inicializando..."

# Acessar as aplicações
echo "🎨 Frontend: http://localhost:3031"
echo "🔙 API: http://localhost:8081"
echo "📖 Swagger: http://localhost:8081/swagger-ui.html"
```

---

### �💻 Execução Local (Desenvolvimento)

> **Ideal para desenvolvedores que querem modificar o código**

#### 📋 Pré-requisitos

- **Java 21+** (JDK)
- **Node.js 18+** e **npm**
- **PostgreSQL 15+**
- **MongoDB 6+**
- **RabbitMQ 3.12+**
- **Redis 7+**
- **Maven 3.9+**

#### 🔙 1. Configuração do Backend (Spring Boot)

```bash
# 1. Clone e entre no diretório do backend
git clone https://github.com/jorgemeyrelles/personRegistration.git
cd personProject/apiPersonRegistration

# 2. Configure as variáveis de ambiente
cp src/main/resources/application.properties.example src/main/resources/application.properties

# 3. Edite o application.properties com suas configurações
# Exemplo para ambiente local:
cat > src/main/resources/application.properties << 'EOF'
# Configurações do Banco de Dados PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/bd_apipersonregistration
spring.datasource.username=admin
spring.datasource.password=root
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# JWT Configuration
jwt.secretkey=minha-chave-secreta-super-segura-de-256-bits-para-jwt-tokens
jwt.expiration=86400000

# MongoDB para logs
spring.data.mongodb.uri=mongodb://localhost:27017/log_apiusuarios

# RabbitMQ
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

# Redis Cache
spring.data.redis.host=localhost
spring.data.redis.port=6379

# Email (MailHog para desenvolvimento)
spring.mail.host=localhost
spring.mail.port=1025
spring.mail.username=naoresponder@personreg.com
spring.mail.properties.mail.smtp.auth=false
spring.mail.properties.mail.smtp.starttls.enable=false

# Configurações da aplicação
server.port=8080
spring.application.name=api-person-registration
EOF

# 4. Instale dependências e compile
mvn clean install -DskipTests

# 5. Execute a aplicação
mvn spring-boot:run

# A API estará disponível em: http://localhost:8080
# Swagger UI: http://localhost:8080/swagger-ui.html
```

#### 🎨 2. Configuração do Frontend (React)

```bash
# 1. Em outro terminal, entre no diretório do frontend
cd ../webPersonRegistration

# 2. Configure as variáveis de ambiente
cat > .env << 'EOF'
# API Configuration
VITE_API_BASE_URL=http://localhost:8080

# Application Settings
VITE_APP_NAME="Sistema de Registro de Pessoas"
VITE_APP_VERSION="1.0.0"

# Development Settings
VITE_NODE_ENV=development
VITE_ENABLE_DEBUG=true
EOF

# 3. Instale as dependências
npm install

# 4. Execute em modo desenvolvimento (com hot reload)
npm run dev

# Frontend estará disponível em: http://localhost:5173
```

#### � 3. Configuração da Infraestrutura (Opcional)

Se não tiver os serviços instalados localmente, use Docker apenas para infraestrutura:

```bash
# Na raiz do projeto, crie docker-compose.infra.yml
cat > docker-compose.infra.yml << 'EOF'
networks:
  projeto-person-registration:
    driver: bridge

services:
  postgres:
    image: postgres:15-alpine
    container_name: postgres_local_dev
    ports:
      - "5432:5432"
    environment:
      POSTGRES_DB: bd_apipersonregistration
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: root
    volumes:
      - postgres_dev_data:/var/lib/postgresql/data
    networks:
      - projeto-person-registration

  mongodb:
    image: mongo:6-alpine
    container_name: mongodb_local_dev
    ports:
      - "27017:27017"
    volumes:
      - mongodb_dev_data:/data/db
    networks:
      - projeto-person-registration

  rabbitmq:
    image: rabbitmq:3.12-management-alpine
    container_name: rabbitmq_local_dev
    ports:
      - "5672:5672"
      - "15672:15672"
    environment:
      RABBITMQ_DEFAULT_USER: guest
      RABBITMQ_DEFAULT_PASS: guest
    networks:
      - projeto-person-registration

  redis:
    image: redis:7-alpine
    container_name: redis_local_dev
    ports:
      - "6379:6379"
    networks:
      - projeto-person-registration

  mailhog:
    image: mailhog/mailhog:latest
    container_name: mailhog_local_dev
    ports:
      - "8025:8025"
      - "1025:1025"
    networks:
      - projeto-person-registration

volumes:
  postgres_dev_data:
  mongodb_dev_data:
EOF

# Execute apenas a infraestrutura
docker-compose -f docker-compose.infra.yml up -d
```

---

### 🐳 Execução com Docker Build Local

> **Para desenvolvedores que querem buildar as imagens localmente**

```bash
# 1. Clone o repositório
git clone https://github.com/jorgemeyrelles/personRegistration.git
cd personProject

# 2. Execute todo o ambiente com build local
docker-compose up -d --build

# 3. Aguarde a inicialização (5-10 minutos na primeira vez)
docker-compose logs -f api_person_registration

# 4. Aguarde ver: "Started PersonRegistrationApplication"
```

### 🌐 URLs de Acesso

#### 🐳 Com Docker Hub / Docker Build

| Serviço            | URL                                     | Credenciais | Descrição              |
| ------------------ | --------------------------------------- | ----------- | ---------------------- |
| 🎨 **Frontend**    | `http://localhost:3031`                 | -           | Interface React        |
| 🔙 **API Backend** | `http://localhost:8081`                 | -           | API Spring Boot        |
| 📖 **Swagger UI**  | `http://localhost:8081/swagger-ui.html` | -           | Documentação da API    |
| 📧 **MailHog UI**  | `http://localhost:8025`                 | -           | Interface de emails    |
| 🐰 **RabbitMQ UI** | `http://localhost:15672`                | guest/guest | Gerenciamento de filas |

#### 💻 Desenvolvimento Local

| Serviço             | URL                                     | Credenciais | Descrição           |
| ------------------- | --------------------------------------- | ----------- | ------------------- |
| 🎨 **Frontend Dev** | `http://localhost:5173`                 | -           | Vite dev server     |
| 🔙 **API Backend**  | `http://localhost:8080`                 | -           | API Spring Boot     |
| 📖 **Swagger UI**   | `http://localhost:8080/swagger-ui.html` | -           | Documentação da API |

#### 🗄️ Bancos de Dados

| Serviço           | Host/Porta                                             | Credenciais | Database                 |
| ----------------- | ------------------------------------------------------ | ----------- | ------------------------ |
| 🐘 **PostgreSQL** | `localhost:5435` (Docker) / `localhost:5432` (Local)   | admin/root  | bd_apipersonregistration |
| 🍃 **MongoDB**    | `localhost:27018` (Docker) / `localhost:27017` (Local) | -           | log_apiusuarios          |
| ⚡ **Redis**      | `localhost:6379`                                       | -           | Cache                    |

#### 👤 Usuários de Teste

**Admin User:**

- **Email:** `admin@personreg.com`
- **Senha:** `admin123`
- **Perfil:** `ADMIN` (vê todos os registros)

**User Regular:**

- **Email:** `user@personreg.com`
- **Senha:** `user123`
- **Perfil:** `DEFAULT` (vê apenas próprios registros)

> **📝 Nota:** Estes usuários são criados automaticamente na inicialização da aplicação

## 📁 Estrutura do Projeto

```
personProject/
├── 📄 README.md                          # Documentação principal
├── 🐳 docker-compose.yaml                # Orquestração completa
│
├── 🔙 apiPersonRegistration/              # Backend Spring Boot
│   ├── 📄 Dockerfile                     # Container da API
│   ├── 📦 pom.xml                        # Dependências Maven
│   ├── ⚙️ src/main/resources/
│   │   └── application.properties        # Configurações do Spring
│   └── ☕ src/main/java/br/com/personreg/
│       ├── 🎯 controllers/               # REST Controllers
│       ├── 🔧 services/                  # Lógica de negócio
│       ├── 🗄️ repositories/              # Acesso a dados
│       ├── 📊 entities/                  # Entidades JPA
│       ├── 📝 dtos/                      # Data Transfer Objects
│       ├── 🔐 configurations/            # Configurações Spring
│       ├── 🛡️ filters/                   # Filtros de segurança
│       ├── ⚠️ exceptions/                # Exception Handlers
│       └── 🏃 runners/                   # Initialization Runners
│
└── 🎨 webPersonRegistration/              # Frontend React
    ├── 📄 Dockerfile                     # Container produção
    ├── 🔧 Dockerfile.dev                 # Container desenvolvimento
    ├── 🌐 nginx.conf                     # Configuração Nginx
    ├── 🐳 docker-compose.yml             # Compose local
    ├── 📦 package.json                   # Dependências npm
    ├── ⚙️ vite.config.ts                 # Configuração Vite
    └── 🗂️ src/
        ├── 🧩 components/                # Componentes React
        │   ├── NavBar.tsx               # Barra de navegação com menu de usuário
        │   ├── Body.tsx                 # Container principal
        │   ├── Footer.tsx               # Rodapé da aplicação
        │   ├── RouteGuards.tsx          # Proteção de rotas autenticadas
        │   ├── GerenciarPessoas.tsx     # CRUD de pessoas com filtro por perfil
        │   ├── GerenciarLogs.tsx        # Visualização de logs
        │   ├── GerenciarLogin.tsx       # Componente de autenticação
        │   ├── GerenciarRegistro.tsx    # Componente de cadastro de usuário
        │   ├── GerenciarNovaSenha.tsx   # Componente de recuperação de senha
        │   ├── CriarRegistroPessoaModal.tsx    # Modal de criação/edição
        │   └── DeletarRegistroPessoaModal.tsx  # Modal de confirmação
        ├── 📄 pages/                     # Páginas da aplicação
        │   ├── Home.tsx                 # Página inicial (CRUD pessoas)
        │   ├── Login.tsx                # Página de login
        │   ├── Register.tsx             # Página de cadastro
        │   ├── RecoverPassword.tsx      # Página de recuperação de senha
        │   ├── Logs.tsx                 # Página de logs
        │   └── NotFound.tsx             # Página 404
        ├── 🔗 routes/                    # Configuração de rotas
        │   └── Routes.tsx               # Router principal com guards
        ├── 🔧 service/                   # Serviços e API calls
        │   ├── usuarios/                # Serviços de usuários/autenticação
        │   ├── registroPessoas/         # Serviços de pessoas
        │   └── exemplos/                # Exemplos de uso
        ├── 🎣 hooks/                     # Custom Hooks
        │   └── useAuth.ts               # Hook de autenticação
        ├── 📝 types/                     # Definições TypeScript
        │   ├── usuario.ts               # Tipos de usuário e autenticação
        │   ├── registroPessoasTypes.ts  # Tipos de registro de pessoas
        │   ├── logs.ts                  # Tipos de logs
        │   └── perfil.ts                # Tipos de perfil
        ├── 🎨 utils/                     # Utilitários e helpers
        │   ├── colors.ts                # Paleta de cores do sistema
        │   └── cookies.ts               # Utilitários para cookies seguros
        └── ⚙️ config/                    # Configurações da aplicação
            └── api.ts                   # Configurações da API
```

## 🌐 API Endpoints

### 🔐 Autenticação e Usuários

```http
# Gestão de Usuários
POST /api/usuario/criar               # Criar novo usuário
POST /api/usuario/autenticar          # Login (retorna JWT + perfil)
GET  /api/usuario/obter-dados/{id}    # Obter dados do usuário
GET  /api/usuario                     # Listar todos os usuários (ADMIN)
PATCH /api/usuario/atualizar-senha    # Atualizar senha do usuário

# Gestão de Perfis
GET  /api/perfil                      # Listar perfis disponíveis
```

### 👥 Gerenciamento de Pessoas

```http
# CRUD Básico
GET    /api/registro-pessoa          # Listar pessoas (filtrado por perfil)
POST   /api/registro-pessoa          # Criar nova pessoa
GET    /api/registro-pessoa/{id}     # Obter pessoa por ID
PUT    /api/registro-pessoa/{id}     # Atualizar pessoa
DELETE /api/registro-pessoa/{id}     # Deletar pessoa

# Buscas Específicas
GET    /api/registro-pessoa/cpf/{cpf}           # Buscar por CPF
GET    /api/registro-pessoa/telefone/{telefone} # Buscar por telefone
GET    /api/registro-pessoa/nome/{nome}         # Buscar por nome
GET    /api/registro-pessoa/existe/cpf/{cpf}    # Verificar se CPF existe
```

### 📊 Sistema de Logs

```http
GET /api/log-operacoes                # Logs de operações CRUD
GET /api/log-mensageria               # Logs de mensageria RabbitMQ
```

### 📋 Exemplos de Payloads

#### 🔐 Criar Usuário

```json
{
  "nome": "João da Silva",
  "email": "joao@example.com",
  "senha": "minhasenha123",
  "perfilId": "uuid-do-perfil"
}
```

#### 🔑 Login

```json
{
  "email": "joao@example.com",
  "senha": "minhasenha123"
}
```

#### 🔄 Response de Login

```json
{
  "id": "uuid-do-usuario",
  "nome": "João da Silva",
  "email": "joao@example.com",
  "dataHoraAcesso": "2024-01-15T10:30:00Z",
  "tokenAcesso": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "dataHoraExpiracao": "2024-01-16T10:30:00Z",
  "nomePerfil": "DEFAULT"
}
```

#### 🔒 Atualizar Senha

```json
{
  "email": "joao@example.com",
  "senha": "novasenha123"
}
```

### 🔒 Controle de Acesso por Perfil

#### 👑 Perfil ADMIN

- ✅ Visualiza **todas as pessoas** cadastradas no sistema
- ✅ Pode criar/editar/deletar qualquer registro
- ✅ Acesso a logs e métricas do sistema
- ✅ Gerenciamento de usuários

#### 👤 Perfil DEFAULT

- ✅ Visualiza **apenas pessoas cadastradas por ele**
- ✅ Pode criar/editar/deletar apenas próprios registros
- ❌ Acesso restrito a logs e usuários

### 🛡️ Headers de Autenticação

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
Content-Type: application/json
```

### 📋 Exemplo de Payload - Criar Pessoa

```json
{
  "nome": "João Silva Santos",
  "cpf": "123.456.789-00",
  "telefone": "(11) 98765-4321",
  "numero": "123",
  "complemento": "Apto 45",
  "cep": "01310-100",
  "bairro": "Bela Vista",
  "nomeMunicipio": "São Paulo",
  "nomeEstado": "São Paulo",
  "latitude": "-23.5505",
  "longitude": "-46.6333",
  "usuarioId": "uuid-do-usuario"
}
```

### 📋 Exemplo de Response - Pessoa

```json
{
  "id": "uuid-da-pessoa",
  "nome": "João Silva Santos",
  "cpf": "123.456.789-00",
  "telefone": "(11) 98765-4321",
  "numero": "123",
  "complemento": "Apto 45",
  "cep": "01310-100",
  "bairro": "Bela Vista",
  "nomeMunicipio": "São Paulo",
  "nomeEstado": "São Paulo",
  "latitude": "-23.5505",
  "longitude": "-46.6333",
  "usuarioId": "uuid-do-usuario",
  "usuario": {
    "id": "uuid-do-usuario",
    "nome": "Admin User",
    "email": "admin@example.com",
    "perfil": {
      "id": "uuid-do-perfil",
      "nome": "ADMIN"
    }
  }
}
```

## ⚙️ Variáveis de Ambiente

### 🔙 Backend (application.properties)

```properties
# Banco de Dados Principal (PostgreSQL)
spring.datasource.url=jdbc:postgresql://postgres:5432/bd_apipersonregistration
spring.datasource.username=admin
spring.datasource.password=root
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true

# JWT Configuration
jwt.secretkey=sua-chave-secreta-256-bits-aqui
jwt.expiration=86400000

# MongoDB (Logs)
spring.data.mongodb.uri=mongodb://mongodb-container:27017/log_apiusuarios

# RabbitMQ (Mensageria)
spring.rabbitmq.host=rabbitmq-container
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

# Redis (Cache)
spring.data.redis.host=redis
spring.data.redis.port=6379
spring.cache.redis.time-to-live=1800000

# Email (MailHog para desenvolvimento)
spring.mail.host=mailhog_container
spring.mail.port=1025
spring.mail.username=naoresponder@personreg.com
spring.mail.properties.mail.smtp.auth=false
spring.mail.properties.mail.smtp.starttls.enable=false

# Configurações da Aplicação
server.port=8080
spring.application.name=api-person-registration
management.endpoints.web.exposure.include=health,info
```

### 🎨 Frontend (.env)

```bash
# API Configuration
VITE_API_BASE_URL=http://localhost:8081

# Application Settings
VITE_APP_NAME="Sistema de Registro de Pessoas"
VITE_APP_VERSION="2.0.0"

# Environment
VITE_NODE_ENV=production

# Feature Flags (Desenvolvimento)
VITE_ENABLE_DEBUG=false
VITE_ENABLE_LOGS=true

# Auth Settings
VITE_JWT_EXPIRATION_HOURS=24
VITE_COOKIE_SECURE=true
```

### 🐳 Docker Environment Variables

#### Backend Container

```bash
# Database
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/bd_apipersonregistration
SPRING_DATASOURCE_USERNAME=admin
SPRING_DATASOURCE_PASSWORD=root

# External Services
SPRING_DATA_MONGODB_URI=mongodb://mongodb-container:27017/log_apiusuarios
SPRING_RABBITMQ_HOST=rabbitmq-container
SPRING_DATA_REDIS_HOST=redis
SPRING_MAIL_HOST=mailhog_container

# Security
JWT_SECRET_KEY=your-production-secret-key-here
JWT_EXPIRATION=86400000
```

#### Frontend Container

```bash
# Environment
NODE_ENV=production
PORT=3031

# Build-time variables (Docker build args)
VITE_API_BASE_URL=http://localhost:8081
VITE_NODE_ENV=production
```

## 🐳 Docker e Infraestrutura

### 🚀 Docker Compose - Serviços

O sistema utiliza Docker Compose para orquestrar todos os serviços necessários:

```yaml
# docker-compose.yaml
services:
  # 🗄️ Banco de Dados Principal
  postgres_api_person_registration:
    image: postgres:15-alpine
    ports: ["5432:5432"]
    environment:
      POSTGRES_DB: bd_apipersonregistration
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: root

  # 🍃 Banco de Logs
  mongodb-container:
    image: mongo:6
    ports: ["27017:27017"]

  # 🐰 Message Broker
  rabbitmq-container:
    image: rabbitmq:3.12-management-alpine
    ports: ["5672:5672", "15672:15672"]

  # ⚡ Cache
  redis:
    image: redis:7-alpine
    ports: ["6379:6379"]

  # 📧 Email Server (Development)
  mailhog_container:
    image: mailhog/mailhog:latest
    ports: ["1025:1025", "8025:8025"]

  # 🔙 Backend API
  api-person-registration:
    build: ./apiPersonRegistration
    ports: ["8081:8081"]
    depends_on: [postgres, mongodb, rabbitmq, redis, mailhog]

  # 🎨 Frontend Web
  web-person-registration:
    build: ./webPersonRegistration
    ports: ["3030:3030"]
    depends_on: [api-person-registration]
```

### 🔧 Comandos Docker Úteis

```bash
# 🚀 Usando imagens do Docker Hub (recomendado)
docker-compose -f docker-compose.hub.yml up -d
docker-compose -f docker-compose.hub.yml logs -f
docker-compose -f docker-compose.hub.yml down

# 🔨 Usando build local
docker-compose up -d --build
docker-compose logs -f api_person_registration
docker-compose logs -f web_person_registration

# 🏗️ Apenas infraestrutura (para desenvolvimento local)
docker-compose -f docker-compose.infra.yml up -d

# 📊 Monitoramento e debugging
docker-compose ps                    # Status dos containers
docker-compose top                   # Processos rodando
docker stats                        # Uso de recursos

# 🔍 Entrar em containers
docker-compose exec api_person_registration bash
docker-compose exec web_person_registration sh
docker-compose exec postgres psql -U admin -d bd_apipersonregistration

# 🧹 Limpeza
docker-compose down -v              # Para e remove volumes (CUIDADO: apaga dados)
docker system prune                 # Remove containers, networks, images não utilizadas
docker volume prune                 # Remove volumes não utilizados

# � Restart de serviços específicos
docker-compose restart api_person_registration
docker-compose restart web_person_registration
```

## 🔬 Desenvolvimento

### 🧪 Testes

#### Backend (Spring Boot)

```bash
# Executar todos os testes
mvn test

# Executar testes com coverage
mvn test jacoco:report

# Executar apenas testes de integração
mvn test -Dtest=**/*IT
```

#### Frontend (React - não implementado ainda)

```bash
# Executar testes unitários
npm test

# Executar testes com coverage
npm run test:coverage

# Executar testes e2e
npm run test:e2e
```

### 🔧 Desenvolvimento Local

#### 🔍 Debug e Monitoramento

```bash
# Swagger UI (Documentação da API)
open http://localhost:8081/swagger-ui.html

# MailHog (Visualizar emails enviados)
open http://localhost:8025

# RabbitMQ Management (Gerenciar filas)
open http://localhost:15672 # guest/guest

# PostgreSQL (Cliente de banco)
psql -h localhost -p 5432 -U admin -d bd_apipersonregistration

# MongoDB (Cliente de banco)
mongosh mongodb://localhost:27017/log_apiusuarios

# Redis (Cliente de cache)
redis-cli -h localhost -p 6379
```

### 📝 Padrões de Código

#### Backend (Java)

- **Clean Code**: Nomes descritivos e métodos pequenos
- **SOLID Principles**: Aplicação dos princípios SOLID
- **Spring Patterns**: Repository, Service, Controller layers
- **Exception Handling**: GlobalExceptionHandler para tratamento centralizado
- **DTOs**: Separação entre entidades e objetos de transferência
- **Validation**: Bean Validation (JSR 303) em DTOs

#### Frontend (TypeScript)

- **React Hooks**: Uso de hooks funcionais
- **TypeScript**: Tipagem forte em toda aplicação
- **Component Composition**: Componentes reutilizáveis e compostos
- **Custom Hooks**: Lógica compartilhada em hooks personalizados
- **Error Boundaries**: Tratamento de erros em componentes
- **Responsive Design**: Mobile-first com Material-UI

### 🎯 Convenções de Commit (não implentado ainda)

```bash
# Formato: tipo(escopo): descrição

feat(auth): implementar sistema de autenticação JWT
feat(api): adicionar endpoint de busca por CPF
feat(ui): adicionar filtros por perfil de usuário
fix(auth): corrigir validação de tokens expirados
fix(ui): corrigir responsividade do modal
docs(readme): atualizar documentação da API
style(components): ajustar responsividade do layout
refactor(service): otimizar consultas do repositório
test(controller): adicionar testes de autenticação
chore(docker): atualizar versão do PostgreSQL
```

### 🔐 Funcionalidades de Autenticação Implementadas

#### ✅ Sistema Completo de Login

- **Login seguro** com JWT e validação de credenciais
- **Armazenamento em cookies** HTTP-only seguros
- **Redirecionamento automático** pós-login
- **Validação em tempo real** dos campos

#### ✅ Cadastro de Usuários

- **Registro de novos usuários** com validação de email único
- **Criptografia de senhas** com BCrypt
- **Associação automática de perfis** (USER por padrão)
- **Feedback visual** de sucesso/erro

#### ✅ Recuperação de Senha

- **Endpoint PATCH** para atualização segura
- **Validação de email** antes da alteração
- **DTOs dedicados** (AtualizarSenhaRequest/Response)
- **Interface moderna** com Material-UI

#### ✅ Controle de Perfis e Permissões

- **Perfil ADMIN**: Acesso total a todos os registros
- **Perfil USER**: Acesso apenas aos próprios registros
- **Filtragem automática** no frontend baseada no perfil
- **Indicadores visuais** de restrições ativas

#### ✅ NavBar com Menu de Usuário

- **Exibição do nome** do usuário logado
- **Menu dropdown** com opções do usuário
- **Logout seguro** com limpeza de cookies
- **Design responsivo** e moderno

#### ✅ Proteção de Rotas

- **RouteGuards** para páginas protegidas
- **Redirecionamento automático** para login se não autenticado
- **Verificação de tokens** em cada navegação
- **Experiência fluida** de autenticação

## 🚀 Roadmap e _Futuras Features_

### 🎨 Frontend (React + TypeScript)

#### 🔄 Gerenciamento de Estado

- ✨ **Redux Toolkit**: Implementar Redux para gerenciamento global de estado complexo
- 🔗 **Context API**: Criar camadas de contexto para compartilhamento de dados simples entre componentes pai-filho
- 📊 **Estado Híbrido**: Combinar Redux (estado global) + Context (estado local/compartilhado)

#### 🛡️ Validação e Tratamento de Erros

- ✅ **Yup Schema Validation**: Implementar camada robusta de validação de formulários
- ⚠️ **Error Boundary Components**: Criar componentes especializados para tratamento de erros de API
- 🔄 **Retry Logic**: Implementar lógica de retry automático para requisições falhadas
- 📝 **Toast Notifications**: Sistema unificado de notificações de sucesso/erro _(Talvez: Em avaliação)_

#### 🏢 Módulo Empresarial

- 🏗️ **Serviços de Empresa**: Implementar services para CRUD de empresas
- 📋 **Serviços de Ordem de Serviço**: Criar services para gestão de ordens de serviço
- 📊 **Dashboard Administrativo**: Componente de acompanhamento de serviços para perfil ADMIN
- ⚡ **Gestão de Ordens**: Componentes para criação de ordens (perfil COORDENADOR+)
- 🏢 **Registro de Empresas**: Interface para cadastro empresarial (perfil ADMIN)

#### 🔧 Operações de Campo

- ▶️ **Início de Serviço**: Componente para inicialização de ordens de serviço
- 📝 **Registro de Atividades**: Interface para acompanhamento de progresso
- 📊 **Geração de Relatórios**: Módulo de relatórios detalhados e exportação
- 📱 **App Mobile (PWA)**: Versão mobile para técnicos de campo _(Talvez: Em avaliação)_

#### 🎨 Melhorias de UX/UI _(sugestões)_

- 🌓 **Tema Dark/Light**: Implementar alternância de temas
- 🔍 **Busca Global**: Componente de busca universal no sistema
- 📈 **Gráficos e Analytics**: Dashboard com métricas e KPIs visuais
- 🔔 **Notificações em Tempo Real**: WebSocket para notificações instantâneas

### 🔙 Backend (Spring Boot)

#### 🏢 Módulo Empresarial

- 🏗️ **Entidades e DTOs**: Criar estrutura completa para Empresa (relacionada com Usuario e Pessoa) e para OrdemServico (relacionada com Empresa)
- 📋 **Ordem de Serviço**: Implementar entidades e DTOs para OrdemServico e para Empresa
- 🎯 **Controllers REST**: Endpoints completos para CRUD empresarial
- 🔧 **Services**: Lógica de negócio para empresas e ordens de serviço
- 🗄️ **Repositories**: Camada de persistência otimizada

#### 🧪 Qualidade e Testes

- ✅ **Testes Unitários**: Cobertura completa para módulos Empresa e OrdemServico
- 🔗 **Testes de Integração**: Validação de fluxos end-to-end
- 📊 **Testes de Performance**: Benchmarks de carga e stress _(Talvez: Em avaliação)_

#### 📧 Comunicação e Notificações

- 📨 **Provedor de Email Real**: Integração com SendGrid, AWS SES ou similar
- ✅ **Validação de Usuario**: Campo `validado` + fluxo de confirmação por email
- 🔔 **Notificações Push**: Sistema de notificações em tempo real _(Talvez: Em avaliação)_
- 📱 **SMS Integration**: Notificações via SMS para casos críticos _(Talvez: Em avaliação)_

#### 🔐 Segurança e Auditoria _(sugestões)_

- 📋 **Auditoria Completa**: Log de todas as operações com timestamps
- 🔒 **Rate Limiting**: Proteção contra abuso de APIs
- 🛡️ **OWASP Compliance**: Implementar diretrizes de segurança OWASP
- 🔑 **OAuth2/OpenID**: Integração com provedores externos (Google, Microsoft)

### 🏗️ Infraestrutura e Gestão

#### 🌳 GitFlow e Versionamento

- 🌿 **GitFlow Strategy**: Implementar fluxo `master` → `qa` → `dev` → `feature-branches`
- 🏷️ **Semantic Versioning**: Versionamento automático baseado em commits convencionais
- 📝 **Conventional Commits**: Padronização de mensagens de commit
- 🔄 **Branch Protection**: Regras de proteção e revisão obrigatória

#### 📊 Gestão de Projeto

- 📋 **GitHub Projects**: Utilização de quadros Kanban para tracking
- 📈 **Diagrama de Gantt**: Planejamento temporal com cronogramas visuais
- 🎯 **Milestones**: Definição de marcos e entregas importantes
- 📊 **Métricas de Produtividade**: Dashboards de velocity e burndown _(Talvez: Em avaliação)_

#### 🚀 CI/CD e Deploy

- ⚙️ **GitHub Actions**: Pipeline completa de CI/CD com testes automatizados
- 🧪 **Quality Gates**: SonarQube para análise de código e cobertura
- 🐳 **Container Registry**: Azure Container Registry para imagens Docker
- 🌐 **Azure VM Deploy**: Deploy automatizado em máquinas virtuais Azure
- 📦 **Staging Environment**: Ambiente de homologação automático _(Talvez: Em avaliação)_

#### 🔍 Monitoramento e Observabilidade _(sugestões)_

- 📊 **Application Insights**: Monitoramento de performance e erros
- 📝 **Centralized Logging**: ELK Stack ou Azure Monitor
- 🚨 **Alerting**: Sistema de alertas proativos
- 📈 **Health Checks**: Monitoramento de saúde dos serviços

### 📋 Priorização (Pré-estabelecida)

#### 🚀 **Fase 1 - Fundação (Sprint 1-2)**

1. Implementar GitFlow e CI/CD básico
2. Criar camadas de validação (Yup) e tratamento de erros
3. Implementar validação de usuário por email

#### 🏢 **Fase 2 - Módulo Empresarial (Sprint 3-4)**

1. Desenvolver entidades e APIs para Empresa e OrdemServico
2. Criar interfaces de cadastro empresarial
3. Implementar testes automatizados

#### ⚡ **Fase 3 - Operações (Sprint 5-6)**

1. Desenvolver módulos de ordem de serviço
2. Criar componentes de acompanhamento e relatórios
3. Implementar provedor de email real

#### 🚀 **Fase 4 - Melhorias e Deploy (Sprint 7-8)**

1. Implementar Redux e Context API
2. Deploy em Azure com monitoramento
3. Otimizações de performance e UX

### 🎯 Métricas de Sucesso

- ✅ **Cobertura de Testes**: > 80% para backend, > 70% para frontend
- ⚡ **Performance**: Tempo de resposta < 2s para 95% das requisições
- 🔍 **Code Quality**: SonarQube Quality Gate "Passed"
- 🚀 **Deploy Frequency**: Deploy automático a cada merge na branch `qa`
- 📊 **MTTR**: Mean Time to Recovery < 30 minutos

---

## 🤝 Contribuição

### 🚀 Como Contribuir

1. **Fork** o projeto
2. **Clone** seu fork: `git clone git@github.com:jorgemeyrelles/personRegistration.git`
3. **Crie uma branch** para sua feature: `git checkout -b feature/nova-funcionalidade`
4. **Commit** suas mudanças: `git commit -m 'feat: adicionar nova funcionalidade'`
5. **Push** para a branch: `git push origin feature/nova-funcionalidade`
6. **Abra um Pull Request**

### 📋 Checklist para PRs

- [ ] ✅ Código segue os padrões estabelecidos
- [ ] 🧪 Testes unitários foram adicionados/atualizados
- [ ] 📖 Documentação foi atualizada se necessário
- [ ] 🔍 Code review interno foi realizado
- [ ] 🐳 Docker build funciona corretamente
- [ ] 📊 Swagger/OpenAPI foi atualizado (se API alterada)

### 🐛 Reportar Bugs

Ao reportar bugs, inclua:

- **OS e versão**: Sistema operacional usado
- **Passos para reproduzir**: Instruções detalhadas
- **Comportamento esperado**: O que deveria acontecer
- **Comportamento atual**: O que está acontecendo
- **Screenshots**: Se aplicável
- **Logs**: Logs relevantes do console/aplicação

### 💡 Sugestão de Features

Para sugerir novas funcionalidades:

- **Contexto**: Por que a feature é necessária
- **Descrição**: Descrição detalhada da funcionalidade
- **Casos de uso**: Exemplos de como seria utilizada
- **Impacto**: Benefícios esperados
- **Alternativas**: Outras formas de resolver o problema

## 📄 Licença

Este projeto está sob a licença **MIT**. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

### MIT License

```
Copyright (c) 2024 Sistema de Registro de Pessoas

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

## 🎉 Agradecimentos

- **React Team** pela biblioteca excepcional
- **Spring Boot Community** pelo framework robusto
- **Material-UI** pelos componentes elegantes
- **Docker** pela simplicidade de containerização
- **PostgreSQL** pela confiabilidade como banco de dados
- **Todos os contribuidores** que tornaram este projeto possível

---

## 📞 Suporte

### 📧 Contato

- **Email**: jotaengpuc@gmail.com
- **Issues**: [GitHub Issues](https://github.com/your-repo/issues) _(Não implementado ainda)_
- **Documentação**: [Wiki do Projeto](https://github.com/your-repo/wiki) _(Não implementado ainda)_

### 🔗 Links Úteis

- **Demo Online**: [https://personreg-demo.com](https://personreg-demo.com) _(Não implementado ainda)_
- **Documentação da API**: [https://api.personreg.com/swagger-ui.html](https://api.personreg.com/swagger-ui.html) _(Não implementado ainda)_
- **Guias de Desenvolvimento**: [https://docs.personreg.com](https://docs.personreg.com) _(Não implementado ainda)_

---

**⭐ Se este projeto foi útil para você, considere dar uma estrela no GitHub!**

**💻 Desenvolvido com ❤️ usando Spring Boot + React + TypeScript + Docker**
