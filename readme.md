# Task Manager

O Task Manager é um gerenciador de tarefas genérico, rápido e extremamente simples, criado com o propósito de resolver um problema real:


Gerenciar tarefas sem frescura, sem anúncios, sem freemium, sem distrações.
Apenas o que você precisa. Nada além disso.

A proposta é entregar uma experiência limpa — só criar, listar, atualizar e apagar tarefas com máxima eficiência, tanto no backend (API) quanto no uso geral.


## Tecnologias

🔧 Backend

Java 21 — performance, segurança e recursos modernos da linguagem

- Spring Boot 3 — produtividade e estrutura elegante para construção de APIs

- Redis — armazenamento rápido em memória e persistente via AOF

- Swagger / springdoc-openapi — documentação automática e interativa

- Docker — conteinerização do backend e dos serviços auxiliares

🖥️ Frontend (em desenvolvimento)

- React — construção de UIs modernas e declarativas

- Vite — bundler ultrarrápido para desenvolvimento

- TypeScript — segurança de tipos e maior robustez no front

- Tailwind CSS — estilização rápida, responsiva e sem esforço

- React Query / Zustand (planejado) — gerenciamento eficiente de estados e cache de API

🔨 Infraestrutura / DevOps

- Docker Compose — orquestração simples dos serviços (Backend, Redis, RedisInsight)

- RedisInsight — inspeção visual das chaves Redis

- SonarQube (opcional) — análise de qualidade e cobertura de código

- GitHub Actions (planejado) — CI/CD automatizado

## Build and Run

### Pré-requisitos

- Docker 🐋
- Docker compose 🐳

### Run

`Docker compose up -d`

_Deve subir algumas aplicações sendo elas_

- Task Manager Backend
- Redis
- Redis Insight
- Sonar Qube
