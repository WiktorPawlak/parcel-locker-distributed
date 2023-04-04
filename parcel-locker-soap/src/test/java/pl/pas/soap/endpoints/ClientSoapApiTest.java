package pl.pas.soap.endpoints;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import lombok.SneakyThrows;
import pl.pas.soap.config.PayaraContainerInitializer;
import pl.pas.soap.endpoints.dto.ClientDto;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClientSoapApiTest extends PayaraContainerInitializer {

    static ClientEndpointApi soapService;

    @Override
    @BeforeAll
    public void setup() {
        super.setup();
        String wsdlUrl = baseUri + "/clientsSoapApi?wsdl";
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(ClientEndpointApi.class);
        factory.setAddress(wsdlUrl);

        soapService = (ClientEndpointApi) factory.create();
    }

    @Test
    @SneakyThrows
    void registerSoapClientTest() {
        ClientDto clientDto = new ClientDto("Jan", "Testowy", "698554389");
        soapService.registerClient(clientDto);

        var retrievedClient = soapService.getClient(clientDto.telNumber);

        assertEquals(clientDto.getFirstName(), retrievedClient.getFirstName());
        assertEquals(clientDto.getLastName(), retrievedClient.getLastName());
        assertEquals(clientDto.getTelNumber(), retrievedClient.getTelNumber());
    }

    @Test
    void unregisterSoapClientTest() {
        ClientDto clientDto = new ClientDto("Jan", "Testowy2", "698554382");
        soapService.registerClient(clientDto);
        var retrievedClient = soapService.getClient(clientDto.telNumber);
        assertTrue(retrievedClient.isActive());

        soapService.unregisterClient(clientDto.telNumber);

        var unregisteredClient = soapService.getClient(clientDto.telNumber);
        assertFalse(unregisteredClient.isActive());
    }
}
