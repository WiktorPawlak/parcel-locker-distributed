package pl.pas.ports.incoming;

import java.util.List;

import pl.pas.core.applicationmodel.model.user.Client;

public interface UserService {
    Client getUser(String telNumber);

    List<Client> getUsersByPartialTelNumber(String telNumberPart);

    Client registerClient(String firstName, String lastName, String telNumber);

    Client unregisterClient(Client user);
}
