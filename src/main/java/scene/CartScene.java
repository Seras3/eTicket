package scene;

import graphic.GUI;
import service.API;
import context.Context;
import util.Command;
import util.Filter;

import java.util.HashMap;
import java.util.Map;

public class CartScene extends Scene {
    private API api;
    private Map<String, Command> commands;

    public CartScene() {
        super("Cart menu");

        api = new API(Context.getIdentity());
        commands = new HashMap<String, Command>();

        commands.put("/list", new Command(1,
                Command.GenerateAccessibilityFor.User(),
                "/list", "See your shopping cart.", (args) -> {
            GUI.showCart(api.getCart());
            return Command.Result.OK;
        }));

        commands.put("/place_order", new Command(1,
                Command.GenerateAccessibilityFor.User(),
                "/place_order", "Purchase your tickets.", (args) -> {
            GUI.apiResponse(api.placeOrder());
            return Command.Result.OK;
        }));

        commands.put("/remove", new Command(1,
                Command.GenerateAccessibilityFor.User(),
                "/remove", "Remove ticket from cart.", (args) -> {
            for (String key : args.keySet()) {
                switch(key) {
                    case "-id" -> GUI.apiResponse(api.deleteTicketFromCart(args.get(key)));
                    default -> GUI.invalidCommandParameter(key);
                }
            }
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
