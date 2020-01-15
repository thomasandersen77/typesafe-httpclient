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

        HttpClient httpClient = new HttpClientImpl(
                URI.create("http://localhost:" + wiremock.port() + "/"),
                new JacksonJsonMappingProvider()
        );

        TypedResponse<ApiResponseDto> typedResponse = httpClient.get(LocalDate.now(), headerBuilder, ApiResponseDto.class);
        ApiResponseDto res = typedResponse.getType();
        assertNotNull(res);
    }

    @Test
    void sendGetRequestWithHttpInterceptors() {
        HeaderBuilder headerBuilder = new HeaderBuilder()
                .withHeader("Authorization", new AuthHeaderSupplier("http://localhost:" + wiremock.port() + "/authorize"))
                .withHeader("Content-Type", "application/json");

        HttpClient httpClient = new HttpClientImpl(
                URI.create("http://localhost:" + wiremock.port() + "/"),
                new JacksonJsonMappingProvider(),
                List.of(new LoggingHttpInterceptor())
        );

        TypedResponse<ApiResponseDto> typedResponse = httpClient.get(LocalDate.now(), headerBuilder, ApiResponseDto.class);
        ApiResponseDto res = typedResponse.getType();
        assertNotNull(res);
    }

    @BeforeAll
    static void setUp() throws JsonProcessingException {
        wiremock.start();
        wiremock.stubFor(get(urlPathMatching("/.*"))
                .withHeader("Authorization", equalTo("CAN_ACCESS:TRUE"))
                .willReturn(aResponse()
                        .withBody(
                                new ObjectMapper().writeValueAsString(new ApiResponseDto("api respons", 100))
                        )));

        wiremock.stubFor(post(urlEqualTo("/authorize"))
                .willReturn(aResponse()
                        .withBody(
                            new ObjectMapper().writeValueAsString(new ApiResponseDto("CAN_ACCESS:TRUE", 200))
                        )));
    }

    @AfterAll
    static void tearDown() {
        wiremock.stop();
    }

    static class ApiResponseDto {
        private ApiResponseDto(){
            // json serialization
        }
        public ApiResponseDto(String message, int code) {
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