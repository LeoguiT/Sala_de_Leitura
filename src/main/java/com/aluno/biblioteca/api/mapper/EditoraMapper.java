package com.aluno.biblioteca.api.mapper;

import com.aluno.biblioteca.api.dto.EditoraRequest;
import com.aluno.biblioteca.api.dto.EditoraResponse;
import com.aluno.biblioteca.domain.Editora;
import org.springframework.stereotype.Component;

@Component
public class EditoraMapper {

    public Editora toEntity(EditoraRequest request) {
        return new Editora(request.nome(), request.cnpj());
    }

    public EditoraResponse toResponse(Editora editora) {
        return new EditoraResponse(
                editora.getId(),
                editora.getNome(),
                editora.getCnpj(),
                editora.getStatus());
    }
}
