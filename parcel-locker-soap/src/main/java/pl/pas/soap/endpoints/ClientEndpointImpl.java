package pl.pas.soap.endpoints;


import jakarta.inject.Inject;
import jakarta.jws.WebService;
import pl.pas.core.applicationmodel.model.user.Client;
import pl.pas.ports.incoming.UserService;
import pl.pas.soap.ClientSoap;
import pl.pas.soap.endpoints.dto.ClientDto;
import pl.pas.soap.endpoints.mapper.ClientSoapMapper;

@WebService(serviceName = "clientsSoapApi", endpointInterface = "pl.pas.soap.endpoints.ClientEndpointApi")
public class ClientEndpointImpl implements ClientEndpointApi {

    @Inject
    private UserService userService;

    @Override
    public ClientSoap getClient(String telNumber) {
        return ClientSoapMapper.toClientSoap(userService.getUser(telNumber));
    }

    @Override
    public ClientSoap registerClient(ClientDto clientDTO) {
        Client newUser = userService.registerClient(clientDTO.firstName, clientDTO.lastName, clientDTO.telNumber);
        return ClientSoapMapper.toClientSoap(newUser);
    }

    @Override
    public ClientSoap unregisterClient(String telNumber) {
        Client user = userService.getUser(telNumber);
        Client unregisteredUser = userService.unregisterClient(user);
        return ClientSoapMapper.toClientSoap(unregisteredUser);
    }
}
