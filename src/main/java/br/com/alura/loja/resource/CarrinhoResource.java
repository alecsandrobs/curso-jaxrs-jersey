package br.com.alura.loja.resource;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

@Path("carrinhos")
public class CarrinhoResource {

    private final String XML = "xml";
    private final String JSON = "json";

    private URI getUri(String type, Long id) {
        return URI.create(String.format("/carrinhos/%s/%d", type, id));
    }

    @GET()
    @Path("xml/{id}")
    @Produces(APPLICATION_XML)
    public String getByIdXml(@PathParam("id") Long id) {
        Carrinho carrinho = new CarrinhoDAO().busca(id);
        return carrinho.toXML();
    }

    @GET()
    @Path("json/{id}")
    @Produces(APPLICATION_JSON)
    public String getByIdJson(@PathParam("id") Long id) {
        Carrinho carrinho = new CarrinhoDAO().busca(id);
        return carrinho.toJson();
    }

    @POST
    @Path("xml")
    @Consumes(APPLICATION_XML)
    public Response postXml(String conteudo) {
        Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
        new CarrinhoDAO().adiciona(carrinho);
        return Response.created(getUri(XML, carrinho.getId())).build();
    }

    @POST
    @Path("json")
    @Consumes(APPLICATION_JSON)
    public Response postJson(String conteudo) {
        Carrinho carrinho = new Gson().fromJson(conteudo, Carrinho.class);
        new CarrinhoDAO().adiciona(carrinho);
        return Response.created(getUri(JSON, carrinho.getId())).build();
    }

    @DELETE
    @Path("xml/{carrinhoId}/produtos/{id}")
    public Response deleteXml(@PathParam("carrinhoId") Long carrinhoId, @PathParam("id") Long id) {
        Carrinho carrinho = new CarrinhoDAO().busca(carrinhoId);
        carrinho.remove(id);
        return Response.ok().build();
    }

    @DELETE
    @Path("json/{carrinhoId}/produtos/{id}")
    public Response deleteJson(@PathParam("carrinhoId") Long carrinhoId, @PathParam("id") Long id) {
        Carrinho carrinho = new CarrinhoDAO().busca(carrinhoId);
        carrinho.remove(id);
        return Response.ok().build();
    }

    @PUT
    @Path("xml/{carrinhoId}/produtos/{id}/quantidade")
    public Response putXml(String conteudo, @PathParam("carrinhoId") Long carrinhoId, @PathParam("id") Long id) {
        Carrinho carrinho = new CarrinhoDAO().busca(carrinhoId);
        Produto produto = (Produto) new XStream().fromXML(conteudo);
        carrinho.troca(produto);
        return Response.ok().build();
    }

    @PUT
    @Path("json/{carrinhoId}/produtos/{id}/quantidade")
    public Response putJson(String conteudo, @PathParam("carrinhoId") Long carrinhoId, @PathParam("id") Long id) {
        Carrinho carrinho = new CarrinhoDAO().busca(carrinhoId);
        Produto produto = new Gson().fromJson(conteudo, Produto.class);
        carrinho.trocaQuantidade(produto);
        return Response.ok().build();
    }


}