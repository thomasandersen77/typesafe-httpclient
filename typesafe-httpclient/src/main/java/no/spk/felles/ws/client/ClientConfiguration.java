package no.spk.felles.ws.client;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ClientConfiguration {
    private HeaderBuilder headerBuilder;
    private List<Interceptor> interceptors = Collections.emptyList();
    private JsonMappingProvider jsonMappingProvider;

    public ClientConfiguration(HeaderBuilder headerBuilder,
                               List<Interceptor> interceptors,
                               JsonMappingProvider jsonMappingProvider) {
        Objects.requireNonNull(jsonMappingProvider, "JsonMappingProvider can not be null. Provide an implementation");
        this.headerBuilder = headerBuilder != null ? headerBuilder : new HeaderBuilder();
        this.interceptors = interceptors;
        this.jsonMappingProvider = jsonMappingProvider;
    }

    public HeaderBuilder getHeaderBuilder() {
        return headerBuilder;
    }

    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    public JsonMappingProvider getJsonMappingProvider() {
        return jsonMappingProvider;
    }
}
