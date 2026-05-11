package br.com.carrinho.model;

import java.math.BigDecimal;
import java.util.List;

public interface ItemCarrinho {

    String getNome();

    BigDecimal getPreco();

    String getDescricao();

    void exibir(int nivel);

    default void adicionar(ItemCarrinho item) {
        throw new UnsupportedOperationException(
            "Esta operação não é suportada por itens simples (folha)."
        );
    }

    default void remover(ItemCarrinho item) {
        throw new UnsupportedOperationException(
            "Esta operação não é suportada por itens simples (folha)."
        );
    }

    default List<ItemCarrinho> getFilhos() {
        return List.of();
    }

    default boolean isComposto() {
        return false;
    }
}
