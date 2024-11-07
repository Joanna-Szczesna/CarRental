package pl.joannaszczesna.carrental;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;
import pl.joannaszczesna.carrental.model.Car;
import pl.joannaszczesna.carrental.model.Type;
import pl.joannaszczesna.carrental.repository.CarRepository;
import pl.joannaszczesna.carrental.repository.ReservationRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CarrentalApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    @Transactional
    void getByExistId_returnSingleCar() throws Exception {
        Car newCar = new Car();
        newCar.setId(5L);
        newCar.setCarType(Type.SEDAN);
        carRepository.save(newCar);
        Long id = newCar.getId();
        MockHttpServletRequestBuilder requestBuilder = get("/cars/" + id);

        this.mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.carType").value("SEDAN"));
    }

//    @Test
//    @Transactional
//    void createReservation_returnCreatedReservation() throws Exception {
//        LocalDateTime start = LocalDateTime.now();
//        LocalDateTime end = start.plusDays(1);
//        Long carId = 1L;
//        Reservation reservation = new Reservation(null, start, end, carId);
//        Reservation savedReservation = reservationRepository.save(reservation);
//
//        MockHttpServletRequestBuilder requestBuilder = post("/");
//
//        this.mockMvc.perform(requestBuilder)
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.id").value(id))
//                .andExpect(jsonPath("$.carId").value(carId));
//    }
}
