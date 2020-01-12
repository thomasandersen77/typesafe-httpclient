package no.spk.felles.ws.client;

import java.net.URI;
import java.net.http.HttpHeaders;

public class HttpClientError extends RuntimeException {
    private final URI uri;
    private final Method method;
    private final int statusCode;
    private final HttpHeaders headers;
    private final String body;

    public HttpClientError(URI uri, Method method, int statusCode, HttpHeaders headers, String body) {
        this.uri = uri;
        this.method = method;
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
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
        return "HttpClientError{" +
                "uri=" + uri +
                ", method=" + method +
                ", statusCode=" + statusCode +
                ", headers=" + headers +
                ", body='" + body + '\'' +
                '}';
    }
}
