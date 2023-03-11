package pl.pas.infrastructure.repositories.hibernate;


import pl.pas.core.applicationmodel.model.locker.Locker;
import pl.pas.ports.outcoming.LockerRepository;

import java.util.Optional;

public class LockerRepositoryHibernate extends HibernateRepository<Locker> implements LockerRepository {
    public LockerRepositoryHibernate() {
        super(Locker.class);
    }

    public Optional<Locker> findByIdentityNumber(String identityNumber) {
        return findBy(locker -> locker.getIdentityNumber().equals(identityNumber)).stream().findFirst();
    }
}
