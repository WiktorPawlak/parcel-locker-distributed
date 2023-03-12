package pl.pas.infrastructure.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.pas.core.applicationmodel.model.locker.Locker;
import pl.pas.infrastructure.repositories.hibernate.LockerRepositoryHibernate;
import pl.pas.ports.outcoming.LockerRepository;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class LockerRepositoryAdapter implements LockerRepository {

    @Inject
    private LockerRepositoryHibernate lockerRepository;

    @Override
    public Locker get(UUID lockerId) {
        return null;
    }

    @Override
    public void add(Locker locker) {

    }

    @Override
    public void update(Locker locker) {

    }

    @Override
    public void remove(Locker locker) {

    }

    @Override
    public Optional<Locker> findByIdentityNumber(String identityNumber) {
        return Optional.empty();
    }
}
