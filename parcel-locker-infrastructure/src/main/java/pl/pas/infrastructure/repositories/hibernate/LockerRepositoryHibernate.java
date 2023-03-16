package pl.pas.infrastructure.repositories.hibernate;


import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import pl.pas.infrastructure.model.locker.LockerEntity;

@ApplicationScoped
public class LockerRepositoryHibernate extends HibernateRepository<LockerEntity> {
    public LockerRepositoryHibernate() {
        super(LockerEntity.class);
    }

    public Optional<LockerEntity> findByIdentityNumber(String identityNumber) {
        return findBy(locker -> locker.getIdentityNumber().equals(identityNumber)).stream().findFirst();
    }
}
