package br.com.alura.loja.dao;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class CarrinhoDAO {

    private static AtomicLong contador = new AtomicLong(1L);
    public static List<Carrinho> carrinhos = new ArrayList<Carrinho>();

    static {
        Produto videogame = new Produto(6237, "Videogame 4", 4000, 1);
        Produto esporte = new Produto(3467, "Jogo de esporte", 60, 2);
        Carrinho carrinho1 = new Carrinho()
                .adiciona(videogame)
                .adiciona(esporte)
                .para("Rua Vergueiro 3185, 8 andar", "S�o Paulo")
                .setId(contador.getAndIncrement());
        carrinhos.add(carrinho1);

        Produto caboAdaptador = new Produto(1234, "Cabo adaptador", 36, 3);
        Produto notebook = new Produto(4321, "Notebook Lenovo", 3199.99, 1);
        Produto celular = new Produto(1111, "Smartphone Xiaomi", 2198.98, 1);
        Carrinho carrinho2 = new Carrinho()
                .adiciona(caboAdaptador)
                .adiciona(notebook)
                .adiciona(celular)
                .para("Rua Vergueiro 3185, 8 andar", "S�o Paulo")
                .setId(contador.getAndIncrement());
        carrinhos.add(carrinho2);
    }

    public void adiciona(Carrinho carrinho) {
        long id = contador.getAndIncrement();
        carrinho.setId(id);
        carrinhos.add(carrinho);
    }

    public List<Carrinho> busca() {
        return carrinhos;
    }

    public Carrinho busca(Long id) {
        for (Carrinho carrinho : carrinhos) {
            if (carrinho.getId() == id) {
                return carrinho;
            }
        }
        return null;
    }

    public void remove(Long id) {
        Iterator<Carrinho> iterator = carrinhos.iterator();
        while (iterator.hasNext()) {
            Carrinho carrinho = iterator.next();
            if (carrinho.getId() == id) {
                iterator.remove();
            }
        }
    }

}
