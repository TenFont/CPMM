package dev.tenfont.cpmm.lang.variables;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import dev.tenfont.cpmm.lang.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public class VariableMap {

    @Nullable
    private final VariableMap parentMap;
    private final Map<String, VariableInfo> map = new HashMap<>();

    public VariableMap() {
        this(null);
    }

    public VariableInfo declareVariable(Identifier identifier) {
        if (map.containsKey(identifier.get()))
            return null;
        VariableInfo info = new VariableInfo(null, null);
        map.put(identifier.get(), info);
        return info;
    }

    public void deleteVariable(String identifier) {
        map.remove(identifier);
    }

    public boolean variableExists(String identifier) {
        return (parentMap != null ? map.containsKey(identifier) && parentMap.variableExists(identifier) : map.containsKey(identifier));
    }

    public boolean variableExists(VariableInfo info) {
        return (parentMap != null ? map.containsValue(info) && parentMap.variableExists(info) : map.containsValue(info));
    }

    public void clear() {
        map.clear();
    }

    public @Nullable <T> T getVariable(String identifier, Class<? extends T>[] returnTypes) {
        return getVariable(identifier, returnTypes, false);
    }

    public @Nullable <T> T getVariable(String identifier, Class<? extends T>[] returnTypes, boolean previous) {
        VariableInfo info = getVariableInfo(identifier);
        Object value = previous ? info.previous.value : info.value;
        for (Class<? extends T> type : returnTypes) {
            if (type.isInstance(value))
                return type.cast(value);
        }
        return null;
    }

    public VariableInfo getVariableInfo(String identifier)   {
        return map.get(identifier);
    }

    public void setVariable(String identifier, Object value) {
        map.compute(identifier, (s, info) -> new VariableInfo(info, value));
    }

    private Map<String, VariableInfo> collectMaps() {
        Map<String, VariableInfo> map = new HashMap<>(this.map);
        if (parentMap != null)
            map.putAll(parentMap.collectMaps());
        return map;
    }

    @Override
    public String toString() {
        return collectMaps().toString();
    }

    public record VariableInfo(VariableInfo previous, Object value) {}
}