package pl.joannaszczesna.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.joannaszczesna.carrental.model.Car;
import pl.joannaszczesna.carrental.model.Type;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("SELECT c FROM Car c"
            + " left join fetch c.reservations")
    List<Car> findAllCars();

    @Query("SELECT c.id FROM Car c " +
            "LEFT JOIN Reservation r ON c.id = r.carId " +
            "AND r.startDate <= :endDate AND r.endDate >= :startDate "+
            "WHERE c.carType = :type "+
            "GROUP BY c.id " +
            "HAVING COUNT(r.id) = 0")
    List<Long> findPossibleCarsIds(LocalDateTime startDate, LocalDateTime endDate, Type type);
}
