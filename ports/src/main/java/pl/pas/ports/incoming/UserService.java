package pl.pas.ports.incoming;

import pl.pas.core.applicationmodel.model.user.Client;

import java.util.List;
import java.util.UUID;

public interface UserService {
    Client getUser(String telNumber);

    List<Client> getUsersByPartialTelNumber(String telNumberPart);

    Client registerClient(UUID operatorId, String firstName, String lastName, String telNumber);

    Client unregisterClient(UUID operatorId, Client user);
}
