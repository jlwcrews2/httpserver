package no.jlwcrews;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    private final ServerSocket serverSocket;
    private PrintWriter out;

    public HttpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void startServer() throws IOException {

        while(true) {
            try{
                Socket clientSocket = serverSocket.accept();
                out = new PrintWriter(clientSocket.getOutputStream());
                var response = new HttpRequestProcessor().processRequest(clientSocket);
                if (response == null) continue;
                sendResponse(response);
                clientSocket.close();
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    private void sendResponse(HttpResponse response){
        String responseLine = response.getStatusLine();
        out.println(responseLine);
        response.getHeaders().forEach((key, value) -> out.println(key + ":" + value));
        out.println();
        out.println(response.getBody());
        out.println();
        out.flush();
    }


}
