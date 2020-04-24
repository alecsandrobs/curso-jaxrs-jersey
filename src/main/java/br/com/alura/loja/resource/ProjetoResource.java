package br.com.alura.loja.resource;

import br.com.alura.loja.dao.ProjetoDAO;
import br.com.alura.loja.modelo.Projeto;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

@Path("projetos")
public class ProjetoResource {

    private final String XML = "xml";
    private final String JSON = "json";

    private URI getUri(String type, Long id) {
        return URI.create(String.format("/carrinhos/%s/%d", type, id));
    }

    @GET
    @Path("xml/{id}")
    @Produces(APPLICATION_XML)
    public String buscaXml(@PathParam("id") Long id) {
        Projeto projeto = new ProjetoDAO().busca(id);
        return projeto.toXML();
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
    public Response postXml(String conteudo) {
        Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
        new ProjetoDAO().adiciona(projeto);
        return Response.created(getUri(XML, projeto.getId())).build();
    }

    @POST
    @Consumes(APPLICATION_JSON)
    public Response postJson(String conteudo) {
        Projeto projeto = new Gson().fromJson(conteudo, Projeto.class);
        new ProjetoDAO().adiciona(projeto);
        return Response.created(getUri(JSON, projeto.getId())).build();
    }

    @DELETE
    @Path("xml/{id}")
    public Response deleteXml(@PathParam("id") Long id) {
        new ProjetoDAO().remove(id);
        return Response.ok().build();
    }

    @DELETE
    @Path("json/{id}")
    public Response deleteJson(@PathParam("id") Long id) {
        new ProjetoDAO().remove(id);
        return Response.ok().build();
    }

}