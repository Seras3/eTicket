package service;


import context.Identity;
import dao.CartDAO;
import dao.LocationDAO;
import dao.OrderDAO;
import dto.*;
import mapper.CartMapper;
import mapper.EventMapper;
import mapper.LocationMapper;
import mapper.TicketMapper;
import model.*;
import repository.EventRepository;
import repository.TicketRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class API {
    private Identity identity;
    private TicketRepository ticketRepository;
    private EventRepository eventRepository;
    private CartDAO cartDao;
    private LocationDAO locationDao;
    private OrderDAO orderDao;

    private LoggingService loggingService;


    public API(Identity identity) {
        this.identity = identity;
        this.ticketRepository = new TicketRepository();
        this.eventRepository = new EventRepository();
        this.cartDao = new CartDAO();
        this.locationDao = new LocationDAO();
        this.orderDao = new OrderDAO();
        this.loggingService = new LoggingService();
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

    public TicketDTO getTicketToBuyForEvent(String id, String name) {
        Ticket ticket = ticketRepository.findToBuy(new HashMap<String, Object>() {{
            put("event_id", Integer.valueOf(id));
            put("name", name);
        }});

        if(ticket == null)
            return null;

        if(ticket instanceof SeatTicket)
            return TicketMapper.INSTANCE.seatTicketToTicketDto((SeatTicket)ticket);

        return TicketMapper.INSTANCE.ticketToTicketDto(ticket);
    }

    public TicketDTO getTicketToBuyForEvent(String id, String name, SeatDTO seat) {
        SeatTicket ticket = ticketRepository.findToBuy(new HashMap<String, Object>() {{
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
        EventCompact eventCompact = eventRepository.getEventCompact(id);
        if(eventCompact == null)
            return null;

        return EventMapper.INSTANCE.eventCompactToEventCompactDto(eventCompact);
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
            loggingService.addLog(String.format("Add ticket %s in cart", id));
            return Result.OK;
        } else {
            return Result.FAIL;
        }

    }

    public List<CartRowDTO> getCart(){
        var res = cartDao.findAll(new HashMap<String, Object>(){{
            put("account_id", identity.getId());
        }});

        Map<Integer, CartRow> cartView = new HashMap<Integer, CartRow>();
        res.forEach((cart) -> {
            Ticket ticket = ticketRepository.get(cart.getTicketId().toString());
            CartRow cartRow;

            if(!cartView.containsKey(ticket.getEventId())) {
                cartRow = new CartRow();

                EventCompact eventCompact = eventRepository.getEventCompact(ticket.getEventId().toString());
                Event event = eventCompact.getEvent();
                event.setId(eventCompact.getEventLocation().getId());

                cartRow.setEvent(event);
                cartRow.setTickets(new ArrayList<Ticket>());

                cartView.put(ticket.getEventId(), cartRow);
            } else {
                cartRow = cartView.get(ticket.getEventId());
            }

            cartRow.getTickets().add(ticket);
        });

        List<CartRowDTO> dto = new ArrayList<CartRowDTO>();
        cartView.forEach((k, v) -> dto.add(CartMapper.INSTANCE.cartRowToCartRowDto(v)));
        return dto;

    }

    public Result placeOrder() {
        getCart().forEach((row) -> {
            row.getTickets().forEach((ticket) -> {
                Order order = new Order();
                order.setAccountId(identity.getId());
                order.setTicketId(ticket.getId());
                orderDao.add(order);
                deleteTicketFromCart(ticket.getId().toString());
            });
        });

        loggingService.addLog("Place an order.");
        return Result.OK;
    }

    public Result deleteTicketFromCart(String id) {
        var res = cartDao.find(new HashMap<String, Object>(){{
            put("account_id", identity.getId());
            put("ticket_id", Integer.parseInt(id));
        }});

        if(res != null) {
            cartDao.delete(res);
            return Result.OK;
        }

        return Result.NOT_FOUND;
    }
}
