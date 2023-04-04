package pl.pas.soap.endpoints.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.pas.core.applicationmodel.model.delivery.Delivery;
import pl.pas.core.applicationmodel.model.delivery.List;
import pl.pas.core.applicationmodel.model.user.Client;
import pl.pas.soap.ClientSoap;
import pl.pas.soap.DeliverySoap;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeliverySoapMapper {

    public static DeliverySoap toDeliverySoap(Delivery delivery) {
        return DeliverySoap.builder()
            .id(delivery.getId())
            .version(delivery.getVersion())
            .shipper(ClientSoapMapper.toClientSoap(delivery.getShipper()))
            .receiver(ClientSoapMapper.toClientSoap(delivery.getReceiver()))
            .status(delivery.getStatus())
            .lockerId(delivery.getLockerId())
            .isArchived(delivery.isArchived())
            .build();
    }

    public static Delivery toDelivery(DeliverySoap deliverySoap) {
        return new Delivery(
            deliverySoap.getId(),
            deliverySoap.getVersion(),
            ClientSoapMapper.toClient(deliverySoap.getShipper()),
            ClientSoapMapper.toClient(deliverySoap.getReceiver()),
            deliverySoap.getStatus(),
            new List(BigDecimal.ONE, true),
            // TODO: deliverySoap.getPackage(),
            deliverySoap.getLockerId(),
            deliverySoap.getAllocationStart(),
            deliverySoap.getAllocationStop(),
            deliverySoap.isArchived());
    }

}
