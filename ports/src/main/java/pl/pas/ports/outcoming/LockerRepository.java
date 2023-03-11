package pl.pas.ports.outcoming;

import pl.pas.core.applicationmodel.model.locker.Locker;

import java.util.Optional;
import java.util.UUID;

public interface LockerRepository {
    Locker get(UUID lockerId);
    void add(Locker locker);
    void update(Locker locker);
    void remove(Locker locker);
    Optional<Locker> findByIdentityNumber(String identityNumber);
}
