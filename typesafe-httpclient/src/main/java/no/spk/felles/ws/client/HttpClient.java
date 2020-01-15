package no.spk.felles.ws.client;

import java.util.List;

public interface HttpClient {
    void setHttpInterceptors(List<HttpInterceptor> httpInterceptors);

    <T> TypedResponse<T> send(Method method, Object requestBody, Class<T> responseType);

    <T> TypedResponse<T> post(Object requestBody, Class<T> responseType);
    <T> TypedResponse<T> get(Object requestBody, Class<T> responseType);
    <T> TypedResponse<T> post(Object requestBody, HeaderBuilder headerBuilder, Class<T> responseType);
    <T> TypedResponse<T> get(Object requestBody, HeaderBuilder headerBuilder, Class<T> responseType);
}
