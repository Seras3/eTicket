package util;

import java.util.HashMap;

public interface CommandAction<T> {
    T call(HashMap<String, String> args);
}
