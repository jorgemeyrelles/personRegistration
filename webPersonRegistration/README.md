# Sistema de Registro de Pessoas

Sistema web moderno para gerenciamento de registros de pessoas, desenvolvido com React, TypeScript e Material-UI. O sistema oferece funcionalidades completas de CRUD, visualização de logs e interface responsiva.

## 🚀 Tecnologias Utilizadas

- **Frontend**: React 18 + TypeScript
- **Build Tool**: Vite
- **UI Framework**: Material-UI (MUI) v5
- **Roteamento**: React Router v6
- **Grid System**: MUI System v2
- **Data Grid**: MUI X Data Grid
- **Ícones**: Material Icons
- **Estilo**: CSS-in-JS (Emotion)

## 📋 Funcionalidades

### 🧑‍💼 Gerenciamento de Pessoas

- ✅ **CRUD Completo**: Criar, visualizar, editar e deletar registros
- ✅ **Busca Inteligente**: Filtros por nome e estado em tempo real
- ✅ **Interface Responsiva**: Cards compactos com design moderno
- ✅ **Validação de Dados**: Formulários com validação de CPF, CEP e telefone
- ✅ **Integração ViaCEP**: Preenchimento automático de endereço

### 📊 Sistema de Logs

- ✅ **Logs Unificados**: Visualização de logs de operações e mensageria
- ✅ **DataGrid Avançado**: Tabela com paginação, ordenação e filtros
- ✅ **Categorização Visual**: Chips com ícones para diferentes tipos de log
- ✅ **Busca e Filtros**: Sistema de filtros integrado ao DataGrid

### 🔐 Sistema de Autenticação

- ✅ **Cookies Seguros**: Armazenamento seguro com proteções CSRF e XSS
- ✅ **Hook Personalizado**: `useAuth` para gerenciamento reativo de estado
- ✅ **Login Responsivo**: Interface Material-UI com validação
- ✅ **Segurança Avançada**: SameSite, Secure, expiração automática
- ✅ **Migração Documentada**: De localStorage para cookies seguros

### 🎨 Interface do Usuário

- ✅ **Design System**: Cores e componentes consistentes
- ✅ **Navbar Responsiva**: Navegação intuitiva com links diretos
- ✅ **Feedback Visual**: Loading states, alertas e confirmações
- ✅ **Acessibilidade**: Labels e navegação por teclado

## 🏗️ Arquitetura do Sistema

