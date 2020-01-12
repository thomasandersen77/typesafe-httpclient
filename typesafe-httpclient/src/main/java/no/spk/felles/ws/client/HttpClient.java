package no.spk.felles.ws.client;

import java.util.List;

public interface HttpClient {
    void setHttpInterceptors(List<HttpInterceptor> httpInterceptors);

    <T> T send(Method method, Object requestBody, Class<T> responseType);

    <T> T post(Object requestBody, Class<T> responseType);
    <T> T get(Object requestBody, Class<T> responseType);
}
