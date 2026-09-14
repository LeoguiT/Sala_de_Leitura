# Biblioteca 2026

API didática desenvolvida na disciplina de Programação da graduação em Sistemas de Informação, com base no roteiro de aulas [`suporteos2026`](https://github.com/jeffersonarpasserini/suporteos2026) do professor Jefferson Passerini.

Este projeto usa um tema próprio — controle de acervo de uma biblioteca — mantendo a mesma estrutura conceitual do projeto de referência.

## Domínio inicial

- Categoria de livro: classificação usada para organizar os livros do acervo.
- Livro: item identificado por ISBN, com exemplares disponíveis e valor de aquisição.

## Requisitos

- Java 21
- Git
- IntelliJ IDEA (ou outra IDE compatível com Maven)
- Docker Desktop ou PostgreSQL local, usado a partir da aula de persistência

## Organização do curso

O sistema será construído incrementalmente, acompanhando as aulas do projeto de referência. Cada aula termina em um estado executável, registrado por um commit e, após validação, por uma tag Git no formato `aula-NN-*`.

## Projeto de referência

O tema oficial do curso ([`suporteos2026`](https://github.com/jeffersonarpasserini/suporteos2026)) demonstra um controle simplificado de produtos organizados por grupos. Este repositório mantém o mesmo domínio conceitual (entidade de classificação + entidade principal), aplicado a um acervo de biblioteca — ver [`docs/tema-do-projeto.md`](docs/tema-do-projeto.md).

## Executando o projeto

Na primeira execução, copie `.env.example` para `.env`, preencha `DB_DEV_PASSWORD` e `DB_TEST_PASSWORD` e mantenha esse arquivo fora do Git.

```bash
cp .env.example .env
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Com a aplicação iniciada, acesse <http://localhost:8080/api/health>. A resposta esperada é `OK`.

## Modelo de domínio atual

O modelo representa categorias, livros e editoras:

```text
CategoriaLivro 1 ─────── N Livro
Editora        1 ─────── N Livro
```

A editora de um livro é opcional. As classes estão no pacote `com.aluno.biblioteca.domain`, são mapeadas com JPA e preservam as regras de negócio. O Liquibase cria e versiona o esquema; o Hibernate apenas o valida. Os profiles `dev`, `test` e `prod` usam PostgreSQL, sem H2.

## API atual

Após iniciar a aplicação, estão disponíveis os cadastros, consultas por ID e listagens de:

- `http://localhost:8080/api/categorias-livros`;
- `http://localhost:8080/api/editoras`;
- `http://localhost:8080/api/livros`.

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
