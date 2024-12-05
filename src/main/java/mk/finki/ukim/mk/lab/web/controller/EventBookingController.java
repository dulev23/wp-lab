package mk.finki.ukim.mk.lab.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EventBookingController {

    @GetMapping("/eventBooking")
    public String bookingConfirmationPage() {
        return "bookingConfirmation";
    }
}
