package no.spk.felles.ws.client;

import java.util.List;
import java.util.Map;

public class TypedResponse<T> {
    private final int statusCode;
    private final Map<String, List<String>> headers;
    private final T type;

    public TypedResponse(int statusCode, Map<String, List<String>> headers, T type) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.type = type;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public T getType() {
        return type;
    }
}
