package scene;

import graphic.GUI;
import util.*;

import java.util.HashMap;

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
            Command.Result command_result;

            if (commands.containsKey(input_command)) {
                try {
                    command_result = commands.get(input_command).call();
                    if(command_result == Command.Result.SHOULD_EXIT) { listening = false; }

                } catch (Exception e) { GUI.exception(e); }

            } else { GUI.invalidCommand(); }
        }
    }
}
