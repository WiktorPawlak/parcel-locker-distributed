package pl.pas.infrastructure.repositories.hibernate;


import pl.pas.infrastructure.model.locker.LockerEnt;

import java.util.Optional;

public class LockerRepositoryHibernate extends HibernateRepository<LockerEnt> {
    public LockerRepositoryHibernate() {
        super(LockerEnt.class);
    }

    public Optional<LockerEnt> findByIdentityNumber(String identityNumber) {
        return findBy(locker -> locker.getIdentityNumber().equals(identityNumber)).stream().findFirst();
    }
}
