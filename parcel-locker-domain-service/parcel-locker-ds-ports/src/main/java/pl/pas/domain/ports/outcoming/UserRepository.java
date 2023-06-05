package pl.pas.domain.ports.outcoming;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import pl.pas.domain.core.applicationmodel.model.user.Client;

public interface UserRepository {
    void add(Client user);

    void archive(UUID clientId);

    List<Client> findAll();

    Optional<Client> findByTelNumber(String telNumber);

    List<Client> findByTelNumberPart(String telNumberPart);

    Optional<Client> findUserById(UUID uuid);
}
