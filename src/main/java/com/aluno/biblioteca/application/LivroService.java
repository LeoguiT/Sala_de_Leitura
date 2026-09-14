package com.aluno.biblioteca.application;

import com.aluno.biblioteca.domain.CategoriaLivro;
import com.aluno.biblioteca.domain.Editora;
import com.aluno.biblioteca.domain.Livro;
import com.aluno.biblioteca.repository.CategoriaLivroRepository;
import com.aluno.biblioteca.repository.EditoraRepository;
import com.aluno.biblioteca.repository.LivroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final CategoriaLivroRepository categoriaRepository;
    private final EditoraRepository editoraRepository;

    public LivroService(
            LivroRepository livroRepository,
            CategoriaLivroRepository categoriaRepository,
            EditoraRepository editoraRepository) {
        this.livroRepository = livroRepository;
        this.categoriaRepository = categoriaRepository;
        this.editoraRepository = editoraRepository;
    }

    @Transactional
    public Livro cadastrar(Livro livro, Long categoriaId, Long editoraId) {
        if (livroRepository.existsByIsbn(livro.getIsbn())) {
            throw new RecursoDuplicadoException("ISBN já cadastrado");
        }

        CategoriaLivro categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Categoria de livro não encontrada"));
        categoria.adicionarLivro(livro);

        if (editoraId != null) {
            Editora editora = editoraRepository.findById(editoraId)
                    .orElseThrow(() -> new RecursoNaoEncontradoException(
                            "Editora não encontrada"));
            livro.associarEditora(editora);
        }

        return livroRepository.save(livro);
    }

    @Transactional(readOnly = true)
    public Livro buscarPorId(Long id) {
        return livroRepository.buscarPorIdComRelacionamentos(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Livro não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<Livro> listar() {
        return livroRepository.buscarTodosComRelacionamentos();
    }

    @Transactional
    public Livro receberExemplares(Long id, BigDecimal quantidade) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Livro não encontrado"));
        livro.receberExemplares(quantidade);
        return livro;
    }
}
