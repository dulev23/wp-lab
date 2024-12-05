package mk.finki.ukim.mk.lab.service.implement;

import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.repository.jpa.EventRepository;
import mk.finki.ukim.mk.lab.repository.jpa.LocationRepository;
import mk.finki.ukim.mk.lab.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImplementation implements EventService {

    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;

    public EventServiceImplementation(EventRepository eventRepository, LocationRepository locationRepository) {
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Event> listAll() {
        return eventRepository.findAll();
    }

    @Override
    public List<Event> searchEvents(String text) {
        return eventRepository.searchEventByName(text);
    }

    @Override
    public List<Event> searchByRating(double rating) {
        return eventRepository.searchEventByPopularityScoreGreaterThanEqual(rating);
    }

    @Override
    public Optional<Event> save(String name, String description, double rating, Long locationID) {
        Location location = locationRepository.findById(locationID)
                .orElseThrow(() -> new IllegalArgumentException("Location with ID " + locationID + " not found"));

        Event event = new Event(name, description, rating, location);
        return Optional.of(eventRepository.save(event));
    }


    @Override
    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public Optional<Event> findByName(String name) {
        return eventRepository.findByName(name);
    }

    @Override
    public List<Event> findAllByLocationID(Long LocationID) {
        return eventRepository.findAllByLocation_Id(LocationID);
    }

    @Override
    public Optional<Event> update(Long id, String name, String description, double rating, Long LocationID) {
        Location location = locationRepository.findById(LocationID)
                .orElseThrow(() -> new IllegalArgumentException("Location with ID " + LocationID + " not found"));

        Event event = eventRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Event with ID " + id + " not found"));
        event.setName(name);
        event.setDescription(description);
        event.setPopularityScore(rating);
        event.setLocation(location);
        return Optional.of(eventRepository.save(event));
    }
}
