package no.spk.felles.ws.client;

import java.net.URI;

public class AuthHeaderSupplier implements HeaderSupplier {
    private String url;
    public AuthHeaderSupplier(String url) {
        this.url = url;
    }

    @Override
    public String get() {
        HttpClient httpClient = new HttpClientImpl(
                URI.create(url),
                new JacksonJsonMappingProvider());
        return httpClient.post(new AuthRequest("user"), new HeaderBuilder(), HttpClientImplTest.ApiResponse.class).getMessage();
    }

    static class AuthRequest {
        private String username;
        AuthRequest(){}

        AuthRequest(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }
    }
}
