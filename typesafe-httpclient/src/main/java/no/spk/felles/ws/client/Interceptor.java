package no.spk.felles.ws.client;

public interface Interceptor {
    void beforeRequest(RequestContext requestContext);

    void afterRequest(ResponseContext responseContext);
}
