package pl.pas.infrastructure.adapters.mappers;

import java.util.List;

import pl.pas.core.applicationmodel.model.locker.DepositBox;
import pl.pas.infrastructure.model.locker.DepositBoxEntity;

public class DepositBoxMapper {

    private static DepositBox mapToDomain(DepositBoxEntity depositBoxEntity) {
        return (depositBoxEntity == null) ? null : new DepositBox(
            depositBoxEntity.getId(),
            DeliveryMapper.mapToDomain(depositBoxEntity.getDelivery()),
            depositBoxEntity.isEmpty(),
            depositBoxEntity.getAccessCode(),
            depositBoxEntity.getTelNumber()
        );
    }

    private static DepositBoxEntity mapToEntity(DepositBox depositBox) {
        return depositBox == null ? null : new DepositBoxEntity(
            depositBox.getId(),
            DeliveryMapper.mapToEntity(depositBox.getDelivery()),
            depositBox.isEmpty(),
            depositBox.getAccessCode(),
            depositBox.getTelNumber()
        );
    }

    public static List<DepositBox> mapToDomain(List<DepositBoxEntity> depositBoxes) {
        return depositBoxes.stream()
            .map(DepositBoxMapper::mapToDomain)
            .toList();
    }

    public static List<DepositBoxEntity> mapToEntity(List<DepositBox> depositBoxes) {
        return depositBoxes.stream()
            .map(DepositBoxMapper::mapToEntity)
            .toList();
    }
}
