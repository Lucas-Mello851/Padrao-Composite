package br.com.carrinho;

import br.com.carrinho.model.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Produto")
class ProdutoTest {

    private Produto produto;

    @BeforeEach
    void setUp() {
        produto = new Produto("Mouse Gamer", new BigDecimal("189.90"), "Periférico");
    }

    @Test
    @DisplayName("Deve calcular preço unitário quando quantidade = 1")
    void deveCalcularPrecoUnitario() {
        assertEquals(new BigDecimal("189.90"), produto.getPreco());
    }

    @Test
    @DisplayName("Deve calcular preço total com múltiplas unidades")
    void deveCalcularPrecoComQuantidade() {
        Produto caneta = new Produto("Caneta", new BigDecimal("3.50"), "Papelaria", 5);
        assertEquals(new BigDecimal("17.50"), caneta.getPreco());
    }

    @Test
    @DisplayName("Não deve ser composto (isComposto = false)")
    void naoDeveSerComposto() {
        assertFalse(produto.isComposto());
    }

    @Test
    @DisplayName("Deve lançar UnsupportedOperationException ao adicionar filho")
    void deveLancarExcecaoAoAdicionarFilho() {
        Produto outro = new Produto("Teclado", new BigDecimal("200.00"), "Periférico");
        assertThrows(UnsupportedOperationException.class, () -> produto.adicionar(outro));
    }

    @Test
    @DisplayName("Deve lançar UnsupportedOperationException ao remover filho")
    void deveLancarExcecaoAoRemoverFilho() {
        Produto outro = new Produto("Teclado", new BigDecimal("200.00"), "Periférico");
        assertThrows(UnsupportedOperationException.class, () -> produto.remover(outro));
    }

    @Test
    @DisplayName("getFilhos() deve retornar lista vazia")
    void deveRetornarListaVazia() {
        assertTrue(produto.getFilhos().isEmpty());
    }

    @Test
    @DisplayName("Deve lançar exceção com preço negativo")
    void deveLancarExcecaoComPrecoNegativo() {
        assertThrows(IllegalArgumentException.class,
            () -> new Produto("X", new BigDecimal("-1.00"), "Cat"));
    }

    @Test
    @DisplayName("Deve lançar exceção com quantidade zero")
    void deveLancarExcecaoComQuantidadeZero() {
        assertThrows(IllegalArgumentException.class,
            () -> new Produto("X", new BigDecimal("10.00"), "Cat", 0));
    }

    @Test
    @DisplayName("Deve lançar NullPointerException com nome nulo")
    void deveLancarExcecaoComNomeNulo() {
        assertThrows(NullPointerException.class,
            () -> new Produto(null, new BigDecimal("10.00"), "Cat"));
    }

    @Test
    @DisplayName("Deve retornar nome corretamente")
    void deveRetornarNome() {
        assertEquals("Mouse Gamer", produto.getNome());
    }

    @Test
    @DisplayName("Deve atualizar quantidade corretamente")
    void deveAtualizarQuantidade() {
        produto.setQuantidade(3);
        assertEquals(new BigDecimal("569.70"), produto.getPreco());
    }
}
