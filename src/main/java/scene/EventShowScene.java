package scene;

import dto.EventDTO;
import graphic.GUI;
import service.API;
import service.Context;
import util.Command;
import util.Filter;

import java.util.HashMap;

public class EventShowScene extends Scene {
    private HashMap<String, Command> commands;
    private API api;
    private EventDTO event;

    public EventShowScene(Integer id) {
        super("Event " + id);

        this.api = new API(Context.getIdentity());
        event = api.getEvent(id);


        this.commands = new HashMap<String, Command>();

        commands.put("/show", new Command(2,
                Command.GenerateAccessibilityFor.Everyone(),
                "/show", "Show current event.", (args) -> {
            GUI.eventShow(event);
            return Command.Result.OK;
        }));

        commands.put("/edit", new Command(2,
                Command.GenerateAccessibilityFor.Admin(),
                "/edit", "Edit event. (-name, -description, -category)", (args) -> {
            EventDTO new_event = new EventDTO(event);
            for (String key : args.keySet()) {
                switch(key) {
                    case "-name" -> new_event.setName(args.get(key).replace('_', ' '));
                    case "-description" -> new_event.setDescription(args.get(key).replace('_', ' '));
                    case "-category" -> new_event.setCategory_id(Integer.valueOf(args.get(key)));
                    default -> GUI.invalidCommandParameter(key);
                }
            }
            API.Result result = api.putEvent(new_event);
            GUI.apiResponse(result);
            this.event = new_event;
            return Command.Result.OK;
        }));

        commands.put("/delete", new Command(2,
                Command.GenerateAccessibilityFor.Admin(),
                "/delete", "Delete event.", (args) -> {
            if(GUI.eventDelete(event)) {
                API.Result result = api.deleteEvent(event.getId());
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

    @Override
    public void run() {
        if(event != null) {
            GUI.eventShow(event);
            GUI.help(commands);
            listenCommand(commands);
        } else {
            GUI.eventNotFound();
        }
    }
}
