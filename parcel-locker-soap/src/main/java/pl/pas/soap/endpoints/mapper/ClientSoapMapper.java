package pl.pas.soap.endpoints.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.pas.core.applicationmodel.model.user.Client;
import pl.pas.soap.ClientSoap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientSoapMapper {

    public static ClientSoap toClientSoap(Client client) {
        return ClientSoap.builder()
            .id(client.getId())
            .version(client.getVersion())
            .firstName(client.getFirstName())
            .lastName(client.getLastName())
            .telNumber(client.getTelNumber())
            .active(client.isActive())
            .build();
    }

    public static Client toClient(ClientSoap clientSoap) {
        return new Client(clientSoap.getId(), clientSoap.getVersion(),
            clientSoap.getFirstName(), clientSoap.getLastName(),
            clientSoap.getTelNumber(), clientSoap.isActive());
    }

}
