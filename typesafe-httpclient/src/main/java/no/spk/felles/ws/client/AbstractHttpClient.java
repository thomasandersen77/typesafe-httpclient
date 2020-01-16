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
import static java.util.Objects.requireNonNull;

public abstract class AbstractHttpClient {
    private static final Logger log = LoggerFactory.getLogger(AbstractHttpClient.class);
    private final URI uri;
    private JsonMappingProvider jsonMappingProvider;
    private List<Interceptor> interceptors;

    public AbstractHttpClient(URI uri, JsonMappingProvider jsonMappingProvider) {
        this(uri, jsonMappingProvider, emptyList());
    }

    public AbstractHttpClient(URI uri, JsonMappingProvider jsonMappingProvider, List<Interceptor> interceptors) {
        requireNonNull(uri, "URI can not be null");
        requireNonNull(uri, "JsonMappingProvider can not be null");
        requireNonNull(interceptors, "HttpInterceptor list can not be null");
        this.uri = uri;
        this.jsonMappingProvider = jsonMappingProvider;
        this.interceptors = interceptors;
    }

    public final <R> TypedResponse<R> send(final Method method, final Object requestBody, final Headers headers, final Class<R> responseType) {
        try {
            HttpClient client = HttpClient.newBuilder().build();

            String jsonBody = jsonMappingProvider.toJson(requestBody);
            Map<String, Object> httpHeadersMap = headers.build();
            RequestContext requestContext = new RequestContext(uri, httpHeadersMap, method, jsonBody);
            interceptors.forEach(interceptor -> {
                interceptor.beforeRequest(requestContext);
            });

            HttpRequest request = buildRequest(method, jsonBody, httpHeadersMap, requestContext);

            HttpResponse.BodyHandler<String> handler = responseInfo -> HttpResponse.BodySubscribers.ofString(StandardCharsets.UTF_8);

            HttpResponse<String> httpResponse = client.send(request, handler);

            ResponseContext responseContext = new ResponseContext(httpResponse.statusCode(), requestContext.getUri(), method, httpResponse.headers().map(), httpResponse.body());
            interceptors.forEach(interceptor -> {
                interceptor.afterRequest(responseContext);
            });

            if(httpResponse.statusCode() >= 500) {
                throw new HttpServerError(requestContext.getUri(), method, httpResponse.statusCode(), httpResponse.headers(), httpResponse.body());
            } else if(httpResponse.statusCode() >= 400) {
                throw new HttpClientError(requestContext.getUri(), method, httpResponse.statusCode(), httpResponse.headers(), httpResponse.body());
            }


            R type = jsonMappingProvider.fromJson(httpResponse.body(), responseType);
            return new TypedResponse<>(httpResponse.statusCode(), httpResponse.headers().map(), type);
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpRequest buildRequest(Method method, String jsonBody, Map<String, Object> httpHeadersMap, RequestContext requestContext) {
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        populateHeaders(builder, httpHeadersMap);
        return builder
                .timeout(Duration.ofSeconds(30))
                .uri(requestContext.getUri())
                .method(method.name(), HttpRequest.BodyPublishers.ofString(jsonBody, StandardCharsets.UTF_8))
                .build();
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



}
