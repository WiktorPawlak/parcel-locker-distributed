package pl.pas.domain.core.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import pl.pas.domain.core.applicationmodel.exceptions.ParcelException;
import pl.pas.domain.core.applicationmodel.model.delivery.Parcel;

class ParcelTest {

    @Test
    void Should_ReturnSameCost_AsGivenToConstructor() {
        Parcel p = new Parcel(UUID.randomUUID(), 0L, BigDecimal.TEN, 1, 2, 3, 4, true);
        assertEquals(new BigDecimal("10.0"), p.getCost());
    }

    @ParameterizedTest(name = "when width = {0}, length = {1}, height = {2}, weight = {3} should throw exception")
    @CsvSource({
        "0, 2, 3, 4",
        "1, 0, 3, 4",
        "1, 2, 0, 4",
        "1, 2, 3, 0",
        "50, 2, 3, 4",
        "1, 50, 3, 4",
        "1, 2, 50, 4",
        "1, 2, 3, 50"
    })
    void Should_ThrowException_WhenGivenValuesAreNotCorrect(double width, double length, double height, double weight) {
        assertThrows(ParcelException.class, () -> new Parcel(UUID.randomUUID(), 0L, BigDecimal.TEN, width, length, height, weight, true));
    }
}
