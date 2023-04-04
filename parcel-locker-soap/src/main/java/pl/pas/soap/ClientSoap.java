package pl.pas.soap;

import java.util.UUID;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "clientSoap", propOrder = {
    "id",
    "version",
    "firstName",
    "lastName",
    "telNumber",
    "active"
})
public class ClientSoap {

    private UUID id;
    private Long version;
    private String firstName;
    private String lastName;
    private String telNumber;
    private boolean active;

    @Builder
    public ClientSoap(final UUID id, final Long version,
                      final String firstName, final String lastName,
                      final String telNumber, final boolean active) {
        this.id = id;
        this.version = version;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telNumber = telNumber;
        this.active = active;
    }
}
