package pl.joannaszczesna.carrental.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.joannaszczesna.carrental.model.Type;
import pl.joannaszczesna.carrental.service.RentalService;
import pl.joannaszczesna.carrental.model.Car;
import pl.joannaszczesna.carrental.model.Reservation;
import pl.joannaszczesna.carrental.repository.CarRepository;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class RentalController {
    private final CarRepository carRepository;
    private final RentalService rentalService;


    @PostMapping
    private ResponseEntity<Car> addCar(@RequestBody Car car) {
        Optional<Car> newCar = rentalService.addCar(car);
        if (newCar.isPresent()) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newCar.get().getId())
                    .toUri();
            return ResponseEntity.created(location)
                    .body(newCar.get());
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/{carId}")
    private ResponseEntity<Car> findById(@PathVariable Long carId) {
        Optional<Car> car = carRepository.findById(carId);
        return car.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

//    @PostMapping("{carType}/reservations")
//    private ResponseEntity<Reservation> createReservation(
//            @PathVariable("carType") Type type,
//            @RequestBody Reservation res) {
//        Reservation newReservation;
//        try {
//            long numDays = ChronoUnit.DAYS.between(res.getEndDate(), res.getStartDate());
//            newReservation =
//                    rentalService.createReservation(res.getStartDate(), (int) numDays, type);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.CONFLICT);
//        }
//
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(newReservation.getId())
//                .toUri();
//        return ResponseEntity.created(location)
//                .body(newReservation);
//    }

    @PostMapping("{carId}/reservations")
    private ResponseEntity<Reservation> makeReservation(
            @PathVariable("carId") long id,
            @RequestBody Reservation reservation) {

        Optional<Reservation> newReservation = rentalService.addReservation(reservation);
        if (newReservation.isPresent()) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newReservation.get().getId())
                    .toUri();
            return ResponseEntity.created(location)
                    .body(newReservation.get());
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
