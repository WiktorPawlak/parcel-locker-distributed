package pl.pas.domain.infrastructure.adapters;

import java.util.Optional;
import java.util.UUID;

import jakarta.inject.Named;
import lombok.AllArgsConstructor;
import pl.pas.domain.core.applicationmodel.model.locker.Locker;
import pl.pas.domain.infrastructure.adapters.mappers.DepositBoxMapper;
import pl.pas.domain.infrastructure.adapters.mappers.LockerMapper;
import pl.pas.domain.infrastructure.model.locker.LockerEntity;
import pl.pas.domain.infrastructure.repositories.hibernate.LockerRepositoryHibernate;
import pl.pas.domain.ports.outcoming.LockerRepository;

@Named
@AllArgsConstructor
public class LockerRepositoryAdapter implements LockerRepository {

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
