package pl.pas.parcellocker.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pl.pas.parcellocker.exceptions.LockerManagerException;
import pl.pas.parcellocker.model.locker.Locker;
import pl.pas.parcellocker.model.locker.LockerRepository;

@ApplicationScoped
@NoArgsConstructor
public class LockerServiceImpl implements LockerService {

    @Inject
    private LockerRepository lockerRepository;

    public LockerServiceImpl(LockerRepository lockerRepository) {
        this.lockerRepository = lockerRepository;
    }

    @Override
    public synchronized Locker createLocker(String identityNumber, String address, int depositBoxCount) {
        checkIfDuplicatedName(identityNumber);

        Locker locker = new Locker(identityNumber, address, depositBoxCount);
        lockerRepository.add(locker);
        return locker;
    }

    @Override
    public void checkIfDuplicatedName(String name) {
        if (lockerRepository.findByIdentityNumber(name).isPresent())
            error("Locker with given name already exists.");
    }

    @Override
    public Locker getLocker(String identityNumber) {
        return lockerRepository.findByIdentityNumber(identityNumber)
            .orElseThrow(() ->
                new LockerManagerException("Locker with given name doesn't exist.")
            );
    }

    @Override
    public synchronized void removeLocker(String identityNumber) {
        Locker lockerToRemove = getLocker(identityNumber);

        try {
            lockerRepository.remove(lockerToRemove);
        } catch (Exception e) {
            error("Couldn't remove locker.", e);
        }
    }

    private void error(String msg) {
        throw new LockerManagerException(msg);
    }

    private void error(String msg, Throwable throwable) {
        throw new LockerManagerException(msg, throwable);
    }
}
