package pl.joannaszczesna.carrental.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.joannaszczesna.carrental.model.Reservation;
import pl.joannaszczesna.carrental.model.Type;
import pl.joannaszczesna.carrental.service.RentalService;
import pl.joannaszczesna.carrental.service.ValidationService;

import java.time.LocalDateTime;
import java.util.EnumSet;

@RequiredArgsConstructor
@Controller
public class ViewController {

    private final RentalService rentalService;
    private final ValidationService validationService;

    @GetMapping
    public String displayAllCarsWithReservations(Model model) {
        model.addAttribute("cars", rentalService.getAllCarsWithReservations());
        model.addAttribute("reservationInfo", new ReservationInfo(Type.SUV, LocalDateTime.now(), 5));
        model.addAttribute("options", EnumSet.allOf(Type.class));
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String createReservation(Model model, @Valid @ModelAttribute ReservationInfo reservationInfo, RedirectAttributes redirectAttributes) {

        String formErr = validationService.validateForm(reservationInfo.startDate(), reservationInfo.numDays());
        if (!formErr.isEmpty()) {
            redirectAttributes.addFlashAttribute("errormessage", formErr);
            return "redirect:/";
        }

        LocalDateTime startDate = reservationInfo.startDate();

        try {
            Reservation createdReservation = rentalService
                    .createReservation(startDate, reservationInfo.numDays, reservationInfo.carType());
            redirectAttributes.addFlashAttribute("message",
                    "The reservation has been saved successfully!");
//        model.addAttribute("success", createdReservation.get());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errormessage",
                    "Booking failed. " + e.getMessage());
        }
        return "redirect:/";
    }

    public record ReservationInfo(Type carType, LocalDateTime startDate, int numDays) {
    }
}
