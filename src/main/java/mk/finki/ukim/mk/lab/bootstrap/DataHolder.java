package mk.finki.ukim.mk.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.finki.ukim.mk.lab.model.Event;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {

    public static List<Event> events = new ArrayList<>();

    @PostConstruct
    public void init(){
        events.add(new Event("Event1", "Description1", 0.5));
        events.add(new Event("Event2", "Description2", 0.6));
        events.add(new Event("Event3", "Description3", 0.7));
        events.add(new Event("Event4", "Description4", 0.8));
        events.add(new Event("Event5", "Description5", 0.9));
        events.add(new Event("Event6", "Description5", 0.9));
        events.add(new Event("Event7", "Description5", 0.9));
        events.add(new Event("Event8", "Description5", 0.9));
        events.add(new Event("Event9", "Description5", 0.9));
        events.add(new Event("Event10", "Description5", 0.9));

    }

}