```
┌─────────────────────────────────────────────────────────────┐
│                    APRESENTAÇÃO (UI)                        │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐          │
│  │    Home     │  │    Logs     │  │  NotFound   │          │
│  │   (Page)    │  │   (Page)    │  │   (Page)    │          │
│  └─────────────┘  └─────────────┘  └─────────────┘          │
│                                                             │
│  ┌────────────────────────────────────────────────────────┐ │
│  │              COMPONENTES GLOBAIS                       │ │
│  │  ┌─────────┐  ┌─────────┐  ┌─────────┐  ┌─────────┐    │ │
│  │  │ NavBar  │  │  Body   │  │ Footer  │  │ Modals  │    │ │
│  │  └─────────┘  └─────────┘  └─────────┘  └─────────┘    │ │
│  └────────────────────────────────────────────────────────┘ │
│                                                             │
│  ┌─────────────────────────────────────────────────────────┐│
│  │            COMPONENTES ESPECÍFICOS                      ││
│  │  ┌──────────────────┐  ┌──────────────────┐             ││
│  │  │ GerenciarPessoas │  │  GerenciarLogs   │             ││
│  │  │                  │  │                  │             ││
│  │  │ • Cards Grid     │  │ • DataGrid       │             ││
│  │  │ • Filtros        │  │ • Logs Unificados│             ││
│  │  │ • CRUD Modals    │  │ • Paginação      │             ││
│  │  └──────────────────┘  └──────────────────┘             ││
│  └─────────────────────────────────────────────────────────┘│
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                     ROTEAMENTO                              │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  React Router v6 (BrowserRouter)                            │
│                                                             │
│  Routes:                                                    │
│  • "/" → Home (GerenciarPessoas)                            │
│  • "/logs" → Logs (GerenciarLogs)                           │
│  • "*" → NotFound                                           │
│                                                             │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                   CAMADA DE SERVIÇOS                        │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌───────────────────┐  ┌───────────────────┐               │
│  │  registroPessoas/ │  │      logs/        │               │
│  │                   │  │                   │               │
│  │ • CRUD Services   │  │ • Logs Operações  │               │
│  │ • API Integration │  │ • Logs Mensageria │               │
│  │ • Error Handling  │  │ • Filtros         │               │
│  └───────────────────┘  └───────────────────┘               │
│                                                             │
│  ┌─────────────────────────────────────────────────────────┐│
│  │              INTEGRAÇÕES EXTERNAS                       ││
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐      ││
│  │  │   ViaCEP    │  │  Backend    │  │   Storage   │      ││
│  │  │   (CEP)     │  │    API      │  │  (Session)  │      ││
│  │  └─────────────┘  └─────────────┘  └─────────────┘      ││
│  └─────────────────────────────────────────────────────────┘│
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                    TIPOS E UTILITÁRIOS                      │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌───────────────────┐  ┌───────────────────┐               │
│  │      types/       │  │      utils/       │               │
│  │                   │  │                   │               │
│  │ • registroPessoas │  │ • colors          │               │
│  │ • logs            │  │ • validations     │               │
│  │ • common          │  │ • formatters      │               │
│  └───────────────────┘  └───────────────────┘               │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

## 📁 Estrutura de Diretórios

```
src/
├── components/           # Componentes reutilizáveis
│   ├── Body.tsx         # Layout principal
│   ├── Footer.tsx       # Rodapé da aplicação
│   ├── NavBar.tsx       # Barra de navegação
│   ├── GerenciarPessoas.tsx     # Gerenciamento de pessoas
│   ├── GerenciarLogs.tsx        # Visualização de logs
│   ├── CriarRegistroPessoaModal.tsx   # Modal de CRUD
│   └── DeletarRegistroPessoaModal.tsx # Modal de deleção
│
├── pages/               # Páginas da aplicação
│   ├── Home.tsx        # Página inicial
│   ├── Logs.tsx        # Página de logs
│   └── NotFound.tsx    # Página 404
│
├── routes/              # Configuração de rotas
│   └── Routes.tsx      # Definição das rotas
│
├── service/             # Camada de serviços
│   ├── registroPessoas/ # Serviços de pessoas
│   ├── logs/           # Serviços de logs
│   └── exemplos-*.ts   # Exemplos de uso
│
├── types/               # Definições de tipos
│   ├── registroPessoasTypes.ts
│   ├── logs.ts
│   └── common.ts
│
├── utils/               # Utilitários
│   └── colors.ts       # Sistema de cores
│
├── App.tsx             # Componente raiz
└── main.tsx           # Ponto de entrada
```

## 🎯 Fluxo de Dados

### Gerenciamento de Estado

```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│   Component │───▶│   Service   │───▶│   Backend   │
│    State    │    │    Layer    │    │     API     │
└─────────────┘    └─────────────┘    └─────────────┘
       ▲                   │                   │
       │                   ▼                   │
       │            ┌─────────────┐            │
       └────────────│   Response  │◀───────────┘
                    │  Processing │
                    └─────────────┘
```

### Filtros e Busca

```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│   Input     │───▶│   Filter    │───▶│  Filtered   │
│   Change    │    │   Logic     │    │   Results   │
└─────────────┘    └─────────────┘    └─────────────┘
       ▲                   │                   │
       │                   ▼                   │
       │            ┌─────────────┐            │
       └────────────│   useEffect │◀───────────┘
                    │   Trigger   │
                    └─────────────┘
```

## 🚀 Como Executar

### Pré-requisitos

- Node.js 18+
- npm ou yarn
- Docker (opcional)

### Instalação Local

```bash
# Clone o repositório
git clone <repository-url>

# Entre no diretório
cd webPersonRegistration

# Instale as dependências
npm install

# Execute em modo desenvolvimento
npm run dev

# Build para produção
npm run build
```

### 🐳 Execução com Docker

#### Produção (Nginx)

```bash
# Build da imagem
npm run docker:build

# Executar container
npm run docker:run

# Ou usar docker-compose
npm run docker:compose
```

#### Desenvolvimento

```bash
# Build da imagem de desenvolvimento
npm run docker:build-dev

# Executar em modo desenvolvimento
npm run docker:compose-dev
```

#### Docker Compose Completo

```bash
# Subir todos os serviços
docker-compose up --build

# Apenas produção
docker-compose up web-person-registration

# Apenas desenvolvimento
docker-compose --profile dev up
```

### Scripts Disponíveis

```bash
# Desenvolvimento local
npm run dev          # Servidor de desenvolvimento
npm run build        # Build de produção
npm run preview      # Preview do build
npm run lint         # Linting do código

