package com.aluno.biblioteca.repository;

import com.aluno.biblioteca.domain.Livro;
import com.aluno.biblioteca.domain.Status;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    Optional<Livro> findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);

    List<Livro> findByCategoriaId(Long categoriaId);

    List<Livro> findByStatus(Status status);

    @EntityGraph(attributePaths = {"categoria", "editora"})
    @Query("select l from Livro l order by l.id")
    List<Livro> buscarTodosComRelacionamentos();

    @EntityGraph(attributePaths = {"categoria", "editora"})
    @Query("select l from Livro l where l.id = :id")
    Optional<Livro> buscarPorIdComRelacionamentos(@Param("id") Long id);
}
