package br.com.alura.loja;

import br.com.alura.loja.modelo.Carrinho;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import static junit.framework.Assert.assertEquals;

public class ClienteTest {

    private static final String URI = "http://localhost:8080";
    private static final String XML = "xml";
    private static final String JSON = "json";

    public String getCarrinhos(String type) {
        return String.format("/carrinhos/%s", type);
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
        String conteudo = target.path(getCarrinhos(XML)).request().get(String.class);
        Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
        assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
    }

    @Test
    public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperadoJson() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(URI);
        String conteudo = target.path(getCarrinhos(JSON)).request().get(String.class);
        Carrinho carrinho = new Gson().fromJson(conteudo, Carrinho.class);
        assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
    }
}