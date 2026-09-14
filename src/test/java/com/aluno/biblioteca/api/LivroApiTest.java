package com.aluno.biblioteca.api;

import com.aluno.biblioteca.domain.CategoriaLivro;
import com.aluno.biblioteca.domain.Editora;
import com.aluno.biblioteca.repository.CategoriaLivroRepository;
import com.aluno.biblioteca.repository.EditoraRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class LivroApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoriaLivroRepository categoriaRepository;

    @Autowired
    private EditoraRepository editoraRepository;

    @Test
    void deveCadastrarLivroERetornar201() throws Exception {
        CategoriaLivro categoria = categoriaRepository.save(new CategoriaLivro("Categoria API"));
        Editora editora = editoraRepository.save(new Editora(
                "Editora API",
                "22222222000192"));

        String json = """
                {
                  "isbn": "API-001",
                  "titulo": "Livro criado pela API",
                  "exemplaresDisponiveis": 10,
                  "valorAquisicao": 49.90,
                  "exemplaresMinimos": 2,
                  "categoriaId": %d,
                  "editoraId": %d
                }
                """.formatted(categoria.getId(), editora.getId());

        mockMvc.perform(post("/api/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.isbn").value("API-001"))
                .andExpect(jsonPath("$.categoriaNome").value("Categoria API"))
                .andExpect(jsonPath("$.editoraNome").value("Editora API"));
    }

    @Test
    void deveRetornar400ComErrosPorCampo() throws Exception {
        mockMvc.perform(post("/api/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Um ou mais campos são inválidos"))
                .andExpect(jsonPath("$.fields.isbn").exists())
                .andExpect(jsonPath("$.fields.categoriaId").exists());
    }

    @Test
    void deveRetornar404ParaLivroInexistente() throws Exception {
        mockMvc.perform(get("/api/livros/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Livro não encontrado"))
                .andExpect(jsonPath("$.path").value("/api/livros/" + Long.MAX_VALUE));
    }
}
