package pl.pas.soap.endpoints;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.function.Executable;

import jakarta.xml.ws.WebServiceException;
import lombok.SneakyThrows;
import pl.pas.soap.config.PayaraContainerInitializer;
import pl.pas.soap.endpoints.dto.LockerDto;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LockerSoapApiTest extends PayaraContainerInitializer {

    static LockerEndpointApi soapService;

    @Override
    @BeforeAll
    public void setup() {
        super.setup();
        String wsdlUrl = baseUri + "/lockersSoapApi?wsdl";
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(LockerEndpointApi.class);
        factory.setAddress(wsdlUrl);

        soapService = (LockerEndpointApi) factory.create();
    }

    @Test
    @SneakyThrows
    void addLockerSoapTest() {
        LockerDto lockerDto = LockerDto.builder()
            .identityNumber("XZY01")
            .address("Cyprysowa 5b")
            .numberOfBoxes(10)
            .build();

        soapService.addLocker(lockerDto);

        var retrievedLocker = soapService.getLocker(lockerDto.identityNumber);
        assertEquals(lockerDto.identityNumber, retrievedLocker.getIdentityNumber());
        assertEquals(lockerDto.address, retrievedLocker.getAddress());
        assertEquals(lockerDto.numberOfBoxes, retrievedLocker.getDepositBoxes().size());
    }

    @Test
    @SneakyThrows
    void removeLockerSoapTest() {
        LockerDto lockerDto = LockerDto.builder()
            .identityNumber("XZY02")
            .address("Cyprysowa 5b")
            .numberOfBoxes(10)
            .build();
        soapService.addLocker(lockerDto);

        soapService.removeLocker(lockerDto.identityNumber);

        final Executable queryNonExistentLocker = () -> soapService.getLocker(lockerDto.identityNumber);
        assertThrows(WebServiceException.class, queryNonExistentLocker);
    }
}
