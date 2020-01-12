package no.spk.felles.ws.client;

import no.spk.felles.ws.client.internal.AbstractHttpClient;

import java.net.URI;
import java.util.List;

public class HttpClientImpl extends AbstractHttpClient implements HttpClient{
    public HttpClientImpl(URI uri, HeaderBuilder headerBuilder, JsonMappingProvider jsonMappingProvider) {
        super(uri, headerBuilder, jsonMappingProvider);
    }

    public HttpClientImpl(URI uri, HeaderBuilder headerBuilder, JsonMappingProvider jsonMappingProvider, List<HttpInterceptor> httpInterceptors) {
        super(uri, headerBuilder, jsonMappingProvider, httpInterceptors);
    }

    @Override
    public <T> T send(Method method, Object requestBody, Class<T> responseType) {
        return super.send(method, requestBody, responseType);
    }

    @Override
    public <T> T post(Object requestBody, Class<T> responseType) {
        return super.send(Method.POST, requestBody, responseType);
    }

    @Override
    public <T> T get(Object requestBody, Class<T> responseType) {
        return super.send(Method.GET, requestBody, responseType);
    }


}
