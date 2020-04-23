package br.com.alura.loja.resource;

import br.com.alura.loja.dao.ProjetoDAO;
import br.com.alura.loja.modelo.Projeto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("projetos")
public class ProjetoResource {

    @GET
    @Path("xml")
    @Produces(MediaType.APPLICATION_XML)
    public String buscaXml() {
        Projeto projeto = new ProjetoDAO().busca(1l);
        return projeto.toXML();
    }

    @GET
    @Path("json")
    @Produces(MediaType.APPLICATION_JSON)
    public String buscaJson() {
        Projeto projeto = new ProjetoDAO().busca(1l);
        return projeto.toJSON();
    }
}