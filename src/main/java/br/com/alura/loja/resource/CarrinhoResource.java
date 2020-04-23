package br.com.alura.loja.resource;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("carrinhos")
public class CarrinhoResource {

    @GET()
    @Path("xml")
    @Produces(MediaType.APPLICATION_XML)
    public String buscaXml() {
        Carrinho carrinho = new CarrinhoDAO().busca(1l);
        return carrinho.toXML();
    }

    @GET()
    @Path("json")
    @Produces(MediaType.APPLICATION_JSON)
    public String buscaJson() {
        Carrinho carrinho = new CarrinhoDAO().busca(1l);
        return carrinho.toJSON();
    }

}