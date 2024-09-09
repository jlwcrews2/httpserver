package no.jlwcrews.httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    public void startServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Server started");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            new HttpRequestHandler(clientSocket).start();
        }
    }


}