package br.com.alura.loja;

import br.com.alura.loja.modelo.Projeto;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import static junit.framework.Assert.assertEquals;

public class ProjetoTest {

    private static final String URI = "http://localhost:8080";
    private static final String XML = "xml";
    private static final String JSON = "json";

    public String getProjeto1(String type) {
        return String.format("/projetos/%s/1", type);
    }

    @Before
    public void setUp() {
        Servidor.start();
    }

    @After
    public void terminate() {
        Servidor.stop();
    }

    @Test
    public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperadoXml() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(URI);
        String conteudo = target.path(getProjeto1(XML)).request().get(String.class);
        Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
        assertEquals("Minha loja", projeto.getNome());
        assertEquals(2014, projeto.getAnoDeInicio());
    }

    @Test
    public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperadoJson() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(URI);
        String conteudo = target.path(getProjeto1(JSON)).request().get(String.class);
        Projeto projeto = new Gson().fromJson(conteudo, Projeto.class);
        assertEquals("Minha loja", projeto.getNome());
        assertEquals(2014, projeto.getAnoDeInicio());
    }
}