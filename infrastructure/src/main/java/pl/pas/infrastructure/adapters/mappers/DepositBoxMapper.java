package pl.pas.infrastructure.adapters.mappers;

import pl.pas.core.applicationmodel.model.locker.DepositBox;
import pl.pas.infrastructure.model.locker.DepositBoxEnt;

import java.util.List;

public class DepositBoxMapper {

    private static DepositBox mapToDomain(DepositBoxEnt depositBoxEnt) {
        return (depositBoxEnt == null) ? null : new DepositBox(
            depositBoxEnt.getId(),
            DeliveryMapper.mapToDomain(depositBoxEnt.getDelivery()),
            depositBoxEnt.isEmpty(),
            depositBoxEnt.getAccessCode(),
            depositBoxEnt.getTelNumber()
        );
    }

    private static DepositBoxEnt mapToEntity(DepositBox depositBox) {
        return depositBox == null ? null : new DepositBoxEnt(
            depositBox.getId(),
            DeliveryMapper.mapToEntity(depositBox.getDelivery()),
            depositBox.isEmpty(),
            depositBox.getAccessCode(),
            depositBox.getTelNumber()
        );
    }

    public static List<DepositBox> mapToDomain(List<DepositBoxEnt> depositBoxes) {
        return depositBoxes.stream()
            .map(DepositBoxMapper::mapToDomain)
            .toList();
    }

    public static List<DepositBoxEnt> mapToEntity(List<DepositBox> depositBoxes) {
        return depositBoxes.stream()
            .map(DepositBoxMapper::mapToEntity)
            .toList();
    }
}
