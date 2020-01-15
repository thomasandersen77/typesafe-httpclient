package no.spk.felles.ws.client;

import no.spk.felles.ws.client.internal.RequestContext;
import no.spk.felles.ws.client.internal.ResponsContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingHttpInterceptor implements HttpInterceptor {
    private static final Logger log = LoggerFactory.getLogger(LoggingHttpInterceptor.class);
    @Override
    public void beforeRequest(RequestContext requestContext) {
        log.info(">> Incoming request: {}", requestContext);
    }

    @Override
    public void afterRequest(ResponsContext responsContext) {
        log.info("<< Outgoing request: {}", responsContext);
    }
}
