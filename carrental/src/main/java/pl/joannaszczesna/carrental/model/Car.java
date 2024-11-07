package pl.joannaszczesna.carrental.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Type carType;
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "carId", updatable = false, insertable = false)
    private List<Reservation> reservations;

    public Car(Long id, Type type) {
        this.id = id;
        this.carType = type;
    }
    public Car(Type type) {
        this.carType = type;
    }
}
