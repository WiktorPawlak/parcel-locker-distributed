package pl.pas.soap.endpoints.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "clientDto", propOrder = {
    "firstName",
    "lastName",
    "telNumber"
})
public class ClientDto {

    public String firstName;

    public String lastName;

    public String telNumber;
}
