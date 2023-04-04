package pl.pas.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "depositBoxSoap", propOrder = {
    "id",
    "version",
    "delivery",
    "isEmpty",
    "accessCode",
    "telNumber"
})
public class DepositBoxSoap {

    private UUID id;
    private Long version;
    private DeliverySoap delivery;
    private boolean isEmpty;
    private String accessCode;
    private String telNumber;

    @Builder
    public DepositBoxSoap(
        final UUID id,
        final Long version,
        final DeliverySoap delivery,
        final boolean isEmpty,
        final String accessCode,
        final String telNumber
    ) {
        this.id = id;
        this.version = version;
        this.delivery = delivery;
        this.isEmpty = isEmpty;
        this.accessCode = accessCode;
        this.telNumber = telNumber;
    }
}
