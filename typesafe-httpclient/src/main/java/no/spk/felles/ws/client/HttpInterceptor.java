package no.spk.felles.ws.client;

import no.spk.felles.ws.client.internal.RequestContext;
import no.spk.felles.ws.client.internal.ResponsContext;

public interface HttpInterceptor {
    void beforeRequest(RequestContext requestContext);

    void afterRequest(ResponsContext responsContext);
}
