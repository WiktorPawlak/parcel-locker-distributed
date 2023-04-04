package pl.pas.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pas.core.applicationmodel.model.locker.DepositBox;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "lockerSoap", propOrder = {
    "id",
    "version",
    "identityNumber",
    "address",
    "depositBoxes"
})
public class LockerSoap {

    private UUID id;
    private Long version;
    private String identityNumber;
    private String address;
    private List<DepositBoxSoap> depositBoxes;

    @Builder
    public LockerSoap(
        final UUID id,
        final Long version,
        final String identityNumber,
        final String address,
        final List<DepositBoxSoap> depositBoxes
    ) {
        this.id = id;
        this.version = version;
        this.identityNumber = identityNumber;
        this.address = address;
        this.depositBoxes = depositBoxes;
    }
}
