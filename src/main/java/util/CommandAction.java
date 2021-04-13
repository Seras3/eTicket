package util;

import java.util.Map;

public interface CommandAction<T> {
    T call(Map<String, String> args);
}
