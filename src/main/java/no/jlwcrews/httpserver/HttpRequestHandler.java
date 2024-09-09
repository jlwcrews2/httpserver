package no.jlwcrews.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpRequestHandler extends Thread {

    private final Socket clientSocket;
    private final PrintWriter out;
    private final BufferedReader in;

    public HttpRequestHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.out = new PrintWriter(clientSocket.getOutputStream());
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    @Override
    public void run() {
        System.out.println("Client thread started");
        HttpRequest request = new HttpRequest();

        try {
            parseRequestLine(request);
            parseHeaders(request);
            if (Objects.equals(request.getHttpMethod(), "POST")) parseBody(request);
            System.out.println(request);

            sendResponse(new HttpResponseBuilder(request).build());
            System.out.println("Client thread finished");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void parseBody(HttpRequest request) throws IOException {
        StringBuilder builder = new StringBuilder();
        int length = Integer.parseInt(request.getHeaders().get("Content-Length"));
        for (int i = 0; i < length; i++) {
            builder.append( (char) in.read());
        }
        request.setBody(builder.toString());
    }

    private void parseHeaders(HttpRequest request) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String currentHeaderLine = in.readLine();
        while(!currentHeaderLine.isEmpty()) {
            String[] headerParts = currentHeaderLine.split(":");
            headers.put(headerParts[0], headerParts[1]);
            currentHeaderLine = in.readLine();
        }
        request.setHeaders(headers);
    }

    private void parseRequestLine(HttpRequest request) throws IOException {
        String[] requestLine = in.readLine().split(" ");
        request.setHttpMethod(requestLine[0]);
        request.setHttpResource(requestLine[1]);
        request.setHttpVersion(requestLine[2]);
    }

    private void sendResponse(HttpResponse response) throws IOException {
        out.println(response.getHttpStatusLine());
        response.getHeaders().forEach((key, value) -> out.println(key + ":" + value));
        out.println();
        out.println(response.getBody());
        out.close();
        in.close();
        clientSocket.close();
    }
}