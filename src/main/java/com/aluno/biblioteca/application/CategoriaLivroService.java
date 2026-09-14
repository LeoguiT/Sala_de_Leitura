package com.aluno.biblioteca.application;

import com.aluno.biblioteca.domain.CategoriaLivro;
import com.aluno.biblioteca.repository.CategoriaLivroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaLivroService {

    private final CategoriaLivroRepository repository;

    public CategoriaLivroService(CategoriaLivroRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public CategoriaLivro cadastrar(String nome) {
        if (repository.existsByNomeIgnoreCase(nome)) {
            throw new RecursoDuplicadoException("Nome da categoria já cadastrado");
        }
        return repository.save(new CategoriaLivro(nome));
    }

    @Transactional(readOnly = true)
    public CategoriaLivro buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Categoria de livro não encontrada"));
    }

    @Transactional(readOnly = true)
    public List<CategoriaLivro> listar() {
        return repository.findAll();
    }
}
