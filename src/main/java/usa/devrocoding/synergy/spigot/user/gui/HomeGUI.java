package usa.devrocoding.synergy.spigot.user.gui;

import lombok.Getter;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.gui.Gui;
import usa.devrocoding.synergy.spigot.gui.object.GuiSize;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class HomeGUI extends Gui {

    @Getter
    private SynergyUser user;

    public HomeGUI(Core plugin, SynergyUser user){
        super(plugin, user.getName()+"'s homes", GuiSize.FIVE_ROWS);
        this.user = user;
    }

    @Override
    public void setup() {

    }
}
