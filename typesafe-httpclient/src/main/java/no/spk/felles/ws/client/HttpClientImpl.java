package no.spk.felles.ws.client;

import java.net.URI;
import java.util.List;

public class HttpClientImpl extends AbstractHttpClient implements HttpClient{

    public HttpClientImpl(URI uri, ClientConfiguration clientConfiguration) {
        super(uri, clientConfiguration.getJsonMappingProvider(), clientConfiguration.getInterceptors());
    }

    public HttpClientImpl(URI uri, JsonMappingProvider jsonMappingProvider) {
        super(uri, jsonMappingProvider);
    }

    public HttpClientImpl(URI uri, JsonMappingProvider jsonMappingProvider, List<Interceptor> interceptors) {
        super(uri, jsonMappingProvider, interceptors);
    }

    @Override
    public <T> TypedResponse<T> send(Method method, Object requestBody, Class<T> responseType) {
        return super.send(method, requestBody, new Headers(), responseType);
    }

    @Override
    public <T> TypedResponse<T> post(Object requestBody, Class<T> responseType) {
        return super.send(Method.POST, requestBody, new Headers(), responseType);
    }


    @Override
    public <T> TypedResponse<T> post(Object requestBody, Headers headers, Class<T> responseType) {
        return super.send(Method.POST, requestBody, headers, responseType);
    }

    @Override
    public <T> TypedResponse<T> get(Object requestBody, Class<T> responseType) {
        return super.send(Method.GET, requestBody, new Headers(), responseType);
    }

    @Override
    public <T> TypedResponse<T> get(Object requestBody, Headers headers, Class<T> responseType) {
        return super.send(Method.GET, requestBody, headers, responseType);
    }
}
