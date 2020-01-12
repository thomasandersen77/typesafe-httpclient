package no.spk.felles.ws.client;

public interface HttpInterceptor {
    void doPreRequest(RequestContext requestContext);
}
