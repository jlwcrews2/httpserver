package no.jlwcrews;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpServer {

    private final String crlf = "/r/n";
    private final ServerSocket serverSocket;
    private PrintWriter out;
    private BufferedReader in;

    public HttpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void startServer() throws IOException {

        while(true) {
            try{
                Socket clientSocket = serverSocket.accept();
                out = new PrintWriter(clientSocket.getOutputStream());
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                var requestLine = readRequestLine();
                if (requestLine == null) continue;
                var httpMethod = requestLine.split(" ")[0];
                System.out.println(requestLine);
                var headers = readHeaders();
                headers.forEach((key, value) -> System.out.println(key + ":" + value));
                if (httpMethod.equals("POST") || httpMethod.equals("PUT")) {
                    var body = readBody(headers);
                    System.out.println(body);
                }
                sendResponse();
                clientSocket.close();
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    private String readRequestLine() throws IOException {
        return in.readLine();
    }

    private Map<String, String> readHeaders() throws IOException {
        Map<String, String> headers = new HashMap<>();
        String line;
        while((line = in.readLine()) != null){
            if (line.isEmpty()) break;
            String[] parts = line.split(":");
            headers.put(parts[0].trim(), parts[1].trim());
        }
        return headers;
    }

    private String readBody(Map<String, String> headers) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int contentLength = Integer.parseInt(headers.get("Content-Length"));
        for (int i = 0; i < contentLength; i++) {
            var c = (char) in.read();
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    private void sendResponse(){
        String response = "<html><h1>Hello from the server</h1></html>";
        String responseLine = "HTTP/1.1 200 OK";
        String contentType = "Content-type:text/html";
        String contentLength = "Content-length:" + response.length();
        out.println(responseLine);
        out.println(contentType);
        out.println(contentLength);
        out.println();
        out.println(response);
        out.println();
        out.flush();
    }


}
