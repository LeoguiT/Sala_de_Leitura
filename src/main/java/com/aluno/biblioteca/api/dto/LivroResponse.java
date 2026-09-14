package com.aluno.biblioteca.api.dto;

import com.aluno.biblioteca.domain.Status;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LivroResponse(
        Long id,
        String isbn,
        String titulo,
        BigDecimal exemplaresDisponiveis,
        BigDecimal valorAquisicao,
        BigDecimal exemplaresMinimos,
        BigDecimal valorAcervo,
        LocalDate dataAquisicao,
        Status status,
        Long categoriaId,
        String categoriaNome,
        Long editoraId,
        String editoraNome) {
}
