package pl.pas.domain.rest.controllers.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDto {
    @NotEmpty
    public String firstName;
    @NotEmpty
    public String lastName;
    @NotEmpty
    public String telNumber;
}
