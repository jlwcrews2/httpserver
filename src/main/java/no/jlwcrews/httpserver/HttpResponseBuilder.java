package no.jlwcrews.httpserver;

import java.util.Map;

public class HttpResponseBuilder {

    private final HttpRequest request;

    public HttpResponseBuilder(HttpRequest request) {
        this.request = request;
    }

    public HttpResponse build(){
        HttpResponse response = new HttpResponse();

        switch (request.getHttpResource()) {
            case "/": response = defaultResponse();
            break;
            case "/account": response = accountResponse();
            break;
        }

        return response;
    }

    private HttpResponse accountResponse() {
        HttpResponse response = new HttpResponse();
        String accountNumber = request.getHttpResource();
        String responseLine = "HTTP/1.1 200 OK";
        response.setHttpStatusLine(responseLine);
        String body = "Here is the status of account " + accountNumber;
        response.setHeaders(Map.of("Content-Type", "text/plain", "Content-Length", String.valueOf(body.length())));
        response.setBody(body);
        return response;
    }

    private HttpResponse defaultResponse() {
        HttpResponse response = new HttpResponse();
        String responseLine = "HTTP/1.1 200 OK";
        String body = "<H1>Hello this a cool and excellent server</H1>";
        response.setHttpStatusLine(responseLine);
        response.setHeaders(Map.of("Content-Type", "text/html", "Content-Length", String.valueOf(body.length())));
        response.setBody(body);
        return response;

    }
}
