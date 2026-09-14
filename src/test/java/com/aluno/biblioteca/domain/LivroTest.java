package com.aluno.biblioteca.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LivroTest {

    @Test
    void deveCriarLivroAtivoComDadosValidos() {
        Livro livro = novoLivro("5", "59.90");

        assertEquals("9788535902778", livro.getIsbn());
        assertEquals("Duna", livro.getTitulo());
        assertEquals(Status.ATIVO, livro.getStatus());
        assertEquals(LocalDate.of(2026, 8, 20), livro.getDataAquisicao());
    }

    @Test
    void deveCalcularValorDoAcervo() {
        Livro livro = novoLivro("5", "59.90");

        BigDecimal valorAcervo = livro.calcularValorAcervo();

        assertEquals(0, new BigDecimal("299.50").compareTo(valorAcervo));
    }

    @Test
    void deveReceberERetirarExemplares() {
        Livro livro = novoLivro("5", "59.90");

        livro.receberExemplares(new BigDecimal("3"));
        livro.retirarExemplares(new BigDecimal("2"));

        assertEquals(0, new BigDecimal("6").compareTo(livro.getExemplaresDisponiveis()));
    }

    @Test
    void naoDeveRetirarQuantidadeMaiorQueOsExemplaresDisponiveis() {
        Livro livro = novoLivro("5", "59.90");

        IllegalArgumentException excecao = assertThrows(
                IllegalArgumentException.class,
                () -> livro.retirarExemplares(new BigDecimal("6")));

        assertEquals("Exemplares disponíveis insuficientes", excecao.getMessage());
    }

    @Test
    void naoDeveCriarLivroComIsbnEmBranco() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Livro(
                        "  ",
                        "Duna",
                        BigDecimal.ZERO,
                        new BigDecimal("59.90"),
                        LocalDate.of(2026, 8, 20)));
    }

    @Test
    void naoDeveCriarLivroComExemplaresNegativos() {
        assertThrows(
                IllegalArgumentException.class,
                () -> novoLivro("-1", "59.90"));
    }

    @Test
    void deveAlterarOStatusPorComportamentoExplicito() {
        Livro livro = novoLivro("5", "59.90");

        livro.inativar();
        assertEquals(Status.INATIVO, livro.getStatus());

        livro.ativar();
        assertEquals(Status.ATIVO, livro.getStatus());
    }

    private Livro novoLivro(String exemplares, String valorAquisicao) {
        return new Livro(
                "9788535902778",
                "Duna",
                new BigDecimal(exemplares),
                new BigDecimal(valorAquisicao),
                LocalDate.of(2026, 8, 20));
    }
}
