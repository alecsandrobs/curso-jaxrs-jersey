package br.com.alura.loja.resource;

import br.com.alura.loja.dao.ProjetoDAO;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.modelo.Projeto;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

@Path("projetos")
public class ProjetoResource {

    private URI getUri(Long... id) {
        final String RESOURCE = "projetos";
        if (id.length > 0) {
            return URI.create(String.format("/%s/%s", RESOURCE, id[0]));
        } else {
            return URI.create(String.format("/%s", RESOURCE));
        }
    }

    @GET
    @Produces(APPLICATION_XML)
    public List<Projeto> get() {
        return new ProjetoDAO().busca();

    }

    @GET
    @Path("{id}")
    @Produces(APPLICATION_XML)
    public Projeto getById(@PathParam("id") Long id) {
        Projeto projeto = new ProjetoDAO().busca(id);
        return projeto;
    }

    @GET
    @Path("json/{id}")
    @Produces(APPLICATION_JSON)
    public String buscaJson(@PathParam("id") Long id) {
        Projeto projeto = new ProjetoDAO().busca(id);
        return projeto.toJson();
    }

    @POST
    @Consumes(APPLICATION_XML)
    public Response post(Projeto projeto) {
        new ProjetoDAO().adiciona(projeto);
        return Response.created(getUri(projeto.getId())).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        new ProjetoDAO().remove(id);
        return Response.ok().build();
    }

}