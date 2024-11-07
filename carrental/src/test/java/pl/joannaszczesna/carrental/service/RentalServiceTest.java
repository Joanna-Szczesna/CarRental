package pl.joannaszczesna.carrental.service;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import pl.joannaszczesna.carrental.model.Type;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RentalServiceTest {

    private static String baseUri;
    private static final String CARS_ENDPOINT_PATH = "/cars";
    private static final String SUV_ENDPOINT_PATH = "/SUV";
    private static final String RESERVATIONS_ENDPOINT_PATH = "/reservations";

    @BeforeAll
    static void setUp(@LocalServerPort int port) {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        baseUri = "%s:%d".formatted(RestAssured.baseURI, port);
    }

    private static String makeCarRequestBody(Type type) {
        return """
                {
                    "carType": "%s"
                }""".formatted(type);
    }

    private static String makeReservationRequestBody(LocalDateTime startDate, LocalDateTime endDate, Long idCar) {
        return """
                {
                    "startDate": "%s",
                    "endDate": "%s",
                    "carId": "%s"
                }""".formatted(startDate, endDate, idCar);
    }

    @Test
    void whenAddCar_successful() {
        String locationExpected = baseUri + CARS_ENDPOINT_PATH;
        String reqBody = makeCarRequestBody(Type.SEDAN);

        given().
                body(reqBody)
                .contentType(ContentType.JSON)
                .when()
                .post(CARS_ENDPOINT_PATH)
                .then()
                .assertThat().statusCode(201)
                .and().body("carType", is("SEDAN"));
    }

    @Test
    void makeReservation_successful() {
        LocalDateTime startDate = LocalDateTime.of(2024, 10, 1, 05, 05, 00);
        LocalDateTime endDate = startDate.plusDays(5L);
        Long idCar = 1L;
        String reqBody = makeReservationRequestBody(startDate, endDate, idCar);
        String reservationPath = CARS_ENDPOINT_PATH + "/" + idCar + RESERVATIONS_ENDPOINT_PATH;

        given().
                body(reqBody)
                .contentType(ContentType.JSON)
                .when()
                .post(reservationPath)
                .then()
                .assertThat().statusCode(201)
                .and().body(
                        "carId", is(idCar.intValue()));
//                        "carId", is(idCar.intValue()),
//                        "startDate", is("2024-10-01T05:00"),
//                        "endDate", is("2024-10-06T05:00"));
    }


//    @Test
//    void createReservation_noPossibleCar_unsuccessful() {
//        LocalDateTime startDate = LocalDateTime.of(2024, 10, 15, 05, 05, 00);
//        LocalDateTime endDate = startDate.plusDays(5L);
//        Type carType = Type.SUV;
//        Long idCar = 1L;
//        String reservationPath = CARS_ENDPOINT_PATH + SUV_ENDPOINT_PATH + RESERVATIONS_ENDPOINT_PATH;
//        String reqBody = makeReservationRequestBody(startDate, endDate, idCar);
//
//        given().
//                body(reqBody)
//                .contentType(ContentType.JSON)
//                .when()
//                .post(reservationPath)
//                .then()
//                .body(
//                        "carId", is(idCar.intValue()));
//    }

}