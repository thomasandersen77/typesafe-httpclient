package no.spk.felles.ws.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static java.util.Collections.emptyList;

public final class HttpClientAdapter {
    private static final Logger log = LoggerFactory.getLogger(HttpClientAdapter.class);
    private final URI uri;
    private final HeadersBuilder headersBuilder;
    private JsonMappingProvider jsonMappingProvider;
    private List<HttpInterceptor> httpInterceptors;

    public HttpClientAdapter(URI uri, HeadersBuilder headersBuilder, JsonMappingProvider jsonMappingProvider) {
        this(uri, headersBuilder, jsonMappingProvider, emptyList());
    }

    public HttpClientAdapter(URI uri, HeadersBuilder headersBuilder, JsonMappingProvider jsonMappingProvider, List<HttpInterceptor> httpInterceptors) {
        this.uri = uri;
        this.headersBuilder = headersBuilder;
        this.jsonMappingProvider = jsonMappingProvider;
        this.httpInterceptors = httpInterceptors;
    }

    public <R> R send(Method method, Object requestBody, Class<R> responseType) {
        try {
            HttpClient client = HttpClient.newBuilder().build();

            String jsonBody = jsonMappingProvider.toJson(requestBody);
            Map<String, Object> httpHeadersMap = headersBuilder.build();
            RequestContext requestContext = new RequestContext(uri, httpHeadersMap, method, jsonBody);
            httpInterceptors.forEach(httpInterceptor -> {
                httpInterceptor.doPreRequest(requestContext);
            });

            HttpRequest.Builder builder = HttpRequest.newBuilder();
            populateHeaders(builder, httpHeadersMap);
            HttpRequest request = builder
                    .timeout(Duration.ofSeconds(30))
                    .uri(requestContext.getUri())
                    .method(method.name(), HttpRequest.BodyPublishers.ofString(jsonBody, StandardCharsets.UTF_8))
                    .build();


            HttpResponse.BodyHandler<String> handler =
                    responseInfo -> HttpResponse.BodySubscribers.ofString(StandardCharsets.UTF_8);

            HttpResponse<String> httpResponse = client.send(request, handler);

            if(httpResponse.statusCode() >= 500) {
                throw new HttpServerError(requestContext.getUri(), method, httpResponse.statusCode(), httpResponse.headers(), httpResponse.body());
            } else if(httpResponse.statusCode() >= 400) {
                throw new HttpClientError(requestContext.getUri(), method, httpResponse.statusCode(), httpResponse.headers(), httpResponse.body());
            }

            return jsonMappingProvider.fromJson(httpResponse.body(), responseType);
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private void populateHeaders(HttpRequest.Builder builder, Map<String, Object> headerMap) {
        headerMap.forEach((key, value) -> {
            if (value instanceof Supplier) {
                String val = ((Supplier<String>) value).get();
                builder.header(key, val != null ? val : "");
            } else if (value instanceof String) {
                builder.header(key, value.toString());
            }
        });
    }

    public void setJsonMappingProvider(JsonMappingProvider jsonMappingProvider) {
        this.jsonMappingProvider = jsonMappingProvider;
    }

    public void setHttpInterceptors(List<HttpInterceptor> httpInterceptors) {
        this.httpInterceptors = httpInterceptors;
    }

}
