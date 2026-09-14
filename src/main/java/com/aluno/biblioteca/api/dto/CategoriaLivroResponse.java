package com.aluno.biblioteca.api.dto;

import com.aluno.biblioteca.domain.Status;

public record CategoriaLivroResponse(
        Long id,
        String nome,
        Status status) {
}
