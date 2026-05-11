package br.com.carrinho.service;

import br.com.carrinho.composite.Combo;
import br.com.carrinho.model.ItemCarrinho;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CarrinhoService {

    private final List<ItemCarrinho> itens;
    private final String nomeCliente;

    public CarrinhoService(String nomeCliente) {
        Objects.requireNonNull(nomeCliente, "Nome do cliente não pode ser nulo.");
        this.nomeCliente = nomeCliente;
        this.itens = new ArrayList<>();
    }

    public void adicionar(ItemCarrinho item) {
        Objects.requireNonNull(item, "Item não pode ser nulo.");
        itens.add(item);
    }

    public void remover(ItemCarrinho item) {
        itens.remove(item);
    }

    public List<ItemCarrinho> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public BigDecimal calcularTotal() {
        return itens.stream()
                .map(ItemCarrinho::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public void exibirCarrinho() {
        System.out.printf("  CARRINHO DE: %s%n", nomeCliente.toUpperCase());


        if (itens.isEmpty()) {
            System.out.println("  (carrinho vazio)");
        } else {
            itens.forEach(item -> item.exibir(1));
        }

        System.out.printf("  TOTAL GERAL: R$ %.2f%n", calcularTotal());
    }

    public boolean isEmpty() {
        return itens.isEmpty();
    }

    public int quantidadeItens() {
        return itens.size();
    }

    public String getNomeCliente() {
        return nomeCliente;
    }
}
