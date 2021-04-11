package util;

import service.Identity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class Command implements Comparable<Command> {
    private int display_priority;
    private List<Identity.Role> access_list;
    private String name;
    private String description;
    private Callable<Command.Result> commandFunction;

    @Override
    public int compareTo(Command obj) {
        return obj.getDisplayPriority() - this.display_priority;
    }

    public enum Result {
        OK,
        SHOULD_EXIT
    }

    public Command(int display_priority, List<Identity.Role> access_list, String name, String description, Callable<Command.Result> commandFunction) {
        this.display_priority = display_priority;
        this.access_list = access_list;
        this.name = name;
        this.description = description;
        this.commandFunction = commandFunction;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getDisplayPriority() { return display_priority; }
    public List<Identity.Role> getAccessList() { return access_list; }

    public Command.Result call() throws Exception{
        return commandFunction.call();
    }

    public static class GenerateAccessibilityFor {
        public static List<Identity.Role> Admin() {
            return new ArrayList<Identity.Role>() {{ add(Identity.Role.ADMIN); }};
        }

        public static List<Identity.Role> User() {
            return new ArrayList<Identity.Role>() {{ add(Identity.Role.USER); }};
        }

        public static List<Identity.Role> Everyone() {
            return new ArrayList<Identity.Role>() {{ add(Identity.Role.ADMIN); add(Identity.Role.USER); }};
        }
    }
}