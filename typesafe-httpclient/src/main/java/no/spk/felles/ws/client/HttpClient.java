package no.spk.felles.ws.client;

public interface HttpClient {
    <T> TypedResponse<T> send(Method method, Object requestBody, Class<T> responseType);

    <T> TypedResponse<T> post(Object requestBody, Class<T> responseType);
    <T> TypedResponse<T> get(Object requestBody, Class<T> responseType);
    <T> TypedResponse<T> post(Object requestBody, HeaderBuilder headerBuilder, Class<T> responseType);
    <T> TypedResponse<T> get(Object requestBody, HeaderBuilder headerBuilder, Class<T> responseType);
}
