package com.aluno.biblioteca.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EditoraTest {

    @Test
    void deveCriarEditoraAtiva() {
        Editora editora = new Editora(
                "Editora Acadêmica",
                "12345678000190");

        assertEquals("Editora Acadêmica", editora.getNome());
        assertEquals("12345678000190", editora.getCnpj());
        assertEquals(Status.ATIVO, editora.getStatus());
    }

    @Test
    void deveRejeitarCnpjComFormatoInvalido() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Editora("Editora", "12.345"));
    }

    @Test
    void deveRejeitarNomeVazio() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Editora(" ", "12345678000190"));
    }

    @Test
    void deveInativarEditora() {
        Editora editora = new Editora("Editora", "12345678000190");

        editora.inativar();

        assertEquals(Status.INATIVO, editora.getStatus());
    }
}
