package pl.joannaszczesna.carrental.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import pl.joannaszczesna.carrental.model.Car;
import pl.joannaszczesna.carrental.model.Reservation;
import pl.joannaszczesna.carrental.model.Type;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class RepositoriesTest {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @DirtiesContext
    @Test
    public void emptyDB_findAllCars_dbIsEmpty() {
        carRepository.deleteAll();
        List<Car> allCars = carRepository.findAllCars();

        assert (allCars.isEmpty());
    }

    @DirtiesContext
    @Test
    public void addCar_returnCar() {
        carRepository.deleteAll();
        Type carType = Type.SUV;
        Car car = new Car(carType);
        Car savedCar = carRepository.save(car);

        List<Car> allCars = carRepository.findAllCars();

        assert (savedCar.getCarType()).equals(carType);
        assert (allCars.size() == 1);
    }

    @DirtiesContext
    @Test
    public void addReservation_returnReservation() {
        carRepository.deleteAll();

        Car car = new Car(Type.SUV);
        Car savedCar = carRepository.save(car);

        LocalDateTime startDate = LocalDateTime.of(2024, 10, 1, 05, 05, 00);
        LocalDateTime endDate = startDate.plusDays(3L);
        Long carId = savedCar.getId();

        Reservation reservation = new Reservation(null, startDate, endDate, carId);
        Reservation savedRes = reservationRepository.save(reservation);

        assert (savedRes.getStartDate()).equals(startDate);
        assert (savedRes.getEndDate()).equals(endDate);
        assert (savedRes.getCarId()).equals(carId);
    }

    @DirtiesContext
    @Test
    public void addReservation_whenCarNotExist_noReservationAdded() {
        carRepository.deleteAll();
        LocalDateTime startDate = LocalDateTime.of(2024, 10, 1, 05, 05, 00);
        LocalDateTime endDate = startDate.plusDays(4L);
        Long carId = 1L;
        String errMessage = "";
        Reservation reservation = new Reservation(null, startDate, endDate, carId);
        try {
            Reservation savedRes = reservationRepository.save(reservation);
        } catch (Exception e) {
            errMessage = e.getMessage();
            System.out.println(e.getMessage());
        }

        assert (errMessage).contains("Referential integrity constraint violation");
    }
}