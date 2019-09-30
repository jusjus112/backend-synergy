package usa.devrocoding.synergy.spigot.assets.wand.event;

import lombok.Getter;
import usa.devrocoding.synergy.spigot.assets.wand.object.SelectorWand;
import usa.devrocoding.synergy.spigot.listeners.SynergyEvent;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class SelectorWandCompletionEvent extends SynergyEvent {

    @Getter
    private SelectorWand selectorWand;
    @Getter
    private SynergyUser synergyUser;

    public SelectorWandCompletionEvent(SelectorWand selectorWand, SynergyUser synergyUser){
        this.selectorWand = selectorWand;
        this.synergyUser = synergyUser;
    }

}
