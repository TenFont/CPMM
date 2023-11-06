package dev.tenfont.cpmm.lang;

import dev.tenfont.cpmm.lang.components.Function;

import java.util.HashMap;
import java.util.Map;

public class FunctionRegistry {
    private final Map<String, Function> map = new HashMap<>();

    public boolean declareFunction(String name, Function function) {
        if (map.containsKey(name))
            return false;
        map.put(name, function);
        return true;
    }

    public boolean functionExists(String name) {
        return map.containsKey(name);
    }
}
