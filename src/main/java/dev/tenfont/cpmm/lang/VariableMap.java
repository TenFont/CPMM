package dev.tenfont.cpmm.lang;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
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

    public VariableInfo declareVariable(String identifier) {
        if (map.containsKey(identifier))
            return null;
        VariableInfo info = new VariableInfo(null, null);
        map.put(identifier, info);
        return info;
    }

    public void deleteVariable(String identifier) {
        map.remove(identifier);
    }

    public boolean variableExists(String identifier) {
        return (parentMap != null ? map.containsKey(identifier) || parentMap.variableExists(identifier) : map.containsKey(identifier));
    }

    public boolean variableExists(VariableInfo info) {
        return (parentMap != null ? map.containsValue(info) || parentMap.variableExists(info) : map.containsValue(info));
    }

    public void clear() {
        map.clear();
    }

    public @Nullable Object getVariable(String identifier) {
        return getVariable(identifier, false);
    }

    public @Nullable Object getVariable(String identifier, boolean previous) {
        VariableInfo info = getVariableInfo(identifier);
        return previous ? info.previous.value : info.value;
    }

    public VariableInfo getVariableInfo(String identifier)   {
        var value = map.get(identifier);
        return ((value == null && parentMap != null) ? parentMap.getVariableInfo(identifier) : value);
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