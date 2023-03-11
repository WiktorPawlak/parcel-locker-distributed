package pl.pas.ports.adapters.mappers;

import pl.pas.core.applicationmodel.model.user.Client;
import pl.pas.infrastructure.model.user.ClientEnt;

public class ClientMapper {

    public static ClientEnt mapToEntity(Client client) {
        return new ClientEnt();
    }

    public static Client mapToDomain(ClientEnt client) {
        return new Client();
    }

}
