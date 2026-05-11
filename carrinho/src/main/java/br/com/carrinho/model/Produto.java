package br.com.carrinho.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Produto implements ItemCarrinho {

    private final String nome;
    private final BigDecimal precoUnitario;
    private int quantidade;
    private final String categoria;

    public Produto(String nome, BigDecimal precoUnitario, String categoria) {
        this(nome, precoUnitario, categoria, 1);
    }

    public Produto(String nome, BigDecimal precoUnitario, String categoria, int quantidade) {
        Objects.requireNonNull(nome, "Nome não pode ser nulo.");
        Objects.requireNonNull(precoUnitario, "Preço não pode ser nulo.");
        if (precoUnitario.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Preço unitário não pode ser negativo.");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }
        this.nome = nome;
        this.precoUnitario = precoUnitario.setScale(2, RoundingMode.HALF_UP);
        this.categoria = categoria;
        this.quantidade = quantidade;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public BigDecimal getPreco() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade))
                            .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getDescricao() {
        return String.format("[Produto] %s | Qtd: %d | Unit: R$ %.2f | Total: R$ %.2f | Cat: %s",
                nome, quantidade, precoUnitario, getPreco(), categoria);
    }

    @Override
    public void exibir(int nivel) {
        String indent = "  ".repeat(nivel);
        System.out.printf("%s  %s%n", indent, getDescricao());
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }
        this.quantidade = quantidade;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return getDescricao();
    }
}
