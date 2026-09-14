package com.aluno.biblioteca.domain;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class PersistenciaJpaTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    void devePersistirERelerCategoriaELivro() {
        CategoriaLivro categoria = new CategoriaLivro("Ficção Científica");
        Livro livro = new Livro(
                "9788535902778",
                "Duna",
                new BigDecimal("5.000"),
                new BigDecimal("59.90"),
                LocalDate.of(2026, 3, 10));

        categoria.adicionarLivro(livro);

        entityManager.persist(categoria);
        entityManager.persist(livro);
        entityManager.flush();

        Long livroId = livro.getId();
        entityManager.clear();

        Livro livroRecuperado = entityManager.find(Livro.class, livroId);

        assertNotNull(livroRecuperado);
        assertEquals("Duna", livroRecuperado.getTitulo());
        assertEquals("Ficção Científica", livroRecuperado.getCategoria().getNome());
        assertEquals(Status.ATIVO, livroRecuperado.getStatus());
    }

    @Test
    void deveRegistrarTodosOsChangeSetsDoCurso() {
        Integer quantidade = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM databasechangelog",
                Integer.class);

        assertEquals(17, quantidade);
    }

    @Test
    @Transactional
    void bancoDeveImpedirIsbnDuplicado() {
        Long categoriaId = inserirCategoriaDiretamente("Categoria para unicidade");

        inserirLivroDiretamente(categoriaId, "ISBN-REPETIDO", "Primeiro livro", "1.000", "10.00");

        assertThrows(
                DataIntegrityViolationException.class,
                () -> inserirLivroDiretamente(
                        categoriaId,
                        "ISBN-REPETIDO",
                        "Segundo livro",
                        "1.000",
                        "20.00"));
    }

    @Test
    @Transactional
    void bancoDeveImpedirExemplaresNegativos() {
        Long categoriaId = inserirCategoriaDiretamente("Categoria para saldo");

        assertThrows(
                DataIntegrityViolationException.class,
                () -> inserirLivroDiretamente(
                        categoriaId,
                        "ISBN-SALDO-NEGATIVO",
                        "Livro inválido",
                        "-1.000",
                        "10.00"));
    }

    private Long inserirCategoriaDiretamente(String nome) {
        return jdbcTemplate.queryForObject(
                """
                INSERT INTO categoria_livro (nome, status)
                VALUES (?, 'ATIVO')
                RETURNING id
                """,
                Long.class,
                nome);
    }

    private void inserirLivroDiretamente(
            Long categoriaId,
            String isbn,
            String titulo,
            String exemplares,
            String valor) {
        jdbcTemplate.update(
                """
                INSERT INTO livro (
                    isbn,
                    titulo,
                    exemplares_disponiveis,
                    valor_aquisicao,
                    exemplares_minimos,
                    data_aquisicao,
                    status,
                    categoria_livro_id
                )
                VALUES (?, ?, CAST(? AS NUMERIC), CAST(? AS NUMERIC), 0, DATE '2026-03-10', 'ATIVO', ?)
                """,
                isbn,
                titulo,
                exemplares,
                valor,
                categoriaId);
    }
}
