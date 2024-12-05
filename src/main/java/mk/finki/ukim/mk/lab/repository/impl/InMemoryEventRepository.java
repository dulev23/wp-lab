package mk.finki.ukim.mk.lab.repository.impl;

import mk.finki.ukim.mk.lab.bootstrap.DataHolder;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InMemoryEventRepository {

    public List<Event> findAll() {
        return DataHolder.events;
    }

    public List<Event> searchEvents(String text) {
        String lowerText = text.toLowerCase();
        if (lowerText.isEmpty()) {
            return null;
        }
        return DataHolder.events.stream().filter(event -> event.getName().toLowerCase().contains(lowerText) ||
                        event.getDescription().toLowerCase().contains(lowerText))
                .collect(Collectors.toList());
    }

    public List<Event> searchByRating(int rating) {
        return DataHolder.events.stream()
                .filter(event -> event.getPopularityScore() >= rating)
                .collect(Collectors.toList());
    }

    public Optional<Event> save(String name, String description, double rating,
                                Location location) {
        Event event_new = new Event(name, description, rating, location);
        DataHolder.events.removeIf(event -> event.getName().equals(name));
        DataHolder.events.add(event_new);
        return Optional.of(event_new);
    }

    public Optional<Event> findById(Long id) {
        return DataHolder.events.stream()
                .filter(event -> event.getId().equals(id))
                .findFirst();
    }

    public void deleteById(Long id) {
        DataHolder.events.removeIf(event -> event.getId().equals(id));
    }

    public Optional<Event> findByName(String name) {
        return DataHolder.events.stream().filter(event -> event.getName().equals(name)).findFirst();
    }
}