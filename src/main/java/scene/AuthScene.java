package scene;

import graphic.GUI;
import service.Context;
import service.Identity;
import util.Command;

import java.util.HashMap;

public class AuthScene extends Scene {
    private HashMap<String, Command> commands;

    public AuthScene() {
        super("Authentication");

        this.commands = new HashMap<String, Command>();

        commands.put("/login", new Command(3, 
                Command.GenerateAccessibilityFor.Everyone(),
                "/login", "Login yourself.", () -> {
            new LoginScene().run();
            return Command.Result.OK;
        }));

        commands.put("/register", new Command(2,
                Command.GenerateAccessibilityFor.Everyone(),
                "/register", "Register if you don't have an account.", () -> {
            new RegisterScene().run();
            return Command.Result.OK;
        }));

        commands.put("/help", new Command(1,
                Command.GenerateAccessibilityFor.Everyone(),
                "/help", "Show commands list.", () -> {
            GUI.help(commands);
            return Command.Result.OK;
        }));

        commands.put("/exit", new Command(0,
                Command.GenerateAccessibilityFor.Everyone(),
                "/exit", "Exit.", () -> Command.Result.SHOULD_EXIT
        ));
    }

    public void run() {
        GUI.authentication();
        GUI.help(commands);
        this.listenCommand(commands);
    }
}
