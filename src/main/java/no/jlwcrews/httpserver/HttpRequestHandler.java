package no.jlwcrews.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpRequestHandler {

    public static HttpResponse handleRequest(BufferedReader in) throws IOException {

        HttpRequest request = new HttpRequest();

        parseRequestLine(in, request);
        parseHeaders(in, request);
        if (Objects.equals(request.getHttpMethod(), "POST")) parseBody(in, request);
        System.out.println(request);
        return createResponse();
    }

    private static HttpResponse createResponse() {
        HttpResponse response = new HttpResponse();
        String responseLine = "HTTP/1.1 200 OK";
        String body = "<H1>Hello this a cool and excellent server</H1>";
        response.setHttpStatusLine(responseLine);
        response.setHeaders(Map.of("Content-Type", "text/html", "Content-Length", String.valueOf(body.length())));
        response.setBody(body);
        return response;
    }

    private static void parseBody(BufferedReader in, HttpRequest request) throws IOException {
        StringBuilder builder = new StringBuilder();
        int length = Integer.parseInt(request.getHeaders().get("Content-Length"));
        for (int i = 0; i < length; i++) {
            builder.append( (char) in.read());
        }
        request.setBody(builder.toString());
    }

    private static void parseHeaders(BufferedReader in, HttpRequest request) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String currentHeaderLine = in.readLine();
        while(!currentHeaderLine.isEmpty()) {
            String[] headerParts = currentHeaderLine.split(":");
            headers.put(headerParts[0], headerParts[1]);
            currentHeaderLine = in.readLine();
        }
        request.setHeaders(headers);
    }

    private static void parseRequestLine(BufferedReader in, HttpRequest request) throws IOException {
        String[] requestLine = in.readLine().split(" ");
        request.setHttpMethod(requestLine[0]);
        request.setHttpResource(requestLine[1]);
        request.setHttpVersion(requestLine[2]);
    }
}