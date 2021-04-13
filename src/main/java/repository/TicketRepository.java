package repository;

import dao.SeatDAO;
import dao.TicketDAO;
import model.SeatTicket;
import model.Ticket;

import java.util.ArrayList;
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

}
