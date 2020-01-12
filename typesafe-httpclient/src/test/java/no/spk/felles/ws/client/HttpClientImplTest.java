package no.spk.felles.ws.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HttpClientImplTest {

    static WireMockServer wiremock = new WireMockServer(WireMockConfiguration.options()
            .dynamicPort()
            .notifier(new ConsoleNotifier(true)));


    @Test
    void sendGetRequest() {
        HeaderBuilder headerBuilder = new HeaderBuilder()
                .withHeader("Authorization", new AuthHeaderSupplier("http://localhost:" + wiremock.port() + "/authorize"))
                .withHeader("Content-Type", "application/json");

        HttpClient abstractHttpClient = new HttpClientImpl(
                URI.create("http://localhost:" + wiremock.port() + "/"),
                headerBuilder,
                new JacksonJsonMappingProvider()
        );

        ApiResponse res = abstractHttpClient.send(Method.GET, LocalDate.now(), ApiResponse.class);
        assertNotNull(res);
    }

    @Test
    void sendGetRequestWithHttpInterceptors() {
        HeaderBuilder headerBuilder = new HeaderBuilder()
                .withHeader("Authorization", new AuthHeaderSupplier("http://localhost:" + wiremock.port() + "/authorize"))
                .withHeader("Content-Type", "application/json");

        HttpClient abstractHttpClient = new HttpClientImpl(
                URI.create("http://localhost:" + wiremock.port() + "/"),
                headerBuilder,
                new JacksonJsonMappingProvider(),
                List.of(new LoggingHttpInterceptor())
        );

        ApiResponse res = abstractHttpClient.send(Method.GET, LocalDate.now(), ApiResponse.class);
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

    static class ApiResponse {
        private ApiResponse(){
            // json serialization
        }
        public ApiResponse(String message, int code) {
            this.message = message;
            this.code = code;
        }

        private String message;
        private int code;

        public String getMessage() {
            return message;
        }

        public int getCode() {
            return code;
        }
    }
}