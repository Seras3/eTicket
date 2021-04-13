package util;

import context.Identity;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Filter {
    public static Map<String, Command> commandsByRole(Map<String, Command> commands, Identity.Role role) {
        return new HashMap<String, Command>(commands.entrySet().stream()
                .filter(item -> item.getValue().getAccessList().contains(role))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }
}
