package com.aluno.biblioteca.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(
        name = "livro",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_livro_isbn",
                columnNames = "isbn"))
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String isbn;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(name = "exemplares_disponiveis", nullable = false, precision = 18, scale = 3)
    private BigDecimal exemplaresDisponiveis;

    @Column(name = "valor_aquisicao", nullable = false, precision = 18, scale = 2)
    private BigDecimal valorAquisicao;

    @Column(name = "exemplares_minimos", nullable = false, precision = 18, scale = 3)
    private BigDecimal exemplaresMinimos;

    @Column(name = "data_aquisicao", nullable = false)
    private LocalDate dataAquisicao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "categoria_livro_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_livro_categoria_livro"))
    private CategoriaLivro categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "editora_id",
            foreignKey = @ForeignKey(name = "fk_livro_editora"))
    private Editora editora;

    protected Livro() {
    }

    public Livro(
            String isbn,
            String titulo,
            BigDecimal exemplaresDisponiveis,
            BigDecimal valorAquisicao,
            LocalDate dataAquisicao) {
        this(
                isbn,
                titulo,
                exemplaresDisponiveis,
                valorAquisicao,
                BigDecimal.ZERO,
                dataAquisicao);
    }

    public Livro(
            String isbn,
            String titulo,
            BigDecimal exemplaresDisponiveis,
            BigDecimal valorAquisicao,
            BigDecimal exemplaresMinimos,
            LocalDate dataAquisicao) {
        this.isbn = validarTextoObrigatorio(isbn, "ISBN é obrigatório");
        this.titulo = validarTextoObrigatorio(titulo, "Título é obrigatório");
        this.exemplaresDisponiveis = validarNaoNegativo(
                exemplaresDisponiveis,
                "Exemplares disponíveis não pode ser negativo");
        this.valorAquisicao = validarNaoNegativo(
                valorAquisicao,
                "Valor de aquisição não pode ser negativo");
        this.exemplaresMinimos = validarNaoNegativo(
                exemplaresMinimos,
                "Exemplares mínimos não pode ser negativo");
        this.dataAquisicao = Objects.requireNonNull(
                dataAquisicao,
                "Data de aquisição é obrigatória");
        this.status = Status.ATIVO;
    }

    public BigDecimal calcularValorAcervo() {
        return exemplaresDisponiveis
                .multiply(valorAquisicao)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public void receberExemplares(BigDecimal quantidade) {
        validarPositivo(quantidade, "Quantidade recebida deve ser maior que zero");
        this.exemplaresDisponiveis = exemplaresDisponiveis.add(quantidade);
    }

    public void retirarExemplares(BigDecimal quantidade) {
        validarPositivo(quantidade, "Quantidade retirada deve ser maior que zero");

        if (exemplaresDisponiveis.compareTo(quantidade) < 0) {
            throw new IllegalArgumentException("Exemplares disponíveis insuficientes");
        }

        this.exemplaresDisponiveis = exemplaresDisponiveis.subtract(quantidade);
    }

    public void alterarTitulo(String novoTitulo) {
        this.titulo = validarTextoObrigatorio(novoTitulo, "Título é obrigatório");
    }

    public void alterarValorAquisicao(BigDecimal novoValor) {
        this.valorAquisicao = validarNaoNegativo(
                novoValor,
                "Valor de aquisição não pode ser negativo");
    }

    public void ativar() {
        this.status = Status.ATIVO;
    }

    public void inativar() {
        this.status = Status.INATIVO;
    }

    void associarACategoria(CategoriaLivro categoria) {
        Objects.requireNonNull(categoria, "Categoria é obrigatória");

        if (this.categoria != null && this.categoria != categoria) {
            throw new IllegalStateException("Livro já pertence a outra categoria");
        }

        this.categoria = categoria;
    }

    public void associarEditora(Editora editora) {
        this.editora = Objects.requireNonNull(editora, "Editora é obrigatória");
    }

    public String getIsbn() {
        return isbn;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public BigDecimal getExemplaresDisponiveis() {
        return exemplaresDisponiveis;
    }

    public BigDecimal getValorAquisicao() {
        return valorAquisicao;
    }

    public BigDecimal getExemplaresMinimos() {
        return exemplaresMinimos;
    }

    public LocalDate getDataAquisicao() {
        return dataAquisicao;
    }

    public Status getStatus() {
        return status;
    }

    public CategoriaLivro getCategoria() {
        return categoria;
    }

    public Editora getEditora() {
        return editora;
    }

    private static String validarTextoObrigatorio(String texto, String mensagem) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException(mensagem);
        }
        return texto.trim();
    }

    private static BigDecimal validarNaoNegativo(BigDecimal valor, String mensagem) {
        Objects.requireNonNull(valor, mensagem);
        if (valor.signum() < 0) {
            throw new IllegalArgumentException(mensagem);
        }
        return valor;
    }

    private static void validarPositivo(BigDecimal valor, String mensagem) {
        Objects.requireNonNull(valor, mensagem);
        if (valor.signum() <= 0) {
            throw new IllegalArgumentException(mensagem);
        }
    }
}
