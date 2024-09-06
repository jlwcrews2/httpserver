package no.jlwcrews;

import no.jlwcrews.httpserver.HttpServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = new HttpServer();
        server.startServer();
    }
}