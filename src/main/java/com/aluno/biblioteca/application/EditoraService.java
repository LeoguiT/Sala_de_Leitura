package com.aluno.biblioteca.application;

import com.aluno.biblioteca.domain.Editora;
import com.aluno.biblioteca.repository.EditoraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EditoraService {

    private final EditoraRepository repository;

    public EditoraService(EditoraRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Editora cadastrar(Editora editora) {
        if (repository.existsByCnpj(editora.getCnpj())) {
            throw new RecursoDuplicadoException("CNPJ já cadastrado");
        }
        return repository.save(editora);
    }

    @Transactional(readOnly = true)
    public Editora buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Editora não encontrada"));
    }

    @Transactional(readOnly = true)
    public List<Editora> listar() {
        return repository.findAll();
    }
}
