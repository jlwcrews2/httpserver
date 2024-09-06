package no.jlwcrews.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    public void startServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        while(true) {
            Socket clientSocket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            HttpResponse response = HttpRequestHandler.handleRequest(in);

            sendResponse(new PrintWriter(clientSocket.getOutputStream()), response);
            in.close();
            clientSocket.close();
        }
    }

    private void sendResponse(PrintWriter out, HttpResponse response) {
        out.println(response.getHttpStatusLine());
        response.getHeaders().forEach((key, value) -> out.println(key + ":" + value));
        out.println();
        out.println(response.getBody());
        out.close();
    }
}