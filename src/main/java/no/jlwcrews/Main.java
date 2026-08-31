package no.jlwcrews;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            var server = new HttpServer(8080);
            server.startServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}