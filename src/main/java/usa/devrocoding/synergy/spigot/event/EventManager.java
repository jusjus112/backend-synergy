package usa.devrocoding.synergy.spigot.event;

import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.event.object.Event;

import java.util.ArrayList;
import java.util.List;

public class EventManager extends Module {

    private List<Event> activeEvents = new ArrayList<>();

    public EventManager(Core plugin){
        super(plugin, "Event Manager", false);
    }

    @Override
    public void reload(String response) {

    }


}
