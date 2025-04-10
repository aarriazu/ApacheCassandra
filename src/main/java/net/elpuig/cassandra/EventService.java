package net.elpuig.cassandra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    // Guardar evento en la base de datos
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }
}
