package pl.pas.as.infrastructure.adapters.mappers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.pas.as.core.model.user.Client;
import pl.pas.as.infrastructure.model.user.ClientEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientMapper {

    public static ClientEntity mapToEntity(Client client) {
        return client == null ? null : new ClientEntity(
            client.getId(),
            client.getVersion(),
            client.getFirstName(),
            client.getLastName(),
            client.getTelNumber(),
            client.getPassword(),
            RoleMapper.mapToEntity(client.getRole()),
            client.isActive()
        );
    }

    public static Client mapToDomain(ClientEntity clientEntity) {
        return clientEntity == null ? null : new Client(
            clientEntity.getId(),
            clientEntity.getVersion(),
            clientEntity.getFirstName(),
            clientEntity.getLastName(),
            clientEntity.getTelNumber(),
            clientEntity.getPassword(),
            RoleMapper.mapToDomain(clientEntity.getRole()),
            clientEntity.isActive()
        );
    }

}
