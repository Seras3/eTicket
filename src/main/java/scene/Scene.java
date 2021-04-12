package scene;

import graphic.GUI;
import util.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class Scene {
    private boolean listening;
    protected String title;

    public Scene(String title) {
        this.title = title;
    }

    abstract public void run();


    protected void listenCommand(HashMap<String, Command> commands) {
        this.listening = true;
        while(listening) {
            GUI.sceneTitle(title);
            String input_command = GUI.getCommand();
            String[] args = input_command.split(" ");
            String command_name = args[0];
            args = Arrays.copyOfRange(args, 1, args.length);
            Command.Result command_result;

            if (commands.containsKey(command_name)) {
                try {
                    command_result = commands.get(command_name).call(args);
                    if(command_result == Command.Result.SHOULD_EXIT) { listening = false; }

                } catch (Exception e) { GUI.exception(e); }

            } else { GUI.invalidCommand(); }
        }
    }
}
