# 🚀 Sistema de Registro de Pessoas - Full Stack

Sistema completo de gerenciamento de registros de pessoas, desenvolvido com **Spring Boot** (backend) e **React + TypeScript** (frontend), utilizando **Docker** para containerização e **PostgreSQL** como banco de dados principal.

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
- **MUI X Data Grid** - Grid avançado para tabelas
- **React Router v6** - Roteamento SPA
- **Emotion** - CSS-in-JS para estilização
- **Docker + Nginx** - Containerização e proxy reverso
- **MUI X Data Grid** - Tabelas avançadas
- **Docker** - Containerização
- **Nginx** - Servidor web de produção

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
│  │   Home      │  │    Logs     │  │  NotFound   │              │
│  │  (CRUD)     │  │ (DataGrid)  │  │   (404)     │              │
│  └─────────────┘  └─────────────┘  └─────────────┘              │
│                                                                 │
│  ┌─────────────────────────────────────────────────────────────┐│
│  │              COMPONENTES & SERVIÇOS                        ││
│  │ • NavBar • GerenciarPessoas • GerenciarLogs • Modals       ││
│  │ • API Services • Types • Utils • Routing                   ││
│  └─────────────────────────────────────────────────────────────┘│
└─────────────────────────────────────────────────────────────────┘
                                    │ HTTP/REST API
                                    ▼
┌─────────────────────────────────────────────────────────────────┐
│                      NGINX (Proxy Reverso)                      │
│                     • Frontend: Port 3030                       │
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
│  │  Security • JWT • Validations • Exception Handling         ││
│  │  RabbitMQ Messaging • Email Service • Cache                ││
│  └─────────────────────────────────────────────────────────────┘│
└─────────────────────────────────────────────────────────────────┘
                            │         │         │
                    ┌───────▼─┐   ┌───▼───┐   ┌─▼────┐
                    │PostgreSQL│   │MongoDB│   │Redis │
                    │(Main DB) │   │(Logs) │   │(Cache)│
                    └─────────┘   └───────┘   └──────┘
```

### 🔄 Fluxo de Dados

```
1. 👤 Usuário interage com Frontend React
2. 🌐 Nginx roteia requisições para Backend
3. 🔐 Spring Security valida JWT tokens
4. 📊 Controllers processam requisições REST
5. 🔧 Services executam lógica de negócio
6. 💾 Repositories persistem dados no PostgreSQL
7. 📝 Logs são enviados assincronamente para MongoDB
8. 📧 Notificações são enviadas via RabbitMQ/MailHog
9. ⚡ Cache Redis otimiza consultas frequentes
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

### 💻 Execução Local

#### 🔙 Backend (Spring Boot)

```bash
# 1. Clone o repositório
git clone <repository-url>
cd personProject/apiPersonRegistration

# 2. Configure as variáveis de ambiente
cp src/main/resources/application.properties.example src/main/resources/application.properties

# 3. Ajuste as configurações de banco no application.properties
# spring.datasource.url=jdbc:postgresql://localhost:5432/bd_apipersonregistration
# spring.data.mongodb.uri=mongodb://localhost:27017/log_apiusuarios
# spring.rabbitmq.host=localhost
# spring.data.redis.host=localhost

# 4. Instale as dependências e compile
mvn clean install

# 5. Execute a aplicação
mvn spring-boot:run

# API estará disponível em: http://localhost:8081
# Swagger UI: http://localhost:8081/swagger-ui.html
```

#### 🎨 Frontend (React)

```bash
# 1. Entre no diretório do frontend
cd ../webPersonRegistration

# 2. Configure as variáveis de ambiente
cp .env.example .env

# 3. Ajuste a URL da API no .env
# VITE_API_BASE_URL=http://localhost:8081

# 4. Instale as dependências
npm install

# 5. Execute em modo desenvolvimento
npm run dev

# Frontend estará disponível em: http://localhost:5173
```

### 🐳 Execução com Docker

#### 🚀 Execução Rápida (Recomendado)

```bash
# 1. Clone o repositório
git clone <repository-url>
cd personProject

# 2. Execute todo o ambiente
docker-compose up -d

# 3. Aguarde a inicialização (pode levar alguns minutos na primeira vez)
docker-compose logs -f api-person-registration
```

#### 🔧 Desenvolvimento com Docker

```bash
# Para desenvolvimento do frontend (hot reload)
docker-compose --profile dev up

# Para rebuild das imagens
docker-compose up --build

# Para visualizar logs
docker-compose logs -f

# Para parar todos os serviços
docker-compose down
```

### 🌐 URLs de Acesso

| Serviço            | URL                                     | Descrição              |
| ------------------ | --------------------------------------- | ---------------------- |
| 🎨 **Frontend**    | `http://localhost:3030`                 | Interface React        |
| 🔙 **API Backend** | `http://localhost:8081`                 | API Spring Boot        |
| 📖 **Swagger UI**  | `http://localhost:8081/swagger-ui.html` | Documentação da API    |
| 📧 **MailHog UI**  | `http://localhost:8025`                 | Interface de emails    |
| 🐰 **RabbitMQ UI** | `http://localhost:15672`                | Gerenciamento de filas |
| 🗄️ **PostgreSQL**  | `localhost:5432`                        | Banco principal        |
| 🍃 **MongoDB**     | `localhost:27017`                       | Banco de logs          |
| ⚡ **Redis**       | `localhost:6379`                        | Cache                  |

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
        │   ├── NavBar.tsx               # Barra de navegação
        │   ├── GerenciarPessoas.tsx     # CRUD de pessoas
        │   ├── GerenciarLogs.tsx        # Visualização de logs
        │   ├── CriarRegistroPessoaModal.tsx    # Modal de criação/edição
        │   └── DeletarRegistroPessoaModal.tsx  # Modal de confirmação
        ├── 📄 pages/                     # Páginas da aplicação
        │   ├── Home.tsx                 # Página inicial
        │   ├── Logs.tsx                 # Página de logs
        │   └── NotFound.tsx             # Página 404
        ├── 🔗 routes/                    # Configuração de rotas
        ├── 🔧 service/                   # Serviços e API calls
        │   ├── registroPessoas/         # Serviços de pessoas
        │   └── exemplos/                # Exemplos de uso
        ├── 📝 types/                     # Definições TypeScript
        ├── 🎨 utils/                     # Utilitários e helpers
        └── ⚙️ config/                    # Configurações da aplicação
