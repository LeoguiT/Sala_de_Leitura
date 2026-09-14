package com.aluno.biblioteca.api.controller;

import com.aluno.biblioteca.api.dto.LivroRequest;
import com.aluno.biblioteca.api.dto.LivroResponse;
import com.aluno.biblioteca.api.mapper.LivroMapper;
import com.aluno.biblioteca.application.LivroService;
import com.aluno.biblioteca.domain.Livro;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private final LivroService service;
    private final LivroMapper mapper;

    public LivroController(LivroService service, LivroMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<LivroResponse> cadastrar(
            @Valid @RequestBody LivroRequest request) {
        Livro livro = mapper.toEntity(request);
        Livro cadastrado = service.cadastrar(
                livro,
                request.categoriaId(),
                request.editoraId());
        URI location = URI.create("/api/livros/" + cadastrado.getId());
        return ResponseEntity.created(location).body(mapper.toResponse(cadastrado));
    }

    @GetMapping("/{id}")
    public LivroResponse buscarPorId(@PathVariable Long id) {
        return mapper.toResponse(service.buscarPorId(id));
    }

    @GetMapping
    public List<LivroResponse> listar() {
        return service.listar().stream().map(mapper::toResponse).toList();
    }
}
