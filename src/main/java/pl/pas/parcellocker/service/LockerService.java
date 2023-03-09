package pl.pas.parcellocker.service;

import pl.pas.parcellocker.model.locker.Locker;

public interface LockerService {

    Locker createLocker(String identityNumber, String address, int depositBoxCount);

    void checkIfDuplicatedName(String name);

    Locker getLocker(String identityNumber);

    void removeLocker(String identityNumber);
}
