package br.com.alura.loja;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClienteTest {

    private Client client;
    private WebTarget target;
    private static final String URI = "http://localhost:8080";
    private static final String XML = "xml";
    private static final String JSON = "json";

    public String getCarrinhos(String type) {
        return String.format("/carrinhos/%s", type);
    }

    public String getCarrinho1(String type) {
        return String.format("%s/1", getCarrinhos(type));
    }

    @Before
    public void setUp() {
        Servidor.start();
        ClientConfig config = new ClientConfig();

        config.register(LocalizationMessages.LOGGING_GLOBAL_REQUEST_FILTERS());
        client = ClientBuilder.newClient(config);
        target = client.target(URI);
    }

    @After
    public void terminate() {
        Servidor.stop();
    }

    @Test
    public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperadoXml() {
        String conteudo = target.path(getCarrinho1(XML)).request().get(String.class);
        Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
        assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
    }

    @Test
    public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperadoJson() {
        String conteudo = target.path(getCarrinho1(JSON)).request().get(String.class);
        Carrinho carrinho = new Gson().fromJson(conteudo, Carrinho.class);
        assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
    }

    @Test
    public void testaQueSuportaNovosCarrinhosXml() {
        Carrinho carrinho = new Carrinho();
        carrinho.adiciona(new Produto(314L, "Tablet", 999, 1));
        carrinho.setRua("Rua Vergueiro");
        carrinho.setCidade("Sao Paulo");
        String xml = carrinho.toXML();

        Entity<String> entity = Entity.entity(xml, APPLICATION_XML);

        Response response = target.path(getCarrinhos(XML)).request().post(entity);
        assertEquals(201, response.getStatus());
        String location = response.getHeaderString("Location");
        String conteudo = client.target(location).request().get(String.class);
        assertTrue(conteudo.contains("Tablet"));
    }

    @Test
    public void testaQueSuportaNovosCarrinhosJson() {
        Carrinho carrinho = new Carrinho();
        carrinho.adiciona(new Produto(314L, "Tablet", 999, 1));
        carrinho.setRua("Rua Vergueiro");
        carrinho.setCidade("Sao Paulo");
        String xml = carrinho.toJson();

        Entity<String> entity = Entity.entity(xml, APPLICATION_JSON);

        Response response = target.path(getCarrinhos(JSON)).request().post(entity);
        assertEquals(201, response.getStatus());
        String location = response.getHeaderString("Location");
        String conteudo = client.target(location).request().get(String.class);
        assertTrue(conteudo.contains("Tablet"));
    }
}