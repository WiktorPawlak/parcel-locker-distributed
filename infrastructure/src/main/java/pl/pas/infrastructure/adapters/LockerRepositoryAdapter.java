package pl.pas.infrastructure.adapters;

import java.util.Optional;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.NoArgsConstructor;
import pl.pas.core.applicationmodel.model.locker.Locker;
import pl.pas.infrastructure.adapters.mappers.DepositBoxMapper;
import pl.pas.infrastructure.adapters.mappers.LockerMapper;
import pl.pas.infrastructure.model.locker.LockerEntity;
import pl.pas.infrastructure.repositories.hibernate.LockerRepositoryHibernate;
import pl.pas.ports.outcoming.LockerRepository;

@Named
@ApplicationScoped
@NoArgsConstructor
public class LockerRepositoryAdapter implements LockerRepository {

    @Inject
    private LockerRepositoryHibernate lockerRepository;

    @Override
    public Locker get(UUID lockerId) {
        LockerEntity lockerEntity = lockerRepository.get(lockerId);
        return LockerMapper.mapToDomain(lockerEntity);
    }

    @Override
    public void add(Locker locker) {
        LockerEntity lockerEntity = LockerMapper.mapToEntity(locker);
        lockerRepository.add(lockerEntity);
    }

    @Override
    public void update(Locker locker) {
        LockerEntity lockerEntity = lockerRepository.get(locker.getId());
        lockerEntity.setDepositBoxes(DepositBoxMapper.mapToEntity(locker.getDepositBoxes()));
        lockerRepository.update(lockerEntity);
    }

    @Override
    public void remove(Locker locker) {
        lockerRepository.remove(LockerMapper.mapToEntity(locker));
    }

    @Override
    public Optional<Locker> findByIdentityNumber(String identityNumber) {
        return lockerRepository.findByIdentityNumber(identityNumber)
            .map(LockerMapper::mapToDomain);
    }
}
