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
        HttpClient clientAdapter = new HttpClientImpl(
                URI.create(url),
                new HeaderBuilder(),
                new JacksonJsonMappingProvider());
        return clientAdapter.send(Method.POST,  LocalDate.now(), HttpClientImplTest.ApiResponse.class).getMessage();
    }
}
