package br.com.alura.loja;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import com.google.gson.Gson;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static junit.framework.Assert.assertEquals;

public class ClienteTest {

    private Client client;
    private WebTarget target;
    private static final String URI = "http://localhost:8080";
    private static final String RESOURCE = "/carrinhos";
    private static final String JSON = "json";

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
    public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {
        Carrinho carrinho = target.path(RESOURCE + "/1").request().get(Carrinho.class);
        assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
    }

    @Test
    public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperadoJson() {
        String conteudo = target.path(RESOURCE + "/json/1").request().get(String.class);
        Carrinho carrinho = new Gson().fromJson(conteudo, Carrinho.class);
        assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
    }

    @Test
    public void testaQueSuportaNovosCarrinhos() {
        Carrinho carrinho = new Carrinho();
        carrinho.adiciona(new Produto(314L, "Tablet", 999, 1));
        carrinho.setRua("Rua Vergueiro");
        carrinho.setCidade("Sao Paulo");

        Entity<Carrinho> entity = Entity.entity(carrinho, APPLICATION_XML);

        Response response = target.path(RESOURCE).request().post(entity);
        assertEquals(201, response.getStatus());
        String location = response.getHeaderString("Location");
        Carrinho carrinho1 = client.target(location).request().get(Carrinho.class);
        assertEquals("Tablet", carrinho1.getProdutos().get(0).getNome());
    }
}