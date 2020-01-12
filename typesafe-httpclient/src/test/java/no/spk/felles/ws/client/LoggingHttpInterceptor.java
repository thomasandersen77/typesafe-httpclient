package no.spk.felles.ws.client;

import no.spk.felles.ws.client.internal.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingHttpInterceptor implements HttpInterceptor {
    private static final Logger log = LoggerFactory.getLogger(LoggingHttpInterceptor.class);
    @Override
    public void doPreRequest(RequestContext requestContext) {
        log.info("Incoming request > {}", requestContext);
    }
}
