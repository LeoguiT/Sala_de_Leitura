package com.aluno.biblioteca.api.controller;

import com.aluno.biblioteca.api.dto.CategoriaLivroRequest;
import com.aluno.biblioteca.api.dto.CategoriaLivroResponse;
import com.aluno.biblioteca.api.mapper.CategoriaLivroMapper;
import com.aluno.biblioteca.application.CategoriaLivroService;
import com.aluno.biblioteca.domain.CategoriaLivro;
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
@RequestMapping("/api/categorias-livros")
public class CategoriaLivroController {

    private final CategoriaLivroService service;
    private final CategoriaLivroMapper mapper;

    public CategoriaLivroController(CategoriaLivroService service, CategoriaLivroMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<CategoriaLivroResponse> cadastrar(
            @Valid @RequestBody CategoriaLivroRequest request) {
        CategoriaLivro categoria = service.cadastrar(request.nome());
        URI location = URI.create("/api/categorias-livros/" + categoria.getId());
        return ResponseEntity.created(location).body(mapper.toResponse(categoria));
    }

    @GetMapping("/{id}")
    public CategoriaLivroResponse buscarPorId(@PathVariable Long id) {
        return mapper.toResponse(service.buscarPorId(id));
    }

    @GetMapping
    public List<CategoriaLivroResponse> listar() {
        return service.listar().stream().map(mapper::toResponse).toList();
    }
}
