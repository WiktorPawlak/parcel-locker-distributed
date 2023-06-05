package pl.pas.domain.infrastructure.repositories.hibernate;


import java.util.Optional;

import org.springframework.stereotype.Repository;

import pl.pas.domain.infrastructure.model.locker.LockerEntity;

@Repository
public class LockerRepositoryHibernate extends HibernateRepository<LockerEntity> {
    public LockerRepositoryHibernate() {
        super(LockerEntity.class);
    }

    public Optional<LockerEntity> findByIdentityNumber(String identityNumber) {
        return findBy(locker -> locker.getIdentityNumber().equals(identityNumber)).stream().findFirst();
    }
}
