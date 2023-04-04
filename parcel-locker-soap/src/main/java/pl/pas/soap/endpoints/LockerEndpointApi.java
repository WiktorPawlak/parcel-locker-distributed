package pl.pas.soap.endpoints;

import jakarta.jws.WebService;
import pl.pas.soap.LockerSoap;
import pl.pas.soap.endpoints.dto.LockerDto;

@WebService
public interface LockerEndpointApi {
    LockerSoap getLocker(String identityNumber);

    LockerSoap addLocker(LockerDto lockerDto);

    void removeLocker(String identityNumber);
}
