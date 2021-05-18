package repository;

import dao.*;
import dto.EventDTO;
import dto.EventLocationDTO;
import mapper.EventMapper;
import model.Event;
import model.EventCompact;
import model.EventLocation;
import model.EventRow;

import java.util.HashMap;
import java.util.List;

public class EventRepository {
    private EventDAO eventDao;
    private LocationDAO locationDao;
    private EventCategoryDAO eventCategoryDao;
    private EventLocationDAO eventLocationDao;

    public EventRepository() {
        this.eventDao = new EventDAO();
        this.locationDao = new LocationDAO();
        this.eventCategoryDao = new EventCategoryDAO();
        this.eventLocationDao = new EventLocationDAO();

    }


    public EventCompact getEventCompact(String id) {
        EventCompact event = new EventCompact();

        EventLocation eventLocation = eventLocationDao.get(id);

        if(eventLocation == null)
            return null;

        event.setEventLocation(eventLocationDao.get(id));

        event.setEvent(eventDao.get(event.getEventLocation().getEventId().toString()));


        event.setLocation(locationDao.find(new HashMap<String, Object>() {{
            put("id", event.getEventLocation().getLocationId());
        }}));

        event.setCategory(eventCategoryDao.find(new HashMap<String, Object>() {{
            put("id", event.getEvent().getCategoryId());
        }}));

        return event;
    }

    public List<EventRow> getAllEventRows() {
        return eventDao.getAllEventRow();
    }

    public List<Event> getAllEvents() {
        return eventDao.getAll();
    }

    public Event getEvent(String id) {
        return eventDao.get(id);
    }

    public boolean addEvent(EventDTO event) {
        return eventDao.add(EventMapper.INSTANCE.eventDtoToEvent(event));
    }

    public boolean updateEvent(EventDTO event) {
        return eventDao.update(EventMapper.INSTANCE.eventDtoToEvent(event));
    }

    public boolean deleteEvent(String id) {
        EventDTO event = new EventDTO();
        event.setId(Integer.parseInt(id));
        return eventDao.delete(EventMapper.INSTANCE.eventDtoToEvent(event));
    }

    public boolean addEventLocation(EventLocationDTO event) {
        return eventLocationDao.add(EventMapper.INSTANCE.eventLocationDtoToEventLocation(event));
    }

    public boolean deleteEventLocation(String id) {
        EventLocationDTO event = new EventLocationDTO();
        event.setId(Integer.parseInt(id));
        return eventLocationDao.delete(EventMapper.INSTANCE.eventLocationDtoToEventLocation(event));
    }
}
