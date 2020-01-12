package no.spk.felles.ws.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HttpClientAdapterTest {

    static WireMockServer wiremock = new WireMockServer(WireMockConfiguration.options()
            .dynamicPort()
            .notifier(new ConsoleNotifier(true)));


    @Test
    void sendGetRequest() {
        HeadersBuilder headersBuilder = new HeadersBuilder()
                .withHeader("Authorization", new AuthHeaderSupplier("http://localhost:" + wiremock.port() + "/authorize"))
                .withHeader("Content-Type", "application/json");

        HttpClientAdapter httpClientAdapter = new HttpClientAdapter(
                URI.create("http://localhost:" + wiremock.port() + "/"),
                headersBuilder,
                new JacksonJsonMappingProvider()
        );

        ApiResponse res = httpClientAdapter.send(Method.GET, LocalDate.now(), ApiResponse.class);
        assertNotNull(res);
    }

    @Test
    void sendGetRequestWithHttpInterceptors() {
        HeadersBuilder headersBuilder = new HeadersBuilder()
                .withHeader("Authorization", new AuthHeaderSupplier("http://localhost:" + wiremock.port() + "/authorize"))
                .withHeader("Content-Type", "application/json");

        HttpClientAdapter httpClientAdapter = new HttpClientAdapter(
                URI.create("http://localhost:" + wiremock.port() + "/"),
                headersBuilder,
                new JacksonJsonMappingProvider()
        );

        httpClientAdapter.setHttpInterceptors(List.of(new LoggingHttpInterceptor()));

        ApiResponse res = httpClientAdapter.send(Method.GET, LocalDate.now(), ApiResponse.class);
        assertNotNull(res);
    }

    @BeforeAll
    static void setUp() throws JsonProcessingException {
        wiremock.start();
        wiremock.stubFor(get(urlPathMatching("/.*"))
                .withHeader("Authorization", equalTo("CAN_ACCESS:TRUE"))
                .willReturn(aResponse()
                        .withBody(
                                new ObjectMapper().writeValueAsString(new ApiResponse("api respons", 100))
                        )));

        wiremock.stubFor(post(urlEqualTo("/authorize"))
                .willReturn(aResponse()
                        .withBody(
                            new ObjectMapper().writeValueAsString(new ApiResponse("CAN_ACCESS:TRUE", 200))
                        )));
    }

    @AfterAll
    static void tearDown() {
        wiremock.stop();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class ApiResponse {
        private String message;
        private int code;
    }
}