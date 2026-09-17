# Sala De Leitura 2026

Começo
API didática desenvolvida na disciplina de Programação da graduação em Sistemas de Informação.

API didática desenvolvida na disciplina de Programação da graduação em Sistemas de Informação

## Domínio inicial

- Categoria de livro: classificação usada para organizar os livros do acervo.
- Livro: item identificado por ISBN, com exemplares disponíveis e valor de aquisição.

## Requisitos

- Java 21
- Git
- IntelliJ IDEA (ou outra IDE compatível com Maven)
- Docker Desktop ou PostgreSQL local, usado a partir da aula de persistência
- Postman, para testar os endpoints da API
=======
- Docker Desktop ou PostgreSQL local, usado a partir da aula de persistência também o postmain

## Organização do curso

O sistema será construído incrementalmente, acompanhando as aulas do projeto de referência.

## Executando o projeto

Na primeira execução, copie `.env.example` para `.env`, preencha `DB_DEV_PASSWORD` e `DB_TEST_PASSWORD` e mantenha esse arquivo fora do Git.

```bash
cp .env.example .env
```

No Windows (PowerShell):

```powershell
$env:SPRING_PROFILES_ACTIVE="dev"
.\mvnw spring-boot:run
```

No macOS/Linux:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Com a aplicação iniciada, acesse <http://localhost:8081/api/health>. A resposta esperada é `OK`.

> Se a porta 8080 já estiver em uso na sua máquina, defina outra porta antes de rodar:
> ```powershell
> $env:SERVER_PORT="8081"
> ```

## Modelo de domínio atual

O modelo representa categorias, livros e editoras:

```text
CategoriaLivro 1 ─────── N Livro
Editora        1 ─────── N Livro
```

A editora de um livro é opcional. As classes estão no pacote `com.aluno.biblioteca.domain`, são mapeadas com JPA e preservam as regras de negócio. O Liquibase cria e versiona o esquema; o Hibernate apenas o valida. Os profiles `dev`, `test` e `prod` usam PostgreSQL, sem H2.

## API atual

Após iniciar a aplicação, estão disponíveis os cadastros, consultas por ID e listagens de:

- `http://localhost:8081/api/categorias-livros`;
- `http://localhost:8081/api/editoras`;
- `http://localhost:8081/api/livros`.

(troque `8080` pela porta configurada via `SERVER_PORT`, se for diferente)

Os contratos usam DTOs, validação de entrada e respostas de erro padronizadas.

## Executando os testes

```bash
./mvnw test
```

Os testes de persistência acessam `biblioteca2026_test` e leem `DB_TEST_PASSWORD` do `.env` local ou das variáveis do ambiente de execução.

## Histórico didático

Para consultar os marcos publicados:

```bash
git tag --list
```
