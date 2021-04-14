package service;


import context.Identity;
import dao.CartDAO;
import dao.LocationDAO;
import dto.*;
import mapper.EventMapper;
import mapper.LocationMapper;
import mapper.TicketMapper;
import model.*;
import repository.EventRepository;
import repository.TicketRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class API {
    private Identity identity;
    private TicketRepository ticketRepository;
    private EventRepository eventRepository;
    private CartDAO cartDao;
    private LocationDAO locationDao;


    public API(Identity identity) {
        this.identity = identity;
        this.ticketRepository = new TicketRepository();
        this.eventRepository = new EventRepository();
        this.cartDao = new CartDAO();
        this.locationDao = new LocationDAO();
    }

    public enum Result {
        OK,
        FAIL,
        NOT_FOUND
    }



    public List<TicketRowDTO> getTicketRowsForEvent(String id) {
        List<TicketRow> tickets = ticketRepository.findAllTicketRows(new HashMap<>(){{
            put("event_id", Integer.parseInt(id));
        }});

        List<TicketRowDTO> ticketRowDtos = new ArrayList<>();

        for(TicketRow ticket : tickets) {
            ticketRowDtos.add(TicketMapper.INSTANCE.ticketRowToTicketRowDto(ticket));
        }

        return ticketRowDtos;
    }

    public TicketDTO getTicketForEvent(String id, String name) {
        Ticket ticket = ticketRepository.find(new HashMap<String, Object>() {{
            put("event_id", Integer.valueOf(id));
            put("name", name);
        }});

        if(ticket instanceof SeatTicket)
            return TicketMapper.INSTANCE.seatTicketToTicketDto((SeatTicket)ticket);

        return TicketMapper.INSTANCE.ticketToTicketDto(ticket);
    }

    public TicketDTO getTicketForEvent(String id, String name, SeatDTO seat) {
        SeatTicket ticket = ticketRepository.find(new HashMap<String, Object>() {{
            put("event_id", Integer.valueOf(id));
            put("name", name);
        }}, new HashMap<>() {{
            put("row_ord", seat.getRowOrd());
            put("place", seat.getPlace());
        }});

        return TicketMapper.INSTANCE.seatTicketToTicketDto(ticket);
    }



    public List<EventRowDTO> getEventRows() {
        List<EventRowDTO> eventsDtoList = new ArrayList<EventRowDTO>();
        for(EventRow event : eventRepository.getAllEventRows()) {
            eventsDtoList.add(EventMapper.INSTANCE.eventRowToEventRowDto(event));
        }
        return eventsDtoList;
    }

    public EventCompactDTO getEventCompact(String id) {
        return EventMapper.INSTANCE.eventCompactToEventCompactDto(eventRepository.getEventCompact(id));
    }



    public List<EventDTO> getAllEvents() {
        List<EventDTO> events = new ArrayList<>();
        for(Event event : eventRepository.getAllEvents()) {
            events.add(EventMapper.INSTANCE.eventToEventDto(event));
        }

        return events;
    }

    public EventDTO getEvent(String id) {
        return EventMapper.INSTANCE.eventToEventDto(eventRepository.getEvent(id));
    }

    public Result postEvent(EventDTO event) {
        return  eventRepository.addEvent(event) ? Result.OK : Result.FAIL;
    }

    public Result putEvent(EventDTO event) {
        return  eventRepository.updateEvent(event) ? Result.OK : Result.FAIL;
    }

    public Result deleteEvent(String id) {
        return eventRepository.deleteEvent(id) ? Result.OK : Result.FAIL;
    }



    public Result postEventLocation(EventLocationDTO eventLocation) {
        return eventRepository.addEventLocation(eventLocation) ? Result.OK : Result.FAIL;
    }

    public Result deleteEventLocation(String id) {
        return eventRepository.deleteEventLocation(id) ? Result.OK : Result.FAIL;
    }


    public List<LocationDTO> getAllLocations() {
        List<LocationDTO> locations = new ArrayList<>();
        for(Location location : locationDao.getAll()) {
            locations.add(LocationMapper.INSTANCE.locationToLocationDto(location));
        }

        return locations;
    }


    public Result postCart(String id) {
        if(cartDao.add(new Cart(identity.getId(), Integer.valueOf(id)))) {
            return Result.OK;
        } else {
            return Result.FAIL;
        }

    }

}
