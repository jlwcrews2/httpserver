package no.jlwcrews;

import java.util.Map;

public class HttpResponse {

    private final String version;
    private final String statusCode;
    private final String statusMessage;
    private final Map<String, String> headers;
    private final String body;

    public HttpResponse(String version, String statusCode, String statusMessage, Map<String, String> headers, String body) {
        this.version = version;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.headers = headers;
        this.body = body;
    }

    public String getVersion() {
        return version;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public String getStatusLine(){
        return version + " " + statusCode + " " + statusMessage;
    }
}
