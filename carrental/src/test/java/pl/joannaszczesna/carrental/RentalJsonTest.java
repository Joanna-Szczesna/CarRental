package pl.joannaszczesna.carrental;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.json.ObjectContent;
import pl.joannaszczesna.carrental.model.Car;
import pl.joannaszczesna.carrental.model.Reservation;
import pl.joannaszczesna.carrental.model.Type;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class RentalJsonTest {

    @Autowired
    private JacksonTester<Reservation> json;
    @Autowired
    private JacksonTester<Car> carJson;

    @Test
    void serializationOfReservation_returnsCorrectObject() throws IOException {
        Long idReservation = 15L;
        Long idCar = 2L;
        LocalDateTime startDate = LocalDateTime.of(2024, 10, 1, 05, 05, 00);
        LocalDateTime endDate = LocalDateTime.of(2024, 10, 15, 05, 05, 00);
        Reservation reservation = new Reservation(idReservation, startDate, endDate, idCar);

        JsonContent<Reservation> result = this.json.write(reservation);

        assertThat(result).hasJsonPathValue("$.id", idReservation);
        assertThat(result).hasJsonPathValue("$.carId", idCar);
        assertThat(result).hasJsonPathValue("$.startDate", "01-10-2024T05:05:00");
        assertThat(result).hasJsonPathValue("$.endDate", "15-10-2024T05:05:00");
    }

    @Test
    void deserializationOfReservation_returnsCorrectJSON() throws IOException {
        Long idReservation = 15L;
        Long idCar = 2L;
        LocalDateTime startDate = LocalDateTime.of(2024, 10, 1, 05, 05);
        LocalDateTime endDate = LocalDateTime.of(2024, 10, 15, 05, 05);

        String expected = """
                {
                  "id": 15,
                  "startDate": "2024-10-01T05:05:00",
                  "endDate": "2024-10-15T05:05:00",
                  "carId": 2
                }
                """;
        ObjectContent<Reservation> parsedReservation = json.parse(expected);

        assertThat(parsedReservation).isEqualTo(new Reservation(idReservation, startDate, endDate, idCar));
        assertThat(json.parseObject(expected).getId()).isEqualTo(idReservation);
        assertThat(json.parseObject(expected).getStartDate()).isEqualTo("2024-10-01T05:05:00");
        assertThat(json.parseObject(expected).getEndDate()).isEqualTo("2024-10-15T05:05:00");
        assertThat(json.parseObject(expected).getCarId()).isEqualTo(idCar);
    }

    @Test
    void carSerialization_returnCorrectCar() throws IOException {
        Long idCar = 3L;
        Car car = new Car(idCar, Type.SEDAN);
        JsonContent<Car> result = this.carJson.write(car);

        assertThat(result).isEqualToJson("car.json");
        assertThat(result).hasJsonPathValue("$.id", idCar);
        assertThat(result).hasJsonPathValue("$.carType", Type.SEDAN.toString());
    }

    @Test
    void carDeserialization_returnJSON() throws IOException {
        String expected = """
                {
                  "id": 3,
                  "carType": "SUV"
                }
                """;

        ObjectContent<Car> parsedCar = carJson.parse(expected);

        assertThat(parsedCar).isEqualTo(new Car(3L, Type.SUV));
        assertThat(carJson.parseObject(expected).getId()).isEqualTo(3);
        assertThat(carJson.parseObject(expected).getCarType()).isEqualTo(Type.SUV);
    }
}
