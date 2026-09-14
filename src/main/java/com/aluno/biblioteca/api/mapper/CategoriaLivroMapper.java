package com.aluno.biblioteca.api.mapper;

import com.aluno.biblioteca.api.dto.CategoriaLivroResponse;
import com.aluno.biblioteca.domain.CategoriaLivro;
import org.springframework.stereotype.Component;

@Component
public class CategoriaLivroMapper {

    public CategoriaLivroResponse toResponse(CategoriaLivro categoria) {
        return new CategoriaLivroResponse(
                categoria.getId(),
                categoria.getNome(),
                categoria.getStatus());
    }
}
