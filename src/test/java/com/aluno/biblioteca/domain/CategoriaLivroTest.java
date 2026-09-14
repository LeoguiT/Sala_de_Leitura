package com.aluno.biblioteca.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CategoriaLivroTest {

    @Test
    void deveAdicionarLivroEManejarOsDoisLadosDaAssociacao() {
        CategoriaLivro categoria = new CategoriaLivro("Ficção Científica");
        Livro livro = novoLivro("9788535902778");

        categoria.adicionarLivro(livro);

        assertEquals(1, categoria.getLivros().size());
        assertSame(livro, categoria.getLivros().getFirst());
        assertSame(categoria, livro.getCategoria());
    }

    @Test
    void naoDeveAdicionarLivroNulo() {
        CategoriaLivro categoria = new CategoriaLivro("Ficção Científica");

        assertThrows(NullPointerException.class, () -> categoria.adicionarLivro(null));
    }

    @Test
    void naoDeveAdicionarDoisLivrosComOMesmoIsbn() {
        CategoriaLivro categoria = new CategoriaLivro("Ficção Científica");
        categoria.adicionarLivro(novoLivro("9788535902778"));

        IllegalArgumentException excecao = assertThrows(
                IllegalArgumentException.class,
                () -> categoria.adicionarLivro(novoLivro("9788535902778")));

        assertEquals("ISBN já utilizado na categoria", excecao.getMessage());
    }

    @Test
    void naoDevePermitirQueLivroPertençaADuasCategorias() {
        CategoriaLivro ficcao = new CategoriaLivro("Ficção Científica");
        CategoriaLivro tecnico = new CategoriaLivro("Técnico");
        Livro livro = novoLivro("9788535902778");
        ficcao.adicionarLivro(livro);

        IllegalStateException excecao = assertThrows(
                IllegalStateException.class,
                () -> tecnico.adicionarLivro(livro));

        assertEquals("Livro já pertence a outra categoria", excecao.getMessage());
    }

    @Test
    void naoDeveExporUmaListaInternaModificavel() {
        CategoriaLivro categoria = new CategoriaLivro("Ficção Científica");
        Livro livro = novoLivro("9788535902778");
        categoria.adicionarLivro(livro);

        assertThrows(
                UnsupportedOperationException.class,
                () -> categoria.getLivros().add(novoLivro("9788575225631")));
    }

    private Livro novoLivro(String isbn) {
        return new Livro(
                isbn,
                "Duna",
                new BigDecimal("5"),
                new BigDecimal("59.90"),
                LocalDate.of(2026, 8, 20));
    }
}
