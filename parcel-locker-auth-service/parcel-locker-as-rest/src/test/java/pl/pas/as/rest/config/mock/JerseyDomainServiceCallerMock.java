package pl.pas.as.rest.config.mock;

import pl.pas.as.ports.outcoming.DomainServiceCaller;
import pl.pas.as.ports.outcoming.dto.DomainClientDto;

public class JerseyDomainServiceCallerMock implements DomainServiceCaller {

    @Override
    public void registerClientInDomain(final DomainClientDto dto, final String token) {
    }

    @Override
    public void unregisterClientInDomain(final String telNumber, final String token) {
    }
}
