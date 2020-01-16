package no.spk.felles.ws.client;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ClientConfiguration {
    private Headers headers;
    private List<Interceptor> interceptors = Collections.emptyList();
    private JsonMappingProvider jsonMappingProvider;

    public ClientConfiguration(Headers headers,
                               List<Interceptor> interceptors,
                               JsonMappingProvider jsonMappingProvider) {
        Objects.requireNonNull(jsonMappingProvider, "JsonMappingProvider can not be null. Provide an implementation");
        this.headers = headers != null ? headers : new Headers();
        this.interceptors = interceptors;
        this.jsonMappingProvider = jsonMappingProvider;
    }

    public Headers getHeaders() {
        return headers;
    }

    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    public JsonMappingProvider getJsonMappingProvider() {
        return jsonMappingProvider;
    }
}
