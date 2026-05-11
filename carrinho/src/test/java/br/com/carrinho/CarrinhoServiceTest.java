package br.com.carrinho;

import br.com.carrinho.composite.Combo;
import br.com.carrinho.model.Produto;
import br.com.carrinho.service.CarrinhoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CarrinhoService")
class CarrinhoServiceTest {

    private CarrinhoService carrinho;
    private Produto headphone;
    private Combo kitGamer;

    @BeforeEach
    void setUp() {
        carrinho  = new CarrinhoService("João");
        headphone = new Produto("Headphone", new BigDecimal("299.90"), "Eletrônico");

        Produto mouse   = new Produto("Mouse",   new BigDecimal("100.00"), "Periférico");
        Produto teclado = new Produto("Teclado", new BigDecimal("200.00"), "Periférico");
        kitGamer = new Combo("Kit Gamer", "Setup gamer");
        kitGamer.adicionar(mouse);
        kitGamer.adicionar(teclado);
    }

    @Test
    @DisplayName("Carrinho vazio deve ter total zero")
    void carrinhoVazioDeveSerZero() {
        assertEquals(BigDecimal.ZERO.setScale(2), carrinho.calcularTotal());
    }

    @Test
    @DisplayName("Deve calcular total com produto simples")
    void deveCalcularTotalComProduto() {
        carrinho.adicionar(headphone);
        assertEquals(new BigDecimal("299.90"), carrinho.calcularTotal());
    }

    @Test
    @DisplayName("Deve calcular total com combo")
    void deveCalcularTotalComCombo() {
        carrinho.adicionar(kitGamer);
        assertEquals(new BigDecimal("300.00"), carrinho.calcularTotal());
    }

    @Test
    @DisplayName("Deve calcular total misturando Produto e Combo")
    void deveCalcularTotalMisto() {
        carrinho.adicionar(headphone);
        carrinho.adicionar(kitGamer);
        assertEquals(new BigDecimal("599.90"), carrinho.calcularTotal());
    }

    @Test
    @DisplayName("Deve calcular total com Combo com desconto")
    void deveCalcularTotalComDesconto() {
        Combo comboDesc = new Combo("Kit 10%", "desc", 0.10);
        comboDesc.adicionar(new Produto("P1", new BigDecimal("100.00"), "C"));
        comboDesc.adicionar(new Produto("P2", new BigDecimal("100.00"), "C"));
        carrinho.adicionar(comboDesc);
        assertEquals(new BigDecimal("180.00"), carrinho.calcularTotal());
    }

    @Test
    @DisplayName("Deve remover item do carrinho")
    void deveRemoverItem() {
        carrinho.adicionar(headphone);
        carrinho.adicionar(kitGamer);
        carrinho.remover(headphone);
        assertEquals(1, carrinho.quantidadeItens());
        assertEquals(new BigDecimal("300.00"), carrinho.calcularTotal());
    }

    @Test
    @DisplayName("isEmpty() deve retornar true quando vazio")
    void deveRetornarVerdadeiroParaCarrinhoVazio() {
        assertTrue(carrinho.isEmpty());
    }

    @Test
    @DisplayName("isEmpty() deve retornar false após adicionar item")
    void deveRetornarFalsoAposAdicionar() {
        carrinho.adicionar(headphone);
        assertFalse(carrinho.isEmpty());
    }

    @Test
    @DisplayName("getItens() deve ser imutável")
    void getItensDeveSerImutavel() {
        carrinho.adicionar(headphone);
        assertThrows(UnsupportedOperationException.class,
            () -> carrinho.getItens().add(kitGamer));
    }

    @Test
    @DisplayName("Deve lançar NullPointerException com nome nulo")
    void deveLancarExcecaoNomeNulo() {
        assertThrows(NullPointerException.class,
            () -> new CarrinhoService(null));
    }

    @Test
    @DisplayName("Deve retornar nome do cliente corretamente")
    void deveRetornarNomeCliente() {
        assertEquals("João", carrinho.getNomeCliente());
    }

    @Test
    @DisplayName("AUTO-RELACIONAMENTO no carrinho: Combo com sub-Combo")
    void deveCalcularComAutoRelacionamento() {
        Combo sub = new Combo("Sub Iluminação", "LEDs");
        sub.adicionar(new Produto("Fita LED",   new BigDecimal("60.00"),  "Ilum"));
        sub.adicionar(new Produto("Controlador", new BigDecimal("90.00"), "Ilum"));

        kitGamer.adicionar(sub);

        carrinho.adicionar(headphone);
        carrinho.adicionar(kitGamer);
        // TOTAL = 749.90
        assertEquals(new BigDecimal("749.90"), carrinho.calcularTotal());
    }
}
