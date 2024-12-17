package mk.finki.ukim.mk.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.EventBooking;
import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.service.EventBookingService;
import mk.finki.ukim.mk.lab.service.EventService;
import mk.finki.ukim.mk.lab.service.LocationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;
    private final LocationService locationService;
    private final EventBookingService eventBookingService;

    public EventController(EventService eventService, LocationService locationService, EventBookingService eventBookingService) {
        this.eventService = eventService;
        this.locationService = locationService;
        this.eventBookingService = eventBookingService;
    }

    @GetMapping
    public String getEventsPage(@RequestParam(required = false) String error, HttpServletRequest req, Model model) {
        List<Event> eventList = eventService.listAll();
        model.addAttribute("events", eventList);
        List<Location> locations = locationService.findAll();
        model.addAttribute("locations", locations);
        String searchByName = req.getParameter("searchByName");
        String searchByRating = req.getParameter("searchByRating");
        String searchByLocation = req.getParameter("searchByLocation");
        double rating;

        if (searchByName != null && searchByRating != null && !searchByName.isEmpty() && !searchByRating.isEmpty() && searchByLocation != null && !searchByLocation.isEmpty()) {
            rating = Integer.parseInt(searchByRating);
            eventList = eventList.stream().filter(event -> event.getName().toLowerCase().contains(searchByName.toLowerCase())
                            && event.getPopularityScore() >= rating)
                    .toList();
        } else if (searchByName != null && !searchByName.isEmpty()) {
            eventList = eventService.searchEvents(searchByName);
        } else if (searchByRating != null && !searchByRating.isEmpty()) {
            rating = Integer.parseInt(searchByRating);
            eventList = eventService.searchByRating(rating);
        } else if (searchByLocation != null && !searchByLocation.isEmpty()) {
            Long locationID = Long.parseLong(searchByLocation);
            eventList = eventService.findAllByLocationID(locationID);
        } else {
            eventList = eventService.listAll();
        }
        model.addAttribute("events", eventList);
        return "listEvents";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAddEventPage(Model model) {
        model.addAttribute("locations", locationService.findAll());
        model.addAttribute("events", eventService.listAll());
        return "add-event";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveEvent(@RequestParam(required = false) Long id,
                            @RequestParam String eventName,
                            @RequestParam String description,
                            @RequestParam double popularityScore,
                            @RequestParam Long location) {
        if (id == null) {
            eventService.save(eventName, description, popularityScore, location);
        } else {
            eventService.update(id, eventName, description, popularityScore, location);
        }
        return "redirect:/events";
    }

    @GetMapping("/edit-form/{Id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String getEditEventForm(@PathVariable Long Id, Model model) {
        Optional<Event> findEvent = eventService.findById(Id);
        if (findEvent.isPresent()) {
            Event event = this.eventService.findById(Id).get();
            model.addAttribute("event", event);
            model.addAttribute("locations", locationService.findAll());
            return "add-event";
        } else {
            model.addAttribute("hasError", true);
            model.addAttribute("error", "Настанот со ID " + Id + " не постои.");
            return "redirect:/events";
        }
    }

    @GetMapping("/delete-form/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String showDeleteFormPage(@PathVariable Long id, Model model) {
        Event event = eventService.findById(id).get();
        model.addAttribute("locations", locationService.findAll());
        model.addAttribute("event", event);
        return "delete-event";
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteById(id);
        return "redirect:/events";
    }

    @PostMapping("/booking")
    public String eventBooking(@RequestParam String eventName,
                               @RequestParam String attendeeName,
                               @RequestParam String attendeeAddress,
                               @RequestParam int numberOfTickets,
                               HttpSession session) {
        EventBooking eventBooking = eventBookingService.placeBooking(eventName, attendeeName, attendeeAddress, numberOfTickets);
        session.setAttribute("eventBooking", eventBooking);
        return "redirect:/eventBooking";
    }
}