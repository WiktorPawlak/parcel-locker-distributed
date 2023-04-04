package pl.pas.soap.endpoints.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.pas.core.applicationmodel.model.delivery.Delivery;
import pl.pas.core.applicationmodel.model.locker.DepositBox;
import pl.pas.soap.DeliverySoap;
import pl.pas.soap.DepositBoxSoap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DepositBoxSoapMapper {

    public static DepositBoxSoap toDepositBoxSoap(DepositBox depositBox) {
        return DepositBoxSoap.builder()
            .id(depositBox.getId())
            .version(depositBox.getVersion())
            .delivery(nullableConvert(depositBox.getDelivery()))
            .isEmpty(depositBox.isEmpty())
            .accessCode(depositBox.getAccessCode())
            .telNumber(depositBox.getTelNumber())
            .build();
    }

    public static DepositBox toDepositBox(DepositBoxSoap depositBoxSoap) {
        return new DepositBox(
            depositBoxSoap.getId(),
            depositBoxSoap.getVersion(),
            DeliverySoapMapper.toDelivery(depositBoxSoap.getDelivery()),
            depositBoxSoap.isEmpty(),
            depositBoxSoap.getAccessCode(),
            depositBoxSoap.getTelNumber()
        );
    }

    private static DeliverySoap nullableConvert(Delivery delivery) {
        return delivery != null ?
            DeliverySoapMapper.toDeliverySoap(delivery)
            : null;
    }

}
