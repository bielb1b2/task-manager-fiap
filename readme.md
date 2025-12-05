# 🚀 Task Manager - Starfield Edition

<div align="center">

![Task Manager](https://img.shields.io/badge/Task-Manager-blue?style=for-the-badge)
![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-green?style=for-the-badge&logo=spring)
![React](https://img.shields.io/badge/React-19.2.0-blue?style=for-the-badge&logo=react)
![Redis](https://img.shields.io/badge/Redis-Latest-red?style=for-the-badge&logo=redis)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?style=for-the-badge&logo=docker)

**Um gerenciador de tarefas moderno, rápido e sem distrações**

[Features](#-features) • [Stack](#-tech-stack) • [Getting Started](#-getting-started) • [API](#-api-documentation) • [Architecture](#-architecture)

</div>

---

## 📋 Sobre o Projeto

O **Task Manager** é uma aplicação completa de gerenciamento de tarefas desenvolvida com foco em:

- ✨ **Simplicidade**: Interface limpa e intuitiva
- ⚡ **Performance**: Backend otimizado com Redis e processamento assíncrono
- 🎯 **Funcionalidade**: Sem frescuras, apenas o essencial
- 🌌 **Design Moderno**: Interface com efeito starfield e animações suaves
- 🔄 **Tempo Real**: Atualizações instantâneas com React Query

### 🎯 Propósito

Resolver um problema real: gerenciar tarefas de forma eficiente, sem anúncios, sem freemium, sem distrações. Apenas você e suas tarefas.

---

## ✨ Features

### 🎨 Frontend
- ✅ Criação, edição e exclusão de tarefas
- ✅ Visualização de todas as tarefas por usuário
- ✅ Marcação de tarefas como concluídas
- ✅ Animação de fundo estrelado (Starfield)
- ✅ Interface responsiva e moderna
- ✅ Toast notifications para feedback visual
- ✅ Persistência de usuário no localStorage
- ✅ Loading states e error handling

### 🔧 Backend
- ✅ API RESTful completa
- ✅ Persistência em Redis com AOF
- ✅ Mensageria com RabbitMQ
- ✅ Processamento assíncrono de eventos
- ✅ Estatísticas de tarefas em PostgreSQL
- ✅ Documentação Swagger/OpenAPI
- ✅ Validações robustas
- ✅ Tratamento global de exceções

### 🏗️ Infraestrutura
- ✅ Containerização completa com Docker
- ✅ Orquestração com Docker Compose
- ✅ Análise de código com SonarQube
- ✅ CI/CD com Jenkins
- ✅ Health checks e auto-restart

---

## 🛠️ Tech Stack

### Backend
```
├── Java 21
├── Spring Boot 3.5.7
│   ├── Spring Web
│   ├── Spring AMQP (RabbitMQ)
│   └── Spring JDBC
├── Redis (Lettuce)
├── PostgreSQL
├── RabbitMQ
├── Swagger/OpenAPI
└── JaCoCo (Code Coverage)
```

### Frontend
```
├── React 19.2.0
├── TypeScript 5.9.3
├── Vite 7.2.4
├── Tailwind CSS 4.1.17
├── React Query (TanStack)
├── Zustand (State Management)
├── Radix UI (Components)
├── Ky (HTTP Client)
└── React Hot Toast
```

### DevOps & Tools
```
├── Docker & Docker Compose
├── Jenkins
├── SonarQube
├── RedisInsight
├── Maven
└── Git
```

---

## 🚀 Getting Started

### Pré-requisitos

- **Docker** >= 20.10
- **Docker Compose** >= 2.0
- **Git**

### Instalação e Execução

1. **Clone o repositório**
```bash
git clone https://github.com/your-username/task-manager.git
cd task-manager
```

2. **Inicie todos os serviços**
```bash
docker compose up -d
```

3. **Aguarde os serviços iniciarem** (pode levar alguns minutos na primeira vez)

4. **Acesse as aplicações**

| Serviço | URL | Descrição |
|---------|-----|-----------|
| 🎨 Frontend | http://localhost:3000 | Interface do usuário |
| 🔧 Backend API | http://localhost:8080 | API REST |
| 📚 Swagger | http://localhost:8080/swagger-ui.html | Documentação da API |
| 🔍 RedisInsight | http://localhost:5540 | Interface do Redis |
| 📊 SonarQube | http://localhost:9000 | Análise de código |
| 🐰 RabbitMQ | http://localhost:15672 | Interface da fila |
| 🏗️ Jenkins | http://localhost:8081 | CI/CD |



---

## 📡 API Documentation

### Endpoints Principais

#### 📝 Tasks

**Criar Tarefa**
```http
POST /api/task
Content-Type: application/json

{
  "personId": "uuid-do-usuario",
  "title": "Minha tarefa",
  "description": "Descrição da tarefa"
}
```

**Listar Tarefas**
```http
GET /api/task/{personId}
```

**Buscar Tarefa Específica**
```http
GET /api/task/{personId}/{taskId}
```

**Atualizar Tarefa**
```http
PATCH /api/task/{personId}/{taskId}
Content-Type: application/json

{
  "title": "Novo título",
  "description": "Nova descrição",
  "finished": false
}
```

**Finalizar Tarefa**
```http
POST /api/task/{personId}/{taskId}/finish
```

**Deletar Tarefa**
```http
DELETE /api/task/{personId}/{taskId}
```

### Documentação Completa

Acesse http://localhost:8080/swagger-ui.html para documentação interativa completa da API.

---

## 🏗️ Architecture

### Arquitetura Geral
![alt text](/assets/architecture.png)
### Fluxo de Dados

1. **Criação de Tarefa**
   - Frontend → Backend (API REST)
   - Backend → Redis (Persistência)
   - Backend → RabbitMQ (Evento)
   - Consumer → PostgreSQL (Estatísticas)

2. **Consulta de Tarefas**
   - Frontend → Backend (API REST)
   - Backend → Redis (Leitura)
   - Backend → Frontend (Resposta)

### Estrutura de Pastas

```
task-manager/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/example/task_manager/
│   │   │   │       ├── config/
│   │   │   │       ├── controllers/
│   │   │   │       ├── entities/
│   │   │   │       ├── exceptions/
│   │   │   │       ├── http/
│   │   │   │       ├── repository/
│   │   │   │       ├── services/
│   │   │   │       ├── utils/
│   │   │   │       └── validators/
│   │   │   └── resources/
│   │   └── test/
│   ├── Dockerfile
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── api/
│   │   ├── components/
│   │   ├── hooks/
│   │   ├── input/
│   │   ├── lib/
│   │   ├── App.tsx
│   │   └── main.tsx
│   ├── Dockerfile
│   └── package.json
├── consumer/
│   └── src/main/java/
├── jenkins/
│   └── Dockerfile
├── docker-compose.yml
├── jenkinsfile
└── init-db.sql
```

---

## 🧪 Testing

### Backend

**Executar todos os testes**
```bash
cd backend
mvn test
```

**Testes de integração**
```bash
mvn verify
```

**Cobertura de código**
```bash
mvn jacoco:report
```

O relatório será gerado em `backend/target/site/jacoco/index.html`

### Testes Disponíveis

- ✅ Unit Tests (Services, Validators)
- ✅ Integration Tests (Controllers, Repository)

---


### RedisInsight

Interface visual para monitorar o Redis.

**Acessar:** http://localhost:5540

---

## 🐳 Docker build

### Build

**Backend**
```bash
cd backend
./mvnw clean package -DskipTests
docker build -t task-manager-backend .
```

**Frontend**
```bash
cd frontend
pnpm build
docker build -t task-manager-frontend .
```


