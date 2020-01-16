package no.spk.felles.ws.client;

public interface HttpClient {
    <T> TypedResponse<T> send(Method method, Object requestBody, Class<T> responseType);

    <T> TypedResponse<T> post(Object requestBody, Class<T> responseType);
    <T> TypedResponse<T> get(Object requestBody, Class<T> responseType);
    <T> TypedResponse<T> post(Object requestBody, Headers headers, Class<T> responseType);
    <T> TypedResponse<T> get(Object requestBody, Headers headers, Class<T> responseType);
}
