package no.spk.felles.ws.client;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public class HeadersBuilder {
    private Map<String, Object> headers = new HashMap<>();

    public HeadersBuilder withHeader(String key, Supplier<String> value) {
        requireNonNull(key, "Header key can not be null");
        requireNonNull(value, "Header value can not be null");
        headers.put(key, value);
        return this;
    }

    public HeadersBuilder withHeader(String key, String value) {
        requireNonNull(key, "Header key can not be null");
        requireNonNull(value, "Header value can not be null");
        headers.put(key, value);
        return this;
    }

    public Map<String, Object> build() {
        return headers;
    }
}
