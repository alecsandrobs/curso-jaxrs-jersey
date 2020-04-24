package br.com.alura.loja;

import br.com.alura.loja.modelo.Projeto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import static junit.framework.Assert.assertEquals;

public class ProjetoTest {

    private Client client;
    private WebTarget target;
    private static final String URI = "http://localhost:8080";
    private static final String RESOURCE = "/projetos";

    @Before
    public void setUp() {
        Servidor.start();
//        ClientConfig config = new ClientConfig();
//        config.register(LocalizationMessages.LOGGING_GLOBAL_REQUEST_FILTERS());
//        client = ClientBuilder.newClient(config);
        client = ClientBuilder.newClient();
        target = client.target(URI);
    }

    @After
    public void terminate() {
        Servidor.stop();
    }

    @Test
    public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {
        Projeto projeto = target.path(RESOURCE + "/1").request().get(Projeto.class);
        assertEquals("Minha loja", projeto.getNome());
        assertEquals(2014, projeto.getAnoDeInicio());
    }
}