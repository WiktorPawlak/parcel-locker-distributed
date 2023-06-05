package pl.pas.as.infrastructure.adapters;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.time.Duration;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import pl.pas.as.ports.outcoming.DomainServiceCaller;
import pl.pas.as.ports.outcoming.dto.DomainClientDto;

@Log
@AllArgsConstructor
public class JerseyDomainServiceCaller implements DomainServiceCaller {

    public static final String CLIENTS_INTERNAL_URL = "http://localhost:8081/api/internal/clients";
    private Client jerseyClient;

    private final RetryConfig config = RetryConfig.<Response>custom()
        .maxAttempts(4)
        .waitDuration(Duration.of(2, SECONDS))
        .retryOnResult(response -> response.getStatus() != OK.value() &&
            response.getStatus() != CREATED.value() &&
            response.getStatus() != CONFLICT.value())
        .retryExceptions(Exception.class)
        .build();

    private final Retry retry = Retry.of("domain-call-retry", config);

    @Override
    public void registerClientInDomain(final DomainClientDto dto, final String token) {
        log.info("Beginning remote call to domain module");
        retryEventPublisherConfig();

        Response response = retry.executeSupplier(() -> jerseyClient.target(CLIENTS_INTERNAL_URL)
            .request(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer " + token)
            .post(Entity.entity(dto, MediaType.APPLICATION_JSON))
        );

        log.info("Ending remote call to domain module with status: " + response.getStatus());
    }

    @Override
    public void unregisterClientInDomain(final String telNumber, final String token) {
        log.info("Beginning remote call to domain module");
        retryEventPublisherConfig();
        Response response = retry.executeSupplier(() -> jerseyClient.target(CLIENTS_INTERNAL_URL)
            .request(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer " + token)
            .put(Entity.entity(telNumber, MediaType.TEXT_PLAIN))
        );

        log.info("Ending remote call to domain module with status: " + response.getStatus());
    }

    private void retryEventPublisherConfig() {
        Retry.EventPublisher publisher = retry.getEventPublisher();
        publisher.onRetry(
            event -> log.warning("[Attempt "
                + event.getNumberOfRetryAttempts() + "/"
                + (retry.getRetryConfig().getMaxAttempts() - 1)
                + "] Failed to make remote call to domain module")
        );
    }
}
