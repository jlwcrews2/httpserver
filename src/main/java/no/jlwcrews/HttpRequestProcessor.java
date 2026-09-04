package no.jlwcrews;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestProcessor {

    public HttpResponse processRequest(Socket client) throws IOException {
        HttpRequest request = parseRequest(client);

        if (request == null) return null;
        var result = switch (request.getUri()) {
            case "/anagram" -> {
                var wordList = request.getBody().split(",");
                yield new AnagramFinder().findAnagrams(Arrays.stream(wordList).toList()).toString();
            }
            default -> "<h1>Other part of server that doesn't do anything</h1>";
        };
        return new HttpResponse(
                "HTTP/1.1",
                "200",
                "OK",
                Map.of("Content-length", String.valueOf(result.length()),
                        "Content-type", "test/plain"),
                result
        );
    }

    private HttpRequest parseRequest(Socket client) throws IOException {
        var in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        var requestLine = readRequestLine(in);
        if (requestLine == null) return null;
        var headers = readHeaders(in);
        var body = readBody(in, headers);
        return new HttpRequest(
                requestLine.split(" ")[0],
                requestLine.split(" ")[1],
                requestLine.split(" ")[2],
                headers,
                body
        );
    }

    private String readRequestLine(BufferedReader in) throws IOException {
        return in.readLine();
    }

    private Map<String, String> readHeaders(BufferedReader in) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String line;
        while((line = in.readLine()) != null){
            if (line.isEmpty()) break;
            String[] parts = line.split(":");
            headers.put(parts[0].trim(), parts[1].trim());
        }
        return headers;
    }

    private String readBody(BufferedReader in, Map<String, String> headers) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int contentLength = Integer.parseInt(headers.get("Content-Length"));
        for (int i = 0; i < contentLength; i++) {
            var c = (char) in.read();
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

}
