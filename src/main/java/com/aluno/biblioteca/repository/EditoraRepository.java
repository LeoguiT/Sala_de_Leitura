package com.aluno.biblioteca.repository;

import com.aluno.biblioteca.domain.Editora;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditoraRepository extends JpaRepository<Editora, Long> {

    boolean existsByCnpj(String cnpj);
}
