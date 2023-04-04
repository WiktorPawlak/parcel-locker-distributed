package pl.pas.soap.endpoints;

import jakarta.jws.WebService;
import pl.pas.soap.ClientSoap;
import pl.pas.soap.endpoints.dto.ClientDto;

@WebService
public interface ClientEndpointApi {
    ClientSoap getClient(String telNumber);

    ClientSoap registerClient(ClientDto clientDTO);

    ClientSoap unregisterClient(String telNumber);
}
