# ConsortiumProject

Plataforma de gerenciamento de consórcios com arquitetura moderna, segurança com JWT, cache Redis e mensageria com RabbitMQ.

---

## ✨ Tecnologias Utilizadas

- **Java 17** + **Spring Boot 3**
- **MongoDB** (para entidades como `Person`)
- **PostgreSQL** (para entidades como `ConsortiumGroup`)
- **Redis** (cache de grupos)
- **RabbitMQ** (mensageria assíncrona)
- **JWT** (autenticação via token)
- **Swagger/OpenAPI** (documentação da API)
- **Docker Compose** (ambiente local)
- **JUnit + Mockito** (testes unitários e de integração)

---

## 📂 Estrutura de Pacotes

```
com.educationproject.consortium
├── config                 # Configurações JWT, Redis, RabbitMQ, Security
├── controller             # Controllers REST
├── dto                   # Data Transfer Objects
├── entity                # Entidades JPA e Mongo
├── exception             # Exceptions e handler global
├── mapper                # Conversores entre DTO e Entity
├── producer/consumer     # Integração RabbitMQ
├── repository            # Repositórios Mongo e JPA
├── service               # Regras de negócio (Services)
├── util                  # Utilitários JWT
└── test                  # Testes unitários e de integração
```

---

## 🔑 Autenticação JWT

- Endpoint: `POST /auth/login`
- Corpo da requisição: `AuthRequestDTO (username, password)`
- Resposta: `AuthResponseDTO (token JWT)`
- Filtro JWT protege os endpoints privados via `JwtAuthenticationFilter`

---

## 🧰 Endpoints Principais

### PersonController (`/person`)

- `POST /` - Criar pessoa
- `GET /` - Listar todas
- `GET /{id}` - Buscar por ID
- `GET /byName/{name}` - Buscar por nome
- `GET /byCity/{city}` - Filtrar por cidade
- `GET /byAge/{age}` - Pessoas com idade > age
- `GET /partialName/{partialName}` - Busca por nome parcial
- `PUT /{id}` - Atualizar pessoa
- `DELETE /{id}` - Remover pessoa

### ConsortiumGroupController (`/consortiumGroup`)

- `POST /create` - Criar grupo de consórcio
- `GET /listAll` - Listar todos (com cache Redis)
- `GET /{id}` - Buscar por ID (cache Redis)
- `PUT /{id}` - Atualizar grupo
- `DELETE /{id}` - Remover grupo

---

## 🪙 Redis Cache

- Implementado no `ConsortiumGroupService`
- Utiliza `RedisTemplate`
- Cache por ID: `consortium_{id}`
- Cache de todos: `consortium_all`
- Expiração: 10 minutos

---

## 🚀 RabbitMQ

- Toda criação/atualização/deleção de `ConsortiumGroup` envia mensagem
- Producer: `ConsortiumGroupProducer`
- Consumer: `ConsortiumGroupConsumer`

---

## ✅ Testes

- `PersonControllerTest` - Testes REST
- `ConsortiumGroupServiceTest` - Lógica de negócio
- `ConsortiumGroupIntegrationTest` - Integração com banco RabbitMQ

---

## 🚫 Tratamento de Erros

Classe centralizada: `GlobalExceptionHandler`

- `@ExceptionHandler` para `ResourceNotFound`, `MethodArgumentNotValidException`, etc.
- Respostas padronizadas com status e mensagem

---

## ⚙️ Como Rodar Localmente

```bash
git clone https://github.com/mateusascacibas/ConsortiumProject.git
cd ConsortiumProject
docker-compose up -d
./mvnw spring-boot:run
```

---

## 🔮 Próximos Passos

-

---

## 👥 Autor

**Mateus Ascacibas da Silva**\
Desenvolvedor Backend Java\
[LinkedIn](https://www.linkedin.com/in/mateus-ascacibas/) | [GitHub](https://github.com/mateusascacibas)

---

> "Esse projeto foi feito com foco em aprendizado e aplicação de boas práticas do mercado."

