package com.aluno.biblioteca.api.controller;

import com.aluno.biblioteca.api.dto.EditoraRequest;
import com.aluno.biblioteca.api.dto.EditoraResponse;
import com.aluno.biblioteca.api.mapper.EditoraMapper;
import com.aluno.biblioteca.application.EditoraService;
import com.aluno.biblioteca.domain.Editora;
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
@RequestMapping("/api/editoras")
public class EditoraController {

    private final EditoraService service;
    private final EditoraMapper mapper;

    public EditoraController(EditoraService service, EditoraMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<EditoraResponse> cadastrar(
            @Valid @RequestBody EditoraRequest request) {
        Editora editora = service.cadastrar(mapper.toEntity(request));
        URI location = URI.create("/api/editoras/" + editora.getId());
        return ResponseEntity.created(location).body(mapper.toResponse(editora));
    }

    @GetMapping("/{id}")
    public EditoraResponse buscarPorId(@PathVariable Long id) {
        return mapper.toResponse(service.buscarPorId(id));
    }

    @GetMapping
    public List<EditoraResponse> listar() {
        return service.listar().stream().map(mapper::toResponse).toList();
    }
}
