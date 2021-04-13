
import graphic.GUI;
import repository.TicketRepository;
import service.API;
import context.Identity;

import java.util.HashMap;


public class Test {
    public static void main(String[] args) {
        API api = new API(new Identity(1, "cineva", 1));

        System.out.println(api.getTicketForEvent("1", "General Access", GUI.getSeat()));
    }
}
