
import model.SeatTicket;
import model.Ticket;
import repository.TicketRepository;
import service.API;
import context.Identity;

import java.util.HashMap;


public class Test {
    public static void main(String[] args) {
        API api = new API(new Identity(1, "cineva", 1));

        TicketRepository ticket_repo = new TicketRepository();
        for(Ticket t : ticket_repo.findAll(new HashMap<String, Object>() {{
            put("event_id", 1);
        }})) {
            if(t instanceof SeatTicket) {
                System.out.println(t);
                System.out.println(t.getName());
                System.out.println(t.getDescription());
            } else {
                System.out.println(t);
            }
            System.out.println();
        }
    }
}