```

## 🌐 API Endpoints

### 🔐 Autenticação

```http
POST /api/usuario/criar               # Criar usuário
POST /api/usuario/autenticar          # Login (retorna JWT)
GET  /api/usuario/obter-dados/{id}    # Obter dados do usuário
```

### 👥 Gerenciamento de Pessoas

```http
# CRUD Básico
GET    /api/registro-pessoa           # Listar todas as pessoas
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
GET /api/log-operacoes                # Logs de operações
GET /api/log-mensageria               # Logs de mensageria
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
# Banco de Dados Principal
spring.datasource.url=jdbc:postgresql://postgres_api_person_registration:5432/bd_apipersonregistration
spring.datasource.username=admin
spring.datasource.password=root

# JWT Configuration
jwt.secretkey=your-secret-key-here
jwt.expiration=3600000

# MongoDB (Logs)
spring.data.mongodb.uri=mongodb://mongodb-container:27017/log_apiusuarios

# RabbitMQ
spring.rabbitmq.host=rabbitmq-container
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

# Redis Cache
spring.data.redis.host=redis
spring.data.redis.port=6379
spring.cache.redis.time-to-live=1800000

# Email (MailHog)
spring.mail.host=mailhog_container
spring.mail.port=1025
spring.mail.username=naoresponder@personreg.com
```

### 🎨 Frontend (.env)

```bash
# API Configuration
VITE_API_BASE_URL=http://localhost:8081

# Application Settings
VITE_APP_NAME="Sistema de Registro de Pessoas"
VITE_APP_VERSION="1.0.0"

# Feature Flags
VITE_ENABLE_DEBUG=true
VITE_ENABLE_LOGS=true
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
# 🚀 Iniciar todo o ambiente
docker-compose up -d

# 📊 Visualizar logs de um serviço específico
docker-compose logs -f api-person-registration
docker-compose logs -f web-person-registration

# 🔄 Rebuild de imagens
docker-compose up --build

# ⏹️ Parar todos os serviços
docker-compose down

# 🧹 Limpar volumes (dados serão perdidos)
docker-compose down -v

# 📋 Status dos containers
docker-compose ps

# 🔍 Entrar em um container
docker-compose exec api-person-registration bash
docker-compose exec web-person-registration sh
```

### 🏗️ Build e Deploy

```bash
# Build manual das imagens
cd apiPersonRegistration
docker build -t api-person-registration .

cd ../webPersonRegistration
docker build -t web-person-registration .

# Push para registry (opcional)
docker tag api-person-registration your-registry/api-person-registration:latest
docker push your-registry/api-person-registration:latest

docker tag web-person-registration your-registry/web-person-registration:latest
docker push your-registry/web-person-registration:latest
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

#### Frontend (React)

```bash
# Executar testes unitários
npm test

# Executar testes com coverage
npm run test:coverage

# Executar testes e2e
npm run test:e2e
```

### 🔧 Desenvolvimento Local

#### Hot Reload e Debugging

```bash
# Backend - Dev mode com hot reload
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"

# Frontend - Dev mode com hot reload
npm run dev

# Frontend com Docker (desenvolvimento)
npm run docker:run-dev
```

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

### 🎯 Convenções de Commit

```bash
# Formato: tipo(escopo): descrição

feat(api): adicionar endpoint de busca por CPF
fix(ui): corrigir validação de formulário
docs(readme): atualizar documentação da API
style(components): ajustar responsividade do modal
refactor(service): otimizar consultas do repositório
test(controller): adicionar testes unitários
chore(docker): atualizar versão do PostgreSQL
```

## 🤝 Contribuição

### 🚀 Como Contribuir

1. **Fork** o projeto
2. **Clone** seu fork: `git clone <your-fork-url>`
3. **Crie uma branch** para sua feature: `git checkout -b feature/nova-funcionalidade`
4. **Commit** suas mudanças: `git commit -m 'feat: adicionar nova funcionalidade'`
5. **Push** para a branch: `git push origin feature/nova-funcionalidade`
6. \*\*Abra um Pull Request`

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

- **Email**: suporte@personreg.com
- **Issues**: [GitHub Issues](https://github.com/your-repo/issues)
- **Documentação**: [Wiki do Projeto](https://github.com/your-repo/wiki)

### 🔗 Links Úteis

- **Demo Online**: [https://personreg-demo.com](https://personreg-demo.com)
- **Documentação da API**: [https://api.personreg.com/swagger-ui.html](https://api.personreg.com/swagger-ui.html)
- **Guias de Desenvolvimento**: [https://docs.personreg.com](https://docs.personreg.com)

---

**⭐ Se este projeto foi útil para você, considere dar uma estrela no GitHub!**

**💻 Desenvolvido com ❤️ usando Spring Boot + React + TypeScript + Docker**
