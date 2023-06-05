package pl.pas.domain.infrastructure.adapters;

import java.util.Optional;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import pl.pas.domain.core.applicationmodel.model.user.Client;
import pl.pas.domain.ports.outcoming.AuthServiceCaller;

@Log
@AllArgsConstructor
public class JerseyServiceCaller implements AuthServiceCaller {

    public static final String CLIENTS_URL = "http://localhost:8080/parcel-locker-as-rest-1.0-SNAPSHOT/api/clients";
    private jakarta.ws.rs.client.Client jerseyClient;

    @Override
    public Optional<Client> findByTelNumber(final String telNumber) {
        Response response = jerseyClient.target(CLIENTS_URL + "?telNumber=" + telNumber)
            .request(MediaType.APPLICATION_JSON)
            .get();

        return response.getEntity() != null
            ? Optional.of((Client) response.getEntity())
            : Optional.empty();
    }

}
