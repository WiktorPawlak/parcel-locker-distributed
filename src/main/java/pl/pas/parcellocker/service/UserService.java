package pl.pas.parcellocker.service;

import java.util.List;
import java.util.UUID;

import pl.pas.parcellocker.model.user.User;

public interface UserService {
    User getUser(String telNumber);

    List<User> getUsersByPartialTelNumber(String telNumberPart);

    User registerClient(UUID operatorId, String firstName, String lastName, String telNumber);

    User unregisterClient(UUID operatorId, User user);
}
