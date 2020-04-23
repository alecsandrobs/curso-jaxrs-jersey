package br.com.alura.loja;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Servidor {

    static HttpServer server;
    final static String URL = "http://localhost:8080/";
    final static String PATH = "br.com.alura.loja";

    public static void main(String[] args) throws IOException {
        start();
        System.out.println("Servidor rodando");
        System.in.read();
        stop();
    }

    static HttpServer start() {
        ResourceConfig config = new ResourceConfig().packages(PATH);
        URI uri = URI.create(URL);
        server = GrizzlyHttpServerFactory.createHttpServer(uri, config);
        return server;
    }

    static void stop() {
        server.shutdown();
    }
}