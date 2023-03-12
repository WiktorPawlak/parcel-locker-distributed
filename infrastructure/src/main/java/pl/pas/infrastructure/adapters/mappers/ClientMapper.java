package pl.pas.infrastructure.adapters.mappers;

import pl.pas.core.applicationmodel.model.user.Client;
import pl.pas.infrastructure.model.user.ClientEnt;

public class ClientMapper {

    public static ClientEnt mapToEntity(Client client) {
        return client == null ? null : new ClientEnt(
            client.getId(),
            client.getFirstName(),
            client.getLastName(),
            client.getTelNumber(),
            client.isActive()
        );
    }

    public static Client mapToDomain(ClientEnt clientEnt) {
        return clientEnt == null ? null : new Client(
            clientEnt.getId(),
            clientEnt.getFirstName(),
            clientEnt.getLastName(),
            clientEnt.getTelNumber(),
            clientEnt.isActive()
        );
    }

}
