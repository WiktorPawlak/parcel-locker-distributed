package pl.pas.ports.incoming;

import pl.pas.core.applicationmodel.model.user.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User getUser(String telNumber);

    List<User> getUsersByPartialTelNumber(String telNumberPart);

    User registerClient(UUID operatorId, String firstName, String lastName, String telNumber);

    User unregisterClient(UUID operatorId, User user);
}
