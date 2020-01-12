package no.spk.felles.ws.client;

public interface JsonMappingProvider {
    <T> String toJson(T type);

    <T> T fromJson(String json, Class<T> type);
}
