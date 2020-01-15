package no.spk.felles.ws.client;

import no.spk.felles.ws.client.internal.AbstractHttpClient;

import java.net.URI;
import java.util.List;

public class HttpClientImpl extends AbstractHttpClient implements HttpClient{
    public HttpClientImpl(URI uri, JsonMappingProvider jsonMappingProvider) {
        super(uri, jsonMappingProvider);
    }

    public HttpClientImpl(URI uri, JsonMappingProvider jsonMappingProvider, List<HttpInterceptor> httpInterceptors) {
        super(uri, jsonMappingProvider, httpInterceptors);
    }

    @Override
    public <T> TypedResponse<T> send(Method method, Object requestBody, Class<T> responseType) {
        return super.send(method, requestBody, new HeaderBuilder(), responseType);
    }

    @Override
    public <T> TypedResponse<T> post(Object requestBody, Class<T> responseType) {
        return super.send(Method.POST, requestBody, new HeaderBuilder(), responseType);
    }

    @Override
    public <T> TypedResponse<T> get(Object requestBody, Class<T> responseType) {
        return super.send(Method.GET, requestBody, new HeaderBuilder(), responseType);
    }

    @Override
    public <T> TypedResponse<T> post(Object requestBody, HeaderBuilder headerBuilder, Class<T> responseType) {
        return super.send(Method.POST, requestBody, headerBuilder, responseType);
    }

    @Override
    public <T> TypedResponse<T> get(Object requestBody, HeaderBuilder headerBuilder, Class<T> responseType) {
        return super.send(Method.GET, requestBody, headerBuilder, responseType);
    }
}
