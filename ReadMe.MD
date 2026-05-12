# Agendamento API

API RESTful para gerenciamento de agendamentos de servicos. Permite o cadastro de prestadores de servicos, o registro de seus horarios disponiveis e a realizacao de reservas por clientes, com validacao de conflitos de horario.

## Tecnologias

- Java 21
- Spring Boot 3.3
- Spring Data JPA
- PostgreSQL
- Maven

## Pre-requisitos

- JDK 21
- Maven 3.8+
- PostgreSQL 14+

## Configuracao do Banco de Dados

Crie o banco de dados no PostgreSQL antes de executar a aplicacao:

```sql
CREATE DATABASE agendamento_db;
```

As credenciais padrao configuradas em `application.properties` sao:

```
usuario: postgres
senha: postgres
host: localhost
porta: 5432
```

Altere o arquivo `src/main/resources/application.properties` caso seu ambiente utilize valores diferentes.

## Como Executar

**1. Clone o repositorio e entre na pasta do projeto:**

```bash
git clone https://github.com/seu-usuario/agendamento-api.git
cd agendamento-api
```

**2. Execute com Maven:**

```bash
./mvnw spring-boot:run
```

Ou, se preferir gerar o JAR primeiro:

```bash
./mvnw clean package
java -jar target/agendamento-api-0.0.1-SNAPSHOT.jar
```

A aplicacao sobe na porta `8080` por padrao.

---

## Endpoints

### Prestadores

| Metodo | Rota | Descricao |
|--------|------|-----------|
| POST | `/api/prestadores` | Cadastra um novo prestador |
| GET | `/api/prestadores` | Lista todos os prestadores |
| GET | `/api/prestadores/{id}` | Busca um prestador pelo ID |
| PUT | `/api/prestadores/{id}` | Atualiza os dados de um prestador |
| DELETE | `/api/prestadores/{id}` | Remove um prestador |

**Exemplo de requisicao - POST /api/prestadores:**

```json
{
  "nome": "Carlos Mendes",
  "email": "carlos.mendes@email.com",
  "especialidade": "Desenvolvimento Web",
  "telefone": "11999990000"
}
```

**Resposta 201 Created:**

```json
{
  "id": 1,
  "nome": "Carlos Mendes",
  "email": "carlos.mendes@email.com",
  "especialidade": "Desenvolvimento Web",
  "telefone": "11999990000"
}
```

---

### Horarios Disponiveis

| Metodo | Rota | Descricao |
|--------|------|-----------|
| POST | `/api/horarios` | Cadastra um horario disponivel para um prestador |
| GET | `/api/horarios` | Lista todos os horarios |
| GET | `/api/horarios/{id}` | Busca um horario pelo ID |
| GET | `/api/horarios/prestador/{prestadorId}/disponiveis` | Lista os horarios livres de um prestador |
| DELETE | `/api/horarios/{id}` | Remove um horario |

**Exemplo de requisicao - POST /api/horarios:**

```json
{
  "prestadorId": 1,
  "inicio": "2025-08-15T09:00:00",
  "fim": "2025-08-15T10:00:00"
}
```

**Resposta 201 Created:**

```json
{
  "id": 1,
  "prestadorId": 1,
  "nomePrestador": "Carlos Mendes",
  "inicio": "2025-08-15T09:00:00",
  "fim": "2025-08-15T10:00:00",
  "disponivel": true
}
```

---

### Reservas

| Metodo | Rota | Descricao |
|--------|------|-----------|
| POST | `/api/reservas` | Cria uma reserva para um horario disponivel |
| GET | `/api/reservas` | Lista todas as reservas |
| GET | `/api/reservas/{id}` | Busca uma reserva pelo ID |
| GET | `/api/reservas/cliente?email={email}` | Lista reservas de um cliente pelo email |
| PATCH | `/api/reservas/{id}/cancelar` | Cancela uma reserva e libera o horario |

**Exemplo de requisicao - POST /api/reservas:**

```json
{
  "nomeCliente": "Ana Lima",
  "emailCliente": "ana.lima@email.com",
  "telefoneCliente": "11988887777",
  "horarioId": 1
}
```

**Resposta 201 Created:**

```json
{
  "id": 1,
  "nomeCliente": "Ana Lima",
  "emailCliente": "ana.lima@email.com",
  "telefoneCliente": "11988887777",
  "horarioId": 1,
  "inicioHorario": "2025-08-15T09:00:00",
  "fimHorario": "2025-08-15T10:00:00",
  "nomePrestador": "Carlos Mendes",
  "criadoEm": "2025-05-12T14:30:00",
  "status": "CONFIRMADA"
}
```

---

## Validacoes e Regras de Negocio

- Nao e permitido cadastrar dois prestadores com o mesmo email.
- O horario de fim de um intervalo deve ser posterior ao horario de inicio.
- Nao e possivel criar horarios disponiveis sobrepostos para o mesmo prestador.
- Um horario so pode ser reservado se seu campo `disponivel` for `true`. Ao confirmar uma reserva, o horario e marcado como indisponivel automaticamente.
- Ao cancelar uma reserva, o horario associado volta a ficar disponivel.
- Tentativas de reservar um horario ja ocupado retornam `409 Conflict`.
- Recursos nao encontrados retornam `404 Not Found`.
- Erros de validacao de campos retornam `400 Bad Request` com detalhes por campo.

## Estrutura do Projeto

```
src/main/java/com/agendamento/api/
  controller/
    PrestadorController.java
    HorarioDisponivelController.java
    ReservaController.java
  service/
    PrestadorService.java
    HorarioDisponivelService.java
    ReservaService.java
  repository/
    PrestadorRepository.java
    HorarioDisponivelRepository.java
    ReservaRepository.java
  model/
    Prestador.java
    HorarioDisponivel.java
    Reserva.java
  dto/
    PrestadorRequestDTO.java
    PrestadorResponseDTO.java
    HorarioDisponivelRequestDTO.java
    HorarioDisponivelResponseDTO.java
    ReservaRequestDTO.java
    ReservaResponseDTO.java
  exception/
    RecursoNaoEncontradoException.java
    ConflitoDeHorarioException.java
    GlobalExceptionHandler.java
```