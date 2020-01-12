package no.spk.felles.ws.client;

import java.net.URI;
import java.net.http.HttpHeaders;

public class HttpServerError extends RuntimeException {
    private URI uri;
    private Method method;
    private final int statusCode;
    private final HttpHeaders headers;
    private final String body;

    public HttpServerError(URI uri, Method method, int statusCode, HttpHeaders headers, String body) {
        this.uri = uri;
        this.method = method;
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
    }

    public URI getUri() {
        return uri;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "HttpServerError{" +
                "uri=" + uri +
                ", method=" + method +
                ", statusCode=" + statusCode +
                ", headers=" + headers +
                ", body='" + body + '\'' +
                '}';
    }
}
