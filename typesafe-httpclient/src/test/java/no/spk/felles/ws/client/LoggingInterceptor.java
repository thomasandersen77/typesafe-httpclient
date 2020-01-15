package no.spk.felles.ws.client;

import no.spk.felles.ws.client.internal.RequestContext;
import no.spk.felles.ws.client.internal.ResponseContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingInterceptor implements Interceptor {
    private static final Logger log = LoggerFactory.getLogger(LoggingInterceptor.class);
    @Override
    public void beforeRequest(RequestContext requestContext) {
        log.info(">> Incoming request: {}", requestContext);
    }

    @Override
    public void afterRequest(ResponseContext responseContext) {
        log.info("<< Outgoing request: {}", responseContext);
    }
}
