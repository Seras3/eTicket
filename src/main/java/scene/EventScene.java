package scene;

import graphic.GUI;
import service.API;
import context.Context;
import util.Command;
import util.Filter;

import java.util.HashMap;
import java.util.Map;

public class EventScene extends Scene {
    private Map<String, Command> commands;
    private API api;

    public EventScene() {
        super("Events menu");

        this.api = new API(Context.getIdentity());

        this.commands = new HashMap<String, Command>();

        commands.put("/list", new Command(2,
                Command.GenerateAccessibilityFor.Everyone(),
                "/list", "Display events list.", (args) -> {
            GUI.eventRowList(api.getEventRows());
            return Command.Result.OK;
        }));

        commands.put("/list_events", new Command(2,
                Command.GenerateAccessibilityFor.Admin(),
                "/list_events", "Display all events from database.", (args) -> {
            GUI.eventList(api.getAllEvents());
            return Command.Result.OK;
        }));

        commands.put("/add_event_location", new Command(2,
                Command.GenerateAccessibilityFor.Admin(),
                "/add_event_location", "Add an event in another place.", (args) -> {
            API.Result result = api.postEventLocation(GUI.getEventLocation());
            GUI.apiResponse(result);
            return Command.Result.OK;
        }));

        commands.put("/add_event", new Command(2,
                Command.GenerateAccessibilityFor.Admin(),
                "/add_event", "Add event.", (args) -> {
            API.Result result = api.postEvent(GUI.getEvent());
            GUI.apiResponse(result);
            return Command.Result.OK;
        }));


        commands.put("/view", new Command(2,
                Command.GenerateAccessibilityFor.Everyone(),
                "/view", "View event with -id x.", (args) -> {
            for (String key : args.keySet()) {
                switch(key) {
                    case "-id" -> new EventViewScene(args.get(key)).run();
                    default -> GUI.invalidCommandParameter(key);
                }
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
        GUI.help(commands);
        listenCommand(commands);
    }
}
