package util;

import service.Identity;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Filter {
    public static HashMap<String, Command> commandsByRole(HashMap<String, Command> commands, Identity.Role role) {
        return new HashMap<String, Command>(commands.entrySet().stream()
                .filter(item -> item.getValue().getAccessList().contains(role))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }
}
