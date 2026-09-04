package no.jlwcrews;

import java.util.Map;

public class HttpRequest {
    private final String method;
    private final String uri;
    private final String version;
    private final Map<String, String> headers;
    private final String body;


    public HttpRequest(String method, String uri, String version, Map<String, String> headers, String body) {
        this.method = method;
        this.uri = uri;
        this.version = version;
        this.headers = headers;
        this.body = body;
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody(){
        return body;
    }
}
