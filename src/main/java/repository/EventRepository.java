package repository;

import dao.*;
import model.EventCompact;

import java.util.HashMap;

public class EventRepository implements Repository<EventCompact> {
    private EventDAO event_dao;
    private LocationDAO location_dao;
    private EventCategoryDAO event_category_dao;
    private EventLocationDAO event_location_dao;

    public EventRepository() {
        this.event_dao = new EventDAO();
        this.location_dao = new LocationDAO();
        this.event_category_dao = new EventCategoryDAO();
        this.event_location_dao = new EventLocationDAO();

    }

    @Override
    public EventCompact get(String id) {
        EventCompact event = new EventCompact();

        event.setEventLocation(event_location_dao.get(id));

        event.setEvent(event_dao.get(event.getEventLocation().getEventId().toString()));


        event.setLocation(location_dao.find(new HashMap<String, Object>() {{
            put("id", event.getEventLocation().getLocationId().toString());
        }}));

        event.setCategory(event_category_dao.find(new HashMap<String, Object>() {{
            put("id", event.getEvent().getCategoryId().toString());
        }}));

        return event;
    }

}
