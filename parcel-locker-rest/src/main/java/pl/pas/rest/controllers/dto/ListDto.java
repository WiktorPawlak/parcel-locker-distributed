package pl.pas.rest.controllers.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListDto {
    @NotNull
    public BigDecimal basePrice;
    public boolean isPriority;
}
