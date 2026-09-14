package com.aluno.biblioteca.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record LivroRequest(
        @NotBlank(message = "ISBN é obrigatório")
        @Size(max = 50, message = "ISBN deve possuir no máximo 50 caracteres")
        String isbn,

        @NotBlank(message = "Título é obrigatório")
        @Size(max = 150, message = "Título deve possuir no máximo 150 caracteres")
        String titulo,

        @NotNull(message = "Exemplares disponíveis é obrigatório")
        @PositiveOrZero(message = "Exemplares disponíveis não pode ser negativo")
        BigDecimal exemplaresDisponiveis,

        @NotNull(message = "Valor de aquisição é obrigatório")
        @PositiveOrZero(message = "Valor de aquisição não pode ser negativo")
        BigDecimal valorAquisicao,

        @NotNull(message = "Exemplares mínimos é obrigatório")
        @PositiveOrZero(message = "Exemplares mínimos não pode ser negativo")
        BigDecimal exemplaresMinimos,

        @NotNull(message = "Categoria é obrigatória")
        @Positive(message = "Identificador da categoria deve ser positivo")
        Long categoriaId,

        @Positive(message = "Identificador da editora deve ser positivo")
        Long editoraId) {
}
