package br.com.alura.loja.modelo;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Carrinho {

    private List<Produto> produtos = new ArrayList<Produto>();
    private String rua;
    private String cidade;
    private long id;

    public Carrinho adiciona(Produto produto) {
        produtos.add(produto);
        return this;
    }

    public Carrinho para(String rua, String cidade) {
        this.rua = rua;
        this.cidade = cidade;
        return this;
    }

    public Carrinho setId(long id) {
        this.id = id;
        return this;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public long getId() {
        return id;
    }

    public void remove(long id) {
        for (Iterator iterator = produtos.iterator(); iterator.hasNext(); ) {
            Produto produto = (Produto) iterator.next();
            if (produto.getId() == id) {
                iterator.remove();
            }
        }
    }

    public void troca(Produto produto) {
        remove(produto.getId());
        adiciona(produto);
    }

    public void trocaQuantidade(Produto produto) {
        for (Iterator iterator = produtos.iterator(); iterator.hasNext(); ) {
            Produto p = (Produto) iterator.next();
            if (p.getId() == produto.getId()) {
                p.setQuantidade(produto.getQuantidade());
                return;
            }
        }
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public String toXML() {
        return new XStream().toXML(this);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

}
