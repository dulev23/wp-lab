package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> listAll();

    List<Event> searchEvents(String text);

    List<Event> searchByRating(double rating);

    Optional<Event> save(String name, String description, double rating, Long LocationID);

    Optional<Event> findById(Long id);

    void deleteById(Long id);

    Optional<Event> findByName(String name);

    List<Event> findAllByLocationID(Long LocationID);

    Optional<Event> update(Long id, String name, String description, double rating, Long LocationID);
}