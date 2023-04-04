package pl.pas.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pas.core.applicationmodel.model.delivery.DeliveryStatus;
import pl.pas.core.applicationmodel.model.locker.Locker;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deliverySoap", propOrder = {
    "id",
    "version",
    "shipper",
    "receiver",
    "status",
    // TODO: "pack",
    "lockerId",
    "isArchived",
    "allocationStart",
    "allocationStop"
})
public class DeliverySoap {

    private UUID id;
    private Long version;

    @XmlElement(name = "shipper")
    private ClientSoap shipper;

    @XmlElement(name = "receiver")
    private ClientSoap receiver;

    private DeliveryStatus status;
    // TODO: private Package pack;
    private UUID lockerId;
    private boolean isArchived;

    @XmlSchemaType(name = "dateTime")
    private LocalDateTime allocationStart;

    @XmlSchemaType(name = "dateTime")
    private LocalDateTime allocationStop;

    @Builder
    public DeliverySoap(
        final UUID id,
        final Long version,
        final ClientSoap shipper,
        final ClientSoap receiver,
        final DeliveryStatus status,
        // TODO: final Package pack,
        final UUID lockerId,
        final boolean isArchived
    ) {
        this.id = id;
        this.version = version;
        this.shipper = shipper;
        this.receiver = receiver;
        this.status = status;
        this.lockerId = lockerId;
        this.isArchived = isArchived;
    }
}
