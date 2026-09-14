package com.aluno.biblioteca.api.dto;

import com.aluno.biblioteca.domain.Status;

public record EditoraResponse(
        Long id,
        String nome,
        String cnpj,
        Status status) {
}
