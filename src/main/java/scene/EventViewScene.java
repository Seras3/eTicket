package scene;

import dto.*;
import graphic.GUI;
import service.API;
import context.Context;
import util.Command;
import util.Filter;

import java.util.HashMap;
import java.util.Map;

public class EventViewScene extends Scene {
    private Map<String, Command> commands;
    private API api;
    private EventCompactDTO event;

    public EventViewScene(String id) {
        super("Event " + id);

        this.api = new API(Context.getIdentity());
        event = api.getEventCompact(id);

        if(event != null) {
            this.commands = new HashMap<String, Command>();

            commands.put("/tickets", new Command(2,
                    Command.GenerateAccessibilityFor.Everyone(),
                    "/tickets", "Show tickets.", (args) -> {
                GUI.ticketsList(api.getTicketRowsForEvent(event.getEventLocation().getId().toString()));
                return Command.Result.OK;
            }));

            commands.put("/add_tickets", new Command(2,
                    Command.GenerateAccessibilityFor.Admin(),
                    "/add_tickets", "Add tickets to this event.", (args) -> {
                return Command.Result.OK;
            }));

            commands.put("/buy", new Command(2,
                    Command.GenerateAccessibilityFor.User(),
                    "/buy", "Buy ticket ( -name ).", (args) -> {
                for (String key : args.keySet()) {
                    if(key.equals("-name")){
                        String name = args.get("-name").replace('_', ' ');
                        TicketDTO ticket = api.getTicketForEvent(event.getEventLocation().getId().toString(), name);

                        if(ticket != null){
                            if(ticket.getSeat() != null) {
                                SeatDTO seat = GUI.getSeat();
                                ticket = api.getTicketForEvent(event.getEventLocation().getId().toString(), name, seat);
                            }
                            GUI.apiResponse(api.postCart(ticket.getId().toString()));

                        } else {
                            GUI.NotFound("Ticket");
                        }
                    } else {
                        GUI.invalidCommandParameter(key);
                    }
                }

                return Command.Result.OK;
            }));

            commands.put("/show", new Command(2,
                    Command.GenerateAccessibilityFor.Everyone(),
                    "/show", "Show current event.", (args) -> {
                GUI.eventCompactShow(event);
                return Command.Result.OK;
            }));

            commands.put("/edit", new Command(2,
                    Command.GenerateAccessibilityFor.Admin(),
                    "/edit", "Edit event. (-name, -description, -category)", (args) -> {
                EventDTO new_event = new EventDTO(event.getEvent());
                for (String key : args.keySet()) {
                    switch(key) {
                        case "-name" -> new_event.setName(args.get(key).replace('_', ' '));
                        case "-description" -> new_event.setDescription(args.get(key).replace('_', ' '));
                        case "-category" -> new_event.setCategoryId(Integer.valueOf(args.get(key)));
                        default -> GUI.invalidCommandParameter(key);
                    }
                }

                API.Result result = api.putEvent(new_event);
                GUI.apiResponse(result);
                this.event.setEvent(new_event);
                return Command.Result.OK;
            }));

            commands.put("/delete", new Command(2,
                    Command.GenerateAccessibilityFor.Admin(),
                    "/delete", "Delete event.", (args) -> {
                if(GUI.eventDelete(event.getEvent(), event.getLocation())) {
                    API.Result result = api.deleteEventLocation(event.getEventLocation().getId().toString());
                    GUI.apiResponse(result);
                    return Command.Result.SHOULD_EXIT;
                }
                return Command.Result.OK;
            }));

            commands.put("/help", new Command(1,
                    Command.GenerateAccessibilityFor.Everyone(),
                    "/help", "Show commands list.", (args) -> {
                GUI.help(commands);
                return Command.Result.OK;
            }));

            commands.put("/back", new Command(0,
                    Command.GenerateAccessibilityFor.Everyone(),
                    "/back", "Go back.", (args) -> Command.Result.SHOULD_EXIT
            ));

            commands = Filter.commandsByRole(commands, Context.getIdentity().getRole());
        }
    }

    @Override
    public void run() {
        if(event != null) {
            GUI.eventCompactShow(event);
            GUI.help(commands);
            listenCommand(commands);
        } else {
            GUI.NotFound("Event");
        }
    }
}
