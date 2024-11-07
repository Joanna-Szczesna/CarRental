package pl.joannaszczesna.carrental.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ValidationService {

    public String validateForm(LocalDateTime startDate, int numbers) {
        String message = "";

        if (startDate != null) {
            LocalDate today = LocalDate.now();
            if (startDate.toLocalDate().isBefore(today)) {
                message += "Reservation start date should be after today ";
            }
        }
        if (numbers < 1) {
            message += "*Days should be more than zero";
        }
        return message;
    }
}
