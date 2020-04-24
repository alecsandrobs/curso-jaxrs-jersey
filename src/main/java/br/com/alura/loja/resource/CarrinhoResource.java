package br.com.alura.loja.resource;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

@Path("carrinhos")
public class CarrinhoResource {

    private URI getUri(Long... id) {
        final String RESOURCE = "carrinhos";
        if (id.length > 0) {
            return URI.create(String.format("/%s/%s", RESOURCE, id[0]));
        } else {
            return URI.create(String.format("/%s", RESOURCE));
        }
    }

    @GET()
    @Produces(APPLICATION_XML)
    public List<Carrinho> get() {
        return new CarrinhoDAO().busca();
    }

    @GET()
    @Path("/{id}")
    @Produces(APPLICATION_XML)
    public Carrinho getById(@PathParam("id") Long id) {
        Carrinho carrinho = new CarrinhoDAO().busca(id);
        return carrinho;
    }

    @GET()
    @Path("json/{id}")
    @Produces(APPLICATION_JSON)
    public String getByIdJson(@PathParam("id") Long id) {
        Carrinho carrinho = new CarrinhoDAO().busca(id);
        return carrinho.toJson();
    }

    @POST
    @Consumes(APPLICATION_XML)
    public Response post(Carrinho carrinho) {
        new CarrinhoDAO().adiciona(carrinho);
        return Response.created(getUri(carrinho.getId())).build();
    }

    @PUT
    @Path("{carrinhoId}/produtos/{id}/quantidade")
    public Response put(Produto produto, @PathParam("carrinhoId") Long carrinhoId, @PathParam("id") Long id) {
        Carrinho carrinho = new CarrinhoDAO().busca(carrinhoId);
        carrinho.troca(produto);
        return Response.ok().build();
    }

    @DELETE
    @Path("{carrinhoId}/produtos/{id}")
    public Response delete(@PathParam("carrinhoId") Long carrinhoId, @PathParam("id") Long id) {
        Carrinho carrinho = new CarrinhoDAO().busca(carrinhoId);
        carrinho.remove(id);
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        new CarrinhoDAO().remove(id);
        return Response.ok().build();
    }


}