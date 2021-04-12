package scene;

import graphic.GUI;
import service.Context;
import util.Command;
import util.Filter;


import java.util.HashMap;


public class MenuScene extends Scene{
    private HashMap<String, Command> commands;

    public MenuScene() {
        super("Menu");

        this.commands = new HashMap<String, Command>();

        commands.put("/events", new Command(2, 
                Command.GenerateAccessibilityFor.Everyone(),
                "/events", "Search for events.", (args) -> {
            new EventScene().run();
            return Command.Result.OK;
        }));

        commands.put("/cart", new Command(2,
                Command.GenerateAccessibilityFor.User(),
                "/cart", "Check your shopping cart.", (args) -> {
            new CartScene().run();
            return Command.Result.OK;
        }));

        commands.put("/account", new Command(2,
                Command.GenerateAccessibilityFor.Everyone(),
                "/account", "Go to account page.", (args) -> {
            new AccountScene().run();
            return Command.Result.OK;
        }));

        commands.put("/help", new Command(1,
                Command.GenerateAccessibilityFor.Everyone(),
                "/help", "Show commands list.", (args) -> {
            GUI.help(commands);
            return Command.Result.OK;
        }));

        commands.put("/logout", new Command(0,
                Command.GenerateAccessibilityFor.Everyone(),
                "/logout", "Log out.", (args) -> Command.Result.SHOULD_EXIT
        ));

        commands = Filter.commandsByRole(commands, Context.getIdentity().getRole());
    }

    @Override
    public void run() {
        GUI.help(commands);
        listenCommand(commands);
    }
}
