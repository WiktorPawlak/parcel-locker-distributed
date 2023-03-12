package pl.pas.ports.outcoming;

import pl.pas.core.applicationmodel.model.user.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    void add(Client user);

    void archive(UUID clientId);

    List<Client> findAll();

    Optional<Client> findByTelNumber(String telNumber);

    List<Client> findByTelNumberPart(String telNumberPart);

    Optional<Client> findUserById(UUID uuid);
}
