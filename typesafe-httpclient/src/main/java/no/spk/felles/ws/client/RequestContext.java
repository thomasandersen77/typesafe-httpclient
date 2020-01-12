package no.spk.felles.ws.client;

import java.net.URI;
import java.util.Map;

class RequestContext {
    private final URI uri;
    private final Map<String, Object> headers;
    private final Method method;
    private final Object body;

    public RequestContext(URI uri, Map<String, Object> httpHeaders, Method method, Object body) {
        this.uri = uri;
        this.headers = httpHeaders;
        this.method = method;
        this.body = body;
    }

    public URI getUri() {
        return uri;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public Method getMethod() {
        return method;
    }

    public Object getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "RequestContext{" +
                "uri=" + uri +
                ", headers=" + headers +
                ", method=" + method +
                ", body=" + body +
                '}';
    }
}
