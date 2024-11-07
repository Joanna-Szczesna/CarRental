package pl.joannaszczesna.carrental.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.joannaszczesna.carrental.model.Car;
import pl.joannaszczesna.carrental.model.Reservation;
import pl.joannaszczesna.carrental.model.Type;
import pl.joannaszczesna.carrental.repository.CarRepository;
import pl.joannaszczesna.carrental.repository.ReservationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;

    public Optional<Car> addCar(Car car) {
        return Optional.of(carRepository.save(car));
    }

    @Transactional
    public List<Car> getAllCarsWithReservations() {
        return carRepository.findAllCars();
    }

    @Transactional
    public Reservation createReservation(LocalDateTime startDate, int days, Type type) {
        LocalDateTime endDate = startDate.plusDays(days);
        List<Long> possibleIdsCarsDate = carRepository.findPossibleCarsIds(
                startDate, endDate, type);

        return possibleIdsCarsDate.stream()
                .findFirst()
                .map(c -> new Reservation(null, startDate, endDate, c))
                .map(reservationRepository::save).orElseThrow(
                        ()-> new RuntimeException("No car available at that time"));
    }

    @Transactional
    public Optional<Reservation> addReservation(Reservation reservation) {
        return Optional.of(reservationRepository.save(reservation));
    }
}
