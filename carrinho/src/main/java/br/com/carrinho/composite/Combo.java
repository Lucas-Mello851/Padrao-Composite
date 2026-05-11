package br.com.carrinho.composite;

import br.com.carrinho.model.ItemCarrinho;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Combo implements ItemCarrinho {

    private final String nome;
    private final String descricaoCombo;

    private final double desconto;

    private final List<ItemCarrinho> itens;

    public Combo(String nome, String descricaoCombo) {
        this(nome, descricaoCombo, 0.0);
    }

    public Combo(String nome, String descricaoCombo, double desconto) {
        Objects.requireNonNull(nome, "Nome do combo não pode ser nulo.");
        if (desconto < 0.0 || desconto > 1.0) {
            throw new IllegalArgumentException("Desconto deve estar entre 0.0 e 1.0.");
        }
        this.nome = nome;
        this.descricaoCombo = descricaoCombo;
        this.desconto = desconto;
        this.itens = new ArrayList<>();
    }

    @Override
    public void adicionar(ItemCarrinho item) {
        Objects.requireNonNull(item, "Item não pode ser nulo.");
        itens.add(item);
    }

    @Override
    public void remover(ItemCarrinho item) {
        itens.remove(item);
    }

    @Override
    public List<ItemCarrinho> getFilhos() {
        return Collections.unmodifiableList(itens);
    }

    @Override
    public boolean isComposto() {
        return true;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public BigDecimal getPreco() {
        BigDecimal subtotal = itens.stream()
                .map(ItemCarrinho::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (desconto > 0.0) {
            BigDecimal fator = BigDecimal.ONE.subtract(BigDecimal.valueOf(desconto));
            subtotal = subtotal.multiply(fator);
        }

        return subtotal.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getDescricao() {
        String descontoStr = desconto > 0
                ? String.format(" | Desconto: %.0f%%", desconto * 100)
                : "";
        return String.format("[Combo] %s%s | Total: R$ %.2f | %s",
                nome, descontoStr, getPreco(), descricaoCombo);
    }

    @Override
    public void exibir(int nivel) {
        String indent = "  ".repeat(nivel);
        System.out.printf("%s %s%n", indent, getDescricao());
        for (ItemCarrinho item : itens) {
            item.exibir(nivel + 1);
        }
    }

    public double getDesconto() {
        return desconto;
    }

    public boolean isEmpty() {
        return itens.isEmpty();
    }

    public int totalDeItens() {
        return itens.stream()
                .mapToInt(i -> i.isComposto() ? ((Combo) i).totalDeItens() : 1)
                .sum();
    }

    @Override
    public String toString() {
        return getDescricao();
    }
}
