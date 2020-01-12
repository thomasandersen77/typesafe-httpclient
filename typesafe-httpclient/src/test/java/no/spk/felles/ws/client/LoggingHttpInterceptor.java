package no.spk.felles.ws.client;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingHttpInterceptor implements HttpInterceptor {
    @Override
    public void doPreRequest(RequestContext requestContext) {
        log.info("Incoming request > {}", requestContext);
    }
}
