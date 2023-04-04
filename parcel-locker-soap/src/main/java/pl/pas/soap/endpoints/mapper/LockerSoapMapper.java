package pl.pas.soap.endpoints.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.pas.core.applicationmodel.model.locker.Locker;
import pl.pas.soap.LockerSoap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LockerSoapMapper {

    public static LockerSoap toLockerSoap(Locker locker) {
        return LockerSoap.builder()
            .id(locker.getId())
            .version(locker.getVersion())
            .identityNumber(locker.getIdentityNumber())
            .address(locker.getAddress())
            .depositBoxes(locker.getDepositBoxes().stream()
                .map(DepositBoxSoapMapper::toDepositBoxSoap)
                .toList())
            .build();
    }

    public static Locker toLocker(LockerSoap lockerSoap) {
        return new Locker(
            lockerSoap.getId(),
            lockerSoap.getVersion(),
            lockerSoap.getIdentityNumber(),
            lockerSoap.getAddress(),
            lockerSoap.getDepositBoxes().stream()
                .map(DepositBoxSoapMapper::toDepositBox)
                .toList()
        );
    }

}
