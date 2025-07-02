# Diagnóstico e Correções - Problema POST /api/registro-pessoa

## 🔍 **Problemas Identificados**

### 1. **Validações Muito Restritivas**
- **Problema**: Campos `latitude`, `longitude` e `numero` eram obrigatórios (`@NotEmpty`)
- **Impacto**: Requisições POST falhavam quando esses campos não eram fornecidos
- **Solução**: Tornados opcionais removendo `@NotEmpty`

### 2. **Incompatibilidade de Tipos**
- **Problema**: Inconsistência entre tipos TypeScript e validações Java
- **Impacto**: Frontend enviava campos que backend rejeitava
- **Solução**: Alinhamento dos tipos entre frontend e backend

### 3. **Configuração de Banco de Dados**
- **Problema**: Campos obrigatórios no banco (`nullable = false`) para dados opcionais
- **Impacto**: Erro de constraint violation no banco de dados
- **Solução**: Ajustado para permitir valores nulos

### 4. **Tratamento de Exceções**
- **Problema**: Falta de handler específico para erros de validação
- **Impacto**: Mensagens de erro genéricas e pouco informativas
- **Solução**: Criado `GlobalExceptionHandler` com mensagens específicas

## ✅ **Correções Implementadas**

### 1. **Backend - DTO Request** (`CriarRegistroPessoaRequest.java`)
```java
// ANTES: Obrigatórios
@NotEmpty(message = "Por favor, informe a latitude.")
@NotEmpty(message = "Por favor, informe a longitude.")
@NotEmpty(message = "Por favor, informe o número do endereço.")

// DEPOIS: Opcionais
@Size(max = 20, message = "A latitude deve ter no máximo 20 caracteres.")
@Size(max = 20, message = "A longitude deve ter no máximo 20 caracteres.")
@Size(max = 10, message = "O número deve ter no máximo 10 caracteres.")
```

### 2. **Backend - Entidade** (`RegistroPessoa.java`)
```java
// ANTES: Campos obrigatórios
@Column(name = "lat", nullable = false, length = 60)
@Column(name = "longitude", nullable = false, length = 60)
@Column(nullable = false, length = 10)

// DEPOIS: Campos opcionais
@Column(name = "lat", length = 20)
@Column(name = "longitude", length = 20)
@Column(length = 10)
```

### 3. **Frontend - Tipos TypeScript** (`registroPessoasTypes.ts`)
```typescript
// ANTES: Todos obrigatórios
export interface RegistroPessoaRequest {
  numero: string;
  complemento: string;
  latitude: string;
  longitude: string;
}

// DEPOIS: Campos opcionais
export interface RegistroPessoaRequest {
  numero?: string;
  complemento?: string;
  latitude?: string;
  longitude?: string;
}
```

### 4. **Frontend - Envio de Dados** (`CriarRegistroPessoaModal.tsx`)
```typescript
// ANTES: Enviava todos os campos
const dadosParaEnvio: RegistroPessoaRequest = {
  ...formData,
  cpf: formData.cpf.replace(/\D/g, ""),
};

// DEPOIS: Envia apenas campos com valor
const dadosParaEnvio: RegistroPessoaRequest = {
  nome: formData.nome,
  telefone: formData.telefone,
  cpf: formData.cpf.replace(/\D/g, ""),
  // ... campos obrigatórios
  // Campos opcionais - apenas incluir se tiverem valor
  ...(formData.numero && formData.numero.trim() && { numero: formData.numero }),
  ...(formData.complemento && formData.complemento.trim() && { complemento: formData.complemento }),
  ...(formData.latitude && formData.latitude.trim() && { latitude: formData.latitude }),
  ...(formData.longitude && formData.longitude.trim() && { longitude: formData.longitude }),
};
```

### 5. **Backend - Handler de Exceções** (`GlobalExceptionHandler.java`)
```java
// Novo arquivo criado para melhor tratamento de erros
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(...) {
        // Retorna erros de validação estruturados
    }
}
```

## 🧪 **Como Testar**

### 1. **Teste com Dados Mínimos**
```bash
curl -X POST http://localhost:3030/api/registro-pessoa \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "telefone": "(11) 99999-8888",
    "cpf": "12345678901",
    "cep": "01234567",
    "bairro": "Centro",
    "nomeMunicipio": "São Paulo",
    "nomeEstado": "SP",
    "usuarioId": "1e2b1cec-1b92-4492-815d-07f2d15c9fcf"
  }'
```

### 2. **Teste com Dados Completos**
```bash
curl -X POST http://localhost:3030/api/registro-pessoa \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Maria Santos",
    "telefone": "(21) 88888-7777",
    "cpf": "98765432100",
    "numero": "123",
    "complemento": "Apto 45",
    "cep": "01234567",
    "bairro": "Copacabana",
    "nomeMunicipio": "Rio de Janeiro",
    "nomeEstado": "RJ",
    "latitude": "-22.9068",
    "longitude": "-43.1729",
    "usuarioId": "1e2b1cec-1b92-4492-815d-07f2d15c9fcf"
  }'
```

## 🚀 **Próximos Passos**

1. **Rebuild das imagens Docker** se estiver usando containers
2. **Teste dos endpoints** com as correções
3. **Verificação dos logs** para confirmar que os erros foram resolvidos
4. **Teste da interface** para validar a experiência do usuário

## 📝 **Campos Obrigatórios vs Opcionais**

### ✅ **Obrigatórios**
- `nome` - Nome da pessoa
- `telefone` - Telefone para contato  
- `cpf` - Documento de identificação
- `cep` - CEP do endereço
- `bairro` - Bairro do endereço
- `nomeMunicipio` - Município
- `nomeEstado` - Estado
- `usuarioId` - ID do usuário responsável

### 📍 **Opcionais**
- `numero` - Número do endereço (nem sempre disponível)
- `complemento` - Complemento do endereço
- `latitude` - Coordenada geográfica
- `longitude` - Coordenada geográfica

Essas correções devem resolver o problema do método POST no endpoint `/api/registro-pessoa`.
