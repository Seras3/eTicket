package repository;

import dao.SeatDAO;
import dao.TicketDAO;
import model.Seat;
import model.SeatTicket;
import model.Ticket;
import model.TicketRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketRepository implements Repository<Ticket> {
    private TicketDAO ticket_dao;
    private SeatDAO seat_dao;

    public TicketRepository() {
        this.ticket_dao = new TicketDAO();
        this.seat_dao = new SeatDAO();
    }

    @Override
    public Ticket get(String id) {
        Ticket ticket = ticket_dao.get(id);
        if(ticket != null && ticket.getSeatId() != 0) {
            ticket = new SeatTicket(ticket);
            ((SeatTicket) ticket).setSeat(seat_dao.get(ticket.getSeatId().toString()));
        }

        return ticket;
    }

    public Ticket find(Map<String, Object> params) {
        if(params.isEmpty())
            return null;

        Ticket ticket = ticket_dao.find(params);

        if(ticket.getSeatId() != 0) {
            SeatTicket seat_ticket = new SeatTicket(ticket);
            seat_ticket.setSeat(seat_dao.get(seat_ticket.getSeatId().toString()));

            return seat_ticket;
        }

        return ticket;
    }

    public SeatTicket find(Map<String, Object> params_ticket, Map<String, Object> params_seat) {
        if(params_ticket.isEmpty() || params_seat.isEmpty())
            return null;

        Ticket ticket = ticket_dao.find(params_ticket);
        Seat seat = seat_dao.findForTicket(ticket.getEventId().toString(), ticket.getName(), params_seat);
        ticket = ticket_dao.find(new HashMap<>() {{
            put("seat_id", seat.getId());
        }});

        SeatTicket seat_ticket = new SeatTicket(ticket);
        seat_ticket.setSeat(seat);

        return seat_ticket;
    }

    public List<Ticket> findAll(Map<String, Object> params) {
        if(params.isEmpty())
            return null;

        List<Ticket> tickets = new ArrayList<Ticket>();

        for(Ticket ticket : ticket_dao.findAll(params)) {
            if(ticket.getSeatId() != 0) {
                SeatTicket seat_ticket = new SeatTicket(ticket);
                seat_ticket.setSeat(seat_dao.get(seat_ticket.getSeatId().toString()));
                tickets.add(seat_ticket);
            } else  {
                tickets.add(ticket);
            }
        }

        if(!tickets.isEmpty())
            return tickets;

        return null;
    }

    public List<TicketRow> findAllTicketRows(Map<String, Object> params) {
        return ticket_dao.findAllTicketRows(params);
    }

}
