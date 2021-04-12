package scene;

import graphic.GUI;
import service.API;
import service.Context;
import util.Command;
import util.Filter;

import java.util.HashMap;

public class CartScene extends Scene {
    private API api;
    private HashMap<String, Command> commands;

    public CartScene() {
        super("Cart menu");

        api = new API(Context.getIdentity());
        commands = new HashMap<String, Command>();

        commands.put("/show", new Command(1,
                Command.GenerateAccessibilityFor.User(),
                "/show", "See your shopping cart.", (args) -> {
            //GUI.showCart(api.getCart());
            return Command.Result.OK;
        }));

        commands.put("/buy", new Command(1,
                Command.GenerateAccessibilityFor.User(),
                "/buy", "Purchase your tickets.", (args) -> {
            //api.placeOrder();
            return Command.Result.OK;
        }));

        commands.put("/remove", new Command(1,
                Command.GenerateAccessibilityFor.User(),
                "/remove", "Remove ticket from cart.", (args) -> {
            //api.deleteTicketFromCart();
            return Command.Result.OK;
        }));

        commands.put("/help", new Command(1,
                Command.GenerateAccessibilityFor.User(),
                "/help", "Show commands list.", (args) -> {
            GUI.help(commands);
            return Command.Result.OK;
        }));

        commands.put("/back", new Command(0,
                Command.GenerateAccessibilityFor.User(),
                "/back", "Go back.",
                (args) -> Command.Result.SHOULD_EXIT
        ));

        commands = Filter.commandsByRole(commands, Context.getIdentity().getRole());
    }


    @Override
    public void run() {
        GUI.help(commands);
        listenCommand(commands);
    }
}
