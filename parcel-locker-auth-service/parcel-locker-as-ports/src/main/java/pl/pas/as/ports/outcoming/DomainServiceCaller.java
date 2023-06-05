package pl.pas.as.ports.outcoming;

import pl.pas.as.ports.outcoming.dto.DomainClientDto;

public interface DomainServiceCaller {

    void registerClientInDomain(final DomainClientDto dto, final String token);

    void unregisterClientInDomain(final String telNumber, final String token);
}
