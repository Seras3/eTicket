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

public class TicketRepository {
    private TicketDAO ticketDao;
    private SeatDAO seatDao;

    public TicketRepository() {
        this.ticketDao = new TicketDAO();
        this.seatDao = new SeatDAO();
    }

    public Ticket get(String id) {
        Ticket ticket = ticketDao.get(id);
        if(ticket != null && ticket.getSeatId() != 0) {
            ticket = new SeatTicket(ticket);
            ((SeatTicket) ticket).setSeat(seatDao.get(ticket.getSeatId().toString()));
        }

        return ticket;
    }

    public Ticket find(Map<String, Object> params) {
        if(params.isEmpty())
            return null;

        Ticket ticket = ticketDao.find(params);

        if(ticket == null)
            return null;

        if(ticket.getSeatId() != 0) {
            SeatTicket seat_ticket = new SeatTicket(ticket);
            seat_ticket.setSeat(seatDao.get(seat_ticket.getSeatId().toString()));

            return seat_ticket;
        }

        return ticket;
    }

    public Ticket findToBuy(Map<String, Object> params) {
        if(params.isEmpty())
            return null;

        Ticket ticket = ticketDao.findToBuy(params);

        if(ticket == null)
            return null;

        if(ticket.getSeatId() != 0) {
            SeatTicket seat_ticket = new SeatTicket(ticket);
            seat_ticket.setSeat(seatDao.get(seat_ticket.getSeatId().toString()));

            return seat_ticket;
        }

        return ticket;
    }

    public SeatTicket find(Map<String, Object> params_ticket, Map<String, Object> params_seat) {
        if(params_ticket.isEmpty() || params_seat.isEmpty())
            return null;

        Ticket ticket = ticketDao.find(params_ticket);
        Seat seat = seatDao.findForTicket(ticket.getEventId().toString(), ticket.getName(), params_seat);
        ticket = ticketDao.find(new HashMap<>() {{
            put("seat_id", seat.getId());
        }});

        SeatTicket seat_ticket = new SeatTicket(ticket);
        seat_ticket.setSeat(seat);

        return seat_ticket;
    }

    public SeatTicket findToBuy(Map<String, Object> params_ticket, Map<String, Object> params_seat) {
        if(params_ticket.isEmpty() || params_seat.isEmpty())
            return null;

        Ticket ticket = ticketDao.findToBuy(params_ticket);
        Seat seat = seatDao.findForTicket(ticket.getEventId().toString(), ticket.getName(), params_seat);
        ticket = ticketDao.find(new HashMap<>() {{
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

        for(Ticket ticket : ticketDao.findAll(params)) {
            if(ticket.getSeatId() != 0) {
                SeatTicket seat_ticket = new SeatTicket(ticket);
                seat_ticket.setSeat(seatDao.get(seat_ticket.getSeatId().toString()));
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
        return ticketDao.findAllTicketRows(params);
    }

    public void deleteTicket(String id) {
        ticketDao.delete(ticketDao.get(id));
    }
}
