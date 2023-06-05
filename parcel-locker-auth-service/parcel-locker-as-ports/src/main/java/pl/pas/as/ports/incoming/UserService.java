package pl.pas.as.ports.incoming;

import java.util.List;
import java.util.Optional;

import pl.pas.as.core.model.user.Client;

public interface UserService {
    Client getUser(String telNumber);

    Optional<Client> getUserByTelNumber(final String telNumber);

    List<Client> getUsersByPartialTelNumber(String telNumberPart);

    Client registerClient(String firstName, String lastName, String telNumber, String password, String token);

    Client unregisterClient(Client user, String token);
}
