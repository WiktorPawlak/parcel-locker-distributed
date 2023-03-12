package pl.pas.infrastructure.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import pl.pas.core.applicationmodel.model.locker.Locker;
import pl.pas.infrastructure.adapters.mappers.DepositBoxMapper;
import pl.pas.infrastructure.adapters.mappers.LockerMapper;
import pl.pas.infrastructure.model.locker.LockerEnt;
import pl.pas.infrastructure.repositories.hibernate.LockerRepositoryHibernate;
import pl.pas.ports.outcoming.LockerRepository;

import java.util.Optional;
import java.util.UUID;

@Named
@ApplicationScoped
@NoArgsConstructor
public class LockerRepositoryAdapter implements LockerRepository {

    @Inject
    private LockerRepositoryHibernate lockerRepository;

    @Override
    public Locker get(UUID lockerId) {
        LockerEnt lockerEnt = lockerRepository.get(lockerId);
        return LockerMapper.mapToDomain(lockerEnt);
    }

    @Override
    public void add(Locker locker) {
        LockerEnt lockerEnt = LockerMapper.mapToEntity(locker);
        lockerRepository.add(lockerEnt);
    }

    @Override
    public void update(Locker locker) {
        LockerEnt lockerEnt = lockerRepository.get(locker.getId());
        lockerEnt.setDepositBoxes(DepositBoxMapper.mapToEntity(locker.getDepositBoxes()));
        lockerRepository.update(lockerEnt);
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
