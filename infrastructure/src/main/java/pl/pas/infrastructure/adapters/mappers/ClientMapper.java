package pl.pas.infrastructure.adapters.mappers;

import pl.pas.core.applicationmodel.model.user.Client;
import pl.pas.infrastructure.model.user.ClientEntity;

public class ClientMapper {

    public static ClientEntity mapToEntity(Client client) {
        return client == null ? null : new ClientEntity(
            client.getId(),
            client.getFirstName(),
            client.getLastName(),
            client.getTelNumber(),
            client.isActive()
        );
    }

    public static Client mapToDomain(ClientEntity clientEntity) {
        return clientEntity == null ? null : new Client(
            clientEntity.getId(),
            clientEntity.getFirstName(),
            clientEntity.getLastName(),
            clientEntity.getTelNumber(),
            clientEntity.isActive()
        );
    }

}
