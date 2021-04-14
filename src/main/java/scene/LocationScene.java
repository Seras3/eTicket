package scene;

import context.Context;
import graphic.GUI;
import service.API;
import util.Command;
import util.Filter;

import java.util.HashMap;
import java.util.Map;

public class LocationScene extends Scene{
    private Map<String, Command> commands;
    private API api;

    public LocationScene() {
        super("Locations menu");

        this.api = new API(Context.getIdentity());

        this.commands = new HashMap<String, Command>();

        commands.put("/list", new Command(2,
                Command.GenerateAccessibilityFor.Everyone(),
                "/list", "Display locations list.", (args) -> {
            GUI.locationList(api.getAllLocations());
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
