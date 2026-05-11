package br.com.carrinho;

import br.com.carrinho.composite.Combo;
import br.com.carrinho.model.Produto;
import br.com.carrinho.service.CarrinhoService;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {

        Produto headphone      = new Produto("Headphone Bluetooth",  new BigDecimal("299.90"), "Eletrônico");
        Produto mouseGamer     = new Produto("Mouse Gamer 12000 DPI", new BigDecimal("189.90"), "Periférico");
        Produto tecladoMec     = new Produto("Teclado Mecânico",  new BigDecimal("349.90"), "Periférico");
        Produto fitaLed        = new Produto("Fita LED RGB 5m",       new BigDecimal("59.90"),  "Iluminação");
        Produto controladorRgb = new Produto("Controlador RGB Smart", new BigDecimal("89.90"),  "Iluminação");
        Produto caneta         = new Produto("Caneta Esferográfica",  new BigDecimal("3.50"),   "Papelaria", 5);
        Produto caderno        = new Produto("Caderno Universitário", new BigDecimal("34.90"),  "Papelaria");

        Combo kitIluminacao = new Combo("Kit Iluminação RGB", "Ambiente gamer completo");
        kitIluminacao.adicionar(fitaLed);
        kitIluminacao.adicionar(controladorRgb);

        Combo kitGamer = new Combo("Kit Gamer Pro", "Setup completo para jogos", 0.10);
        kitGamer.adicionar(mouseGamer);
        kitGamer.adicionar(tecladoMec);
        kitGamer.adicionar(kitIluminacao);

        Combo kitEscritorio = new Combo("Kit Escritório", "Material de escritório essencial", 0.05);
        kitEscritorio.adicionar(caneta);
        kitEscritorio.adicionar(caderno);

        CarrinhoService carrinho = new CarrinhoService("João Silva");
        carrinho.adicionar(headphone);
        carrinho.adicionar(kitGamer);
        carrinho.adicionar(kitEscritorio);

        carrinho.exibirCarrinho();

        System.out.println();
        System.out.printf("  Total de itens no carrinho (top-level): %d%n", carrinho.quantidadeItens());
        System.out.printf("  Total de produtos no Kit Gamer: %d%n", kitGamer.totalDeItens());
    }
}
