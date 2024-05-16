package me.fodded.gamemanager.map.template;

import lombok.Getter;
import me.fodded.gamemanager.map.IGameTemplate;

import java.util.HashMap;
import java.util.Map;

@Getter
public class GameTemplateRegistry {

    private final Map<String, IGameTemplate> templates = new HashMap<>();

    public void registerTemplate(IGameTemplate template) {
        templates.put(template.getName(), template);
    }

    @SuppressWarnings("unchecked")
    public IGameTemplate getTemplate(String name) {
        return templates.get(name);
    }
}
