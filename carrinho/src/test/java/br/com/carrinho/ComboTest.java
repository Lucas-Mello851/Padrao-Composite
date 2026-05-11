package br.com.carrinho;

import br.com.carrinho.composite.Combo;
import br.com.carrinho.model.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Combo")
class ComboTest {

    private Produto mouse;
    private Produto teclado;
    private Combo combo;

    @BeforeEach
    void setUp() {
        mouse   = new Produto("Mouse",   new BigDecimal("100.00"), "Periférico");
        teclado = new Produto("Teclado", new BigDecimal("200.00"), "Periférico");
        combo   = new Combo("Kit Gamer", "Setup gamer");
    }

    @Test
    @DisplayName("Combo vazio deve ter preço zero")
    void comboVazioDeveSerPrecoZero() {
        assertEquals(BigDecimal.ZERO.setScale(2), combo.getPreco());
    }

    @Test
    @DisplayName("Deve somar preços dos filhos")
    void deveSomarPrecosFilhos() {
        combo.adicionar(mouse);
        combo.adicionar(teclado);
        assertEquals(new BigDecimal("300.00"), combo.getPreco());
    }

    @Test
    @DisplayName("Deve aplicar desconto corretamente")
    void deveAplicarDesconto() {
        Combo comboDesc = new Combo("Kit", "Desc", 0.10);
        comboDesc.adicionar(mouse);
        comboDesc.adicionar(teclado);
        assertEquals(new BigDecimal("270.00"), comboDesc.getPreco());
    }

    @Test
    @DisplayName("AUTO-RELACIONAMENTO: Combo dentro de Combo deve somar recursivamente")
    void deveCalcularPrecoComAutoRelacionamento() {
        Combo kitIluminacao = new Combo("Kit Iluminação", "LEDs");
        kitIluminacao.adicionar(new Produto("Fita LED",   new BigDecimal("60.00"), "Iluminação"));
        kitIluminacao.adicionar(new Produto("Controlador", new BigDecimal("90.00"), "Iluminação"));


        combo.adicionar(mouse);
        combo.adicionar(teclado);
        combo.adicionar(kitIluminacao);

        assertEquals(new BigDecimal("450.00"), combo.getPreco());
    }

    @Test
    @DisplayName("AUTO-RELACIONAMENTO: três níveis de aninhamento")
    void deveSuportarTresNiveisAninhamento() {
        Produto caneta  = new Produto("Caneta",  new BigDecimal("5.00"), "Pap");
        Produto borracha = new Produto("Borracha", new BigDecimal("3.00"), "Pap");
        Produto regua   = new Produto("Régua",   new BigDecimal("2.00"), "Pap");

        Combo nivel3 = new Combo("Nivel3", "N3");
        nivel3.adicionar(regua);

        Combo nivel2 = new Combo("Nivel2", "N2");
        nivel2.adicionar(borracha);
        nivel2.adicionar(nivel3);

        Combo nivel1 = new Combo("Nivel1", "N1");
        nivel1.adicionar(caneta);
        nivel1.adicionar(nivel2);

        assertEquals(new BigDecimal("10.00"), nivel1.getPreco());
    }

    @Test
    @DisplayName("isComposto() deve retornar true")
    void deveSerComposto() {
        assertTrue(combo.isComposto());
    }

    @Test
    @DisplayName("Deve adicionar e remover itens corretamente")
    void deveAdicionarERemoverItens() {
        combo.adicionar(mouse);
        assertEquals(1, combo.getFilhos().size());
        combo.remover(mouse);
        assertTrue(combo.getFilhos().isEmpty());
    }

    @Test
    @DisplayName("getFilhos() deve ser imutável")
    void getFilhosDeveSerImutavel() {
        combo.adicionar(mouse);
        assertThrows(UnsupportedOperationException.class,
            () -> combo.getFilhos().add(teclado));
    }

    @Test
    @DisplayName("totalDeItens() deve contar recursivamente")
    void deveTotalizarItensRecursivamente() {
        Combo sub = new Combo("Sub", "s");
        sub.adicionar(new Produto("P1", BigDecimal.ONE, "C"));
        sub.adicionar(new Produto("P2", BigDecimal.ONE, "C"));

        combo.adicionar(mouse);
        combo.adicionar(sub);

        assertEquals(3, combo.totalDeItens());
    }

    @Test
    @DisplayName("Deve lançar exceção com desconto inválido")
    void deveLancarExcecaoDescontoInvalido() {
        assertThrows(IllegalArgumentException.class,
            () -> new Combo("X", "Y", 1.5));
    }

    @Test
    @DisplayName("Deve lançar NullPointerException ao adicionar item nulo")
    void deveLancarExcecaoItemNulo() {
        assertThrows(NullPointerException.class,
            () -> combo.adicionar(null));
    }
}
