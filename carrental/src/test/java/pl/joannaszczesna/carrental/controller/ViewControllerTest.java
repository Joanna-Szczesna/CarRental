package pl.joannaszczesna.carrental.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import pl.joannaszczesna.carrental.service.RentalService;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ViewControllerTest {
    @Mock
    private RentalService service;

    @InjectMocks
    private ViewController controller;

    @Test
    void name() {
        ModelAndView model = new ModelAndView();
        Model newModel = new ExtendedModelMap();
        controller.displayAllCarsWithReservations(newModel);
    }
}