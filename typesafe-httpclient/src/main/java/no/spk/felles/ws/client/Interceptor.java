package no.spk.felles.ws.client;

import no.spk.felles.ws.client.internal.RequestContext;
import no.spk.felles.ws.client.internal.ResponseContext;

public interface Interceptor {
    void beforeRequest(RequestContext requestContext);

    void afterRequest(ResponseContext responseContext);
}
