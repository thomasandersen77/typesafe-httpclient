package no.spk.felles.ws.client;

import java.net.URI;
import java.time.LocalDate;

public class AuthHeaderSupplier implements HeaderSupplier {
    private String url;
    public AuthHeaderSupplier(String url) {
        this.url = url;
    }

    @Override
    public String get() {
        HttpClientAdapter clientAdapter = new HttpClientAdapter(
                URI.create(url),
                new HeadersBuilder(),
                new JacksonJsonMappingProvider());
        return clientAdapter.send(Method.POST,  LocalDate.now(), HttpClientAdapterTest.ApiResponse.class).getMessage();
    }
}
