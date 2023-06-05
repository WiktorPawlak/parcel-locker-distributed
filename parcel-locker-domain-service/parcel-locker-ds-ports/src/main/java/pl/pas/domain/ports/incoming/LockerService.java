package pl.pas.domain.ports.incoming;


import pl.pas.domain.core.applicationmodel.model.locker.Locker;

public interface LockerService {

    Locker createLocker(String identityNumber, String address, int depositBoxCount);

    void checkIfDuplicatedName(String name);

    Locker getLocker(String identityNumber);

    void removeLocker(String identityNumber);
}
