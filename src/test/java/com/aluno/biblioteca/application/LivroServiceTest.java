package com.aluno.biblioteca.application;

import com.aluno.biblioteca.domain.CategoriaLivro;
import com.aluno.biblioteca.domain.Editora;
import com.aluno.biblioteca.domain.Livro;
import com.aluno.biblioteca.repository.CategoriaLivroRepository;
import com.aluno.biblioteca.repository.EditoraRepository;
import com.aluno.biblioteca.repository.LivroRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class LivroServiceTest {

    @Autowired
    private LivroService livroService;

    @Autowired
    private CategoriaLivroRepository categoriaRepository;

    @Autowired
    private EditoraRepository editoraRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Test
    void deveCadastrarLivroComCategoriaEEditora() {
        CategoriaLivro categoria = categoriaRepository.save(new CategoriaLivro("Categoria de teste"));
        Editora editora = editoraRepository.save(new Editora(
                "Editora de teste",
                "11111111000191"));

        Livro cadastrado = livroService.cadastrar(
                novoLivro("TESTE-SERVICE-001"),
                categoria.getId(),
                editora.getId());

        assertNotNull(cadastrado.getId());
        assertEquals(categoria.getId(), cadastrado.getCategoria().getId());
        assertEquals(editora.getId(), cadastrado.getEditora().getId());
    }

    @Test
    void deveImpedirIsbnDuplicado() {
        CategoriaLivro categoria = categoriaRepository.save(new CategoriaLivro("Categoria duplicidade"));
        livroService.cadastrar(novoLivro("TESTE-DUPLICADO"), categoria.getId(), null);

        assertThrows(
                RecursoDuplicadoException.class,
                () -> livroService.cadastrar(
                        novoLivro("TESTE-DUPLICADO"),
                        categoria.getId(),
                        null));
    }

    @Test
    void deveInformarCategoriaInexistenteSemSalvarLivro() {
        Livro livro = novoLivro("TESTE-SEM-CATEGORIA");

        assertThrows(
                RecursoNaoEncontradoException.class,
                () -> livroService.cadastrar(livro, Long.MAX_VALUE, null));
        assertEquals(false, livroRepository.existsByIsbn("TESTE-SEM-CATEGORIA"));
    }

    @Test
    void deveAtualizarExemplaresPorDirtyChecking() {
        CategoriaLivro categoria = categoriaRepository.save(new CategoriaLivro("Categoria dirty checking"));
        Livro livro = livroService.cadastrar(
                novoLivro("TESTE-DIRTY-CHECKING"),
                categoria.getId(),
                null);

        livroService.receberExemplares(livro.getId(), new BigDecimal("5"));

        Livro atualizado = livroRepository.findById(livro.getId()).orElseThrow();
        assertEquals(0, new BigDecimal("15").compareTo(atualizado.getExemplaresDisponiveis()));
    }

    private Livro novoLivro(String isbn) {
        return new Livro(
                isbn,
                "Livro de teste",
                new BigDecimal("10"),
                new BigDecimal("25.90"),
                new BigDecimal("2"),
                LocalDate.of(2026, 8, 27));
    }
}
