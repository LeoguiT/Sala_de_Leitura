# Tema do projeto

## Identificação

- Nome: `saladeleitura2026`
- Tema: Controle de Uma Sala De Leitura
- Objetivo: cadastrar livros e organizá-los por categorias

## Classificação

- Nome no singular: `CategoriaLivro`
- Nome no plural: categorias de livros
- Descrição: classificação utilizada para organizar os livros
- Exemplos: Ficção Científica, Técnico, Infantil, Biografia
- Status: ativo ou inativo

## Principal

- Nome no singular: `Livro`
- Nome no plural: livros
- Código único: ISBN
- Descrição: título
- Medida quantitativa: disponíveis
- Valor unitário: valor de adquirido 
- Valor calculado: valor total
- Data relevante: data de quando pegou
- Status: ativo ou inativo

## Relacionamento

- Uma categoria de livro pode classificar vários livros.
- Cada livro pertence a uma categoria de livro.

## Exemplos

| Categoria | ISBN | Livro | Exemplares | Valor de aquisição |
|---|---|---|---:|---:|
| Ficção Científica | `9788535902778` | Duna | 5 | 59,90 |
| Técnico | `9788575225631` | Clean Code | 3 | 129,90 |
| Infantil | `9788508139120` | O Pequeno Príncipe | 8 | 24,50 |

## Projeto

```text
GrupoProduto -> CategoriaLivro
Produto      -> Livro
Fornecedor   -> Editora
```

| Campo em `saladeleitura`   | Campo em `saladeleitura2026` |
|----------------------------|------------------------------|
| `codigoBarras`             | `isbn`                       |
| `descricao`                | `titulo`                     |
| `saldoEstoque`             | `exemplaresDisponiveis`      |
| `valorUnitario`            | `valorAquisicao`             |
| `estoqueMinimo`            | `exemplaresMinimos`          |
| `dataCadastro`             | `dataAquisicao`              |
| `razaoSocial` (Fornecedor) | `nome` (Editora)             |
