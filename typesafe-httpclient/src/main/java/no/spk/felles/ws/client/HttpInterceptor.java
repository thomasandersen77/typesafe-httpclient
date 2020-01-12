package no.spk.felles.ws.client;

import no.spk.felles.ws.client.internal.RequestContext;

public interface HttpInterceptor {
    void doPreRequest(RequestContext requestContext);
}
