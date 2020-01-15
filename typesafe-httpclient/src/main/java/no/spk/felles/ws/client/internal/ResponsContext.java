package no.spk.felles.ws.client.internal;

import no.spk.felles.ws.client.Method;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ResponsContext {

    private final URI uri;
    private final Method method;
    private final Map<String, List<String>> headers;
    private final String body;

    public ResponsContext(URI uri, Method method, Map<String, List<String>> headers, final String body) {
        this.uri = uri;
        this.method = method;
        this.headers = headers;
        this.body = body;
    }

    public URI getUri() {
        return uri;
    }

    public Method getMethod() {
        return method;
    }

    public Map<String, List<String>> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }

    public final String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "ResponsContext{" +
                "uri=" + uri +
                ", method=" + method +
                ", headers=" + headers +
                '}';
    }
}
