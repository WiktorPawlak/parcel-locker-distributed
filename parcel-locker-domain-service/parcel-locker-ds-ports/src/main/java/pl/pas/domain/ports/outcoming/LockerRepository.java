package pl.pas.domain.ports.outcoming;

import java.util.Optional;
import java.util.UUID;

import pl.pas.domain.core.applicationmodel.model.locker.Locker;

public interface LockerRepository {
    Locker get(UUID lockerId);

    void add(Locker locker);

    void update(Locker locker);

    void remove(Locker locker);

    Optional<Locker> findByIdentityNumber(String identityNumber);
}
