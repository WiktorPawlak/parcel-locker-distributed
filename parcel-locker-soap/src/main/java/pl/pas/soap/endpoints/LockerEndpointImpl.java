package pl.pas.soap.endpoints;


import jakarta.inject.Inject;
import jakarta.jws.WebService;
import pl.pas.ports.incoming.LockerService;
import pl.pas.soap.LockerSoap;
import pl.pas.soap.endpoints.dto.LockerDto;
import pl.pas.soap.endpoints.mapper.LockerSoapMapper;

@WebService(serviceName = "lockersSoapApi", endpointInterface = "pl.pas.soap.endpoints.LockerEndpointApi")
public class LockerEndpointImpl implements LockerEndpointApi {

    @Inject
    private LockerService lockerService;


    @Override
    public LockerSoap getLocker(String identityNumber) {
        return LockerSoapMapper.toLockerSoap(lockerService.getLocker(identityNumber));
    }

    @Override
    public LockerSoap addLocker(LockerDto lockerDto) {
        return LockerSoapMapper.toLockerSoap(
            lockerService.createLocker(lockerDto.identityNumber, lockerDto.address, lockerDto.numberOfBoxes)
        );
    }

    @Override
    public void removeLocker(String identityNumber) {
        lockerService.removeLocker(identityNumber);
    }
}
