package com.aluno.biblioteca.api.mapper;

import com.aluno.biblioteca.api.dto.LivroRequest;
import com.aluno.biblioteca.api.dto.LivroResponse;
import com.aluno.biblioteca.domain.Editora;
import com.aluno.biblioteca.domain.Livro;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LivroMapper {

    public Livro toEntity(LivroRequest request) {
        return new Livro(
                request.isbn(),
                request.titulo(),
                request.exemplaresDisponiveis(),
                request.valorAquisicao(),
                request.exemplaresMinimos(),
                LocalDate.now());
    }

    public LivroResponse toResponse(Livro livro) {
        Editora editora = livro.getEditora();

        return new LivroResponse(
                livro.getId(),
                livro.getIsbn(),
                livro.getTitulo(),
                livro.getExemplaresDisponiveis(),
                livro.getValorAquisicao(),
                livro.getExemplaresMinimos(),
                livro.calcularValorAcervo(),
                livro.getDataAquisicao(),
                livro.getStatus(),
                livro.getCategoria().getId(),
                livro.getCategoria().getNome(),
                editora == null ? null : editora.getId(),
                editora == null ? null : editora.getNome());
    }
}