# Docker
npm run docker:build       # Build imagem produção
npm run docker:build-dev   # Build imagem desenvolvimento
npm run docker:run         # Executar container produção
npm run docker:run-dev     # Executar container desenvolvimento
npm run docker:compose     # Docker compose produção
npm run docker:compose-dev # Docker compose desenvolvimento
npm run docker:stop        # Parar containers
```

## 🔧 Configuração

### Variáveis de Ambiente

```env
VITE_API_BASE_URL=http://localhost:8081
```

### 🐳 Configuração Docker

#### Estrutura de Arquivos Docker

```
├── Dockerfile              # Produção (multi-stage build)
├── Dockerfile.dev          # Desenvolvimento
├── docker-compose.yml      # Orquestração de containers
├── nginx.conf              # Configuração do Nginx
└── .dockerignore           # Arquivos ignorados no build
```

#### Características do Container de Produção

- **Base**: nginx:alpine (pequeno e seguro)
- **Multi-stage build**: Otimizado para produção
- **Health check**: Monitoramento automático
- **Compressão**: Gzip habilitado
- **Cache**: Assets estáticos com cache de 1 ano
- **Segurança**: Headers de segurança configurados
- **SPA Support**: Fallback para index.html

#### Portas

- **Produção**: 3030:3030 (nginx)
- **Desenvolvimento**: 5173:5173 (vite dev server)

#### Volumes (Desenvolvimento)

- Código fonte: Hot reload ativado
- node_modules: Volume dedicado para performance

### Backend API

O sistema integra com uma API backend que deve fornecer os seguintes endpoints:

```
# Pessoas
GET    /api/registro-pessoas/buscar-todos
POST   /api/registro-pessoas/criar
PUT    /api/registro-pessoas/atualizar/{id}
DELETE /api/registro-pessoas/deletar/{id}

# Logs
GET    /api/log-operacoes/buscar-todos
GET    /api/log-mensageria/buscar-todos

# Integração Externa
GET    https://viacep.com.br/ws/{cep}/json/
```

## 🎨 Design System

### Cores Principais

- **Primary**: Azul (#1976d2)
- **Secondary**: Roxo (#9c27b0)
- **Success**: Verde (#2e7d32)
- **Error**: Vermelho (#d32f2f)
- **Warning**: Laranja (#ed6c02)

### Componentes

- **Cards**: Bordas arredondadas (borderRadius: 3)
- **Buttons**: Material Design padrão
- **Typography**: Roboto font family
- **Spacing**: Sistema de 8px base

## 📱 Responsividade

### Breakpoints

- **xs**: 0px (mobile)
- **sm**: 600px (tablet)
- **md**: 900px (desktop pequeno)
- **lg**: 1200px (desktop grande)

### Grid Layout

- **Mobile**: 1 coluna
- **Tablet**: 2 colunas
- **Desktop**: 3-4 colunas

## 🧪 Funcionalidades Avançadas

### Filtros Inteligentes

- Busca em tempo real por nome
- Filtro por estado com lista dinâmica
- Contadores de resultados
- Reset de filtros

### DataGrid Personalizado

- Paginação automática
- Ordenação por colunas
- Localização em português
- Renderização customizada de células

### Validações

- CPF com máscara e validação
- CEP com integração ViaCEP
- Telefone formatado
- Campos obrigatórios

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

## 📚 Documentação Adicional

### 🔐 Sistema de Autenticação

- **[Documentação Completa](docs/AUTENTICACAO_COOKIES.md)** - Guia detalhado sobre cookies seguros
- **[Exemplos Práticos](src/examples/README.md)** - Implementações e comparações
- **[Hook useAuth](src/hooks/useAuth.ts)** - Hook personalizado para autenticação
- **[Utilitários de Cookies](src/utils/cookies.ts)** - Funções de baixo nível

### 🧪 Exemplos de Uso

- **`ExemploUsoLogin.tsx`** - Migração de localStorage para cookies
- **`ExemploUseAuth.tsx`** - Hook completo com dashboard
- **`ComparacaoArmazenamento.tsx`** - Comparação interativa de tecnologias

### 🔧 Migração

Se você está usando localStorage, veja o guia de migração em:

- `src/examples/ExemploUsoLogin.tsx` (antes e depois)
- `docs/AUTENTICACAO_COOKIES.md` (documentação completa)

---

**Desenvolvido com ❤️ usando React + TypeScript + Material-UI**

### Autenticação com Cookies

```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│  Login Form │───▶│  useAuth    │───▶│   Cookies   │
│  (Component)│    │   Hook      │    │   Seguros   │
└─────────────┘    └─────────────┘    └─────────────┘
       ▲                   │                   │
       │                   ▼                   │
       │            ┌─────────────┐            │
       └────────────│   Estado    │◀───────────┘
                    │  Reativo    │
                    └─────────────┘
```

**Funcionalidades de Segurança:**

- 🔒 **SameSite=Strict**: Proteção contra CSRF
- 🛡️ **Secure Flag**: HTTPS obrigatório em produção
- ⏰ **Expiração Automática**: Configurável (padrão: 24h)
- 🧹 **Limpeza Automática**: Remove cookies expirados
- 📱 **Desenvolvimento**: Funciona em localhost HTTP

**Hook useAuth:**

```tsx
const { isAuthenticated, usuario, token, login, logout } = useAuth();
```
