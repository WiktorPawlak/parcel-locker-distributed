package pl.pas.domain.ports.outcoming;

import java.util.Optional;

import pl.pas.domain.core.applicationmodel.model.user.Client;

public interface AuthServiceCaller {

    Optional<Client> findByTelNumber(final String telNumber);
}
