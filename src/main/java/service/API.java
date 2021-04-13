package service;


import context.Identity;
import dao.CartDAO;
import dao.EventDAO;
import dto.*;
import mapper.EventMapper;
import mapper.TicketMapper;
import model.*;
import repository.TicketRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class API {
    private Identity identity;
    private EventDAO eventDao;
    private TicketRepository ticketRepository;
    private CartDAO cartDao;

    public API(Identity identity) {
        this.identity = identity;
        this.eventDao = new EventDAO();
        this.ticketRepository = new TicketRepository();
        this.cartDao = new CartDAO();
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


    public List<EventRowDTO> getEvents() {
        List<EventRowDTO> events_dto_list = new ArrayList<EventRowDTO>();
        for(Event event : eventDao.getAll()) {
            events_dto_list.add(EventMapper.INSTANCE.eventToEventRowDto(event));
        }
        return events_dto_list;
    }

    public EventDTO getEvent(String id) {
        return EventMapper.INSTANCE.eventToEventDto(eventDao.get(id.toString()));
    }

    public Result postEvent(EventDTO event) {
        eventDao.add(EventMapper.INSTANCE.eventDtoToEvent(event));
        return Result.OK;
    }

    public Result putEvent(EventDTO event) {
        eventDao.update(EventMapper.INSTANCE.eventDtoToEvent(event));
        return Result.OK;
    }

    public Result deleteEvent(String id) {
        EventDTO event = new EventDTO();
        event.setId(Integer.parseInt(id));
        eventDao.delete(EventMapper.INSTANCE.eventDtoToEvent(event));
        return Result.OK;
    }


    public Result postCart(String id) {
        cartDao.add(new Cart(identity.getId(), Integer.valueOf(id)));
        return Result.OK;
    }

}
