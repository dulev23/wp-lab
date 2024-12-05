package mk.finki.ukim.mk.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<Event> events = new ArrayList<>();
    public static List<Location> locations = new ArrayList<>();

//    @PostConstruct
//    public void init() {
//        locations.add(new Location("Location1", "Address1", "capacity1", "desc1"));
//        locations.add(new Location("Location2", "Address2", "capacity2", "desc2"));
//        locations.add(new Location("Location3", "Address3", "capacity3", "desc3"));
//        locations.add(new Location("Location4", "Address4", "capacity4", "desc4"));
//        locations.add(new Location("Location5", "Address5", "capacity5", "desc5"));
//
//        events.add(new Event("Event1", "Desc1", 10.0, locations.get(0)));
//        events.add(new Event("Event2", "Desc2", 11.0, locations.get(1)));
//        events.add(new Event("Event3", "Desc3", 12.0, locations.get(2)));
//        events.add(new Event("Event4", "Desc4", 13.0, locations.get(0)));
//        events.add(new Event("Event5", "Desc5", 14.0, locations.get(1)));
//        events.add(new Event("Event6", "Desc6", 15.0, locations.get(2)));
//        events.add(new Event("Event7", "Desc7", 16.0, locations.get(0)));
//        events.add(new Event("Event8", "Desc8", 17.0, locations.get(1)));
//        events.add(new Event("Event9", "Desc9", 18.0, locations.get(2)));
//        events.add(new Event("Event10", "Desc10", 19.0, locations.get(1)));
//    }
}
