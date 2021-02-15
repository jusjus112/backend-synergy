package usa.devrocoding.synergy.spigot.objectives.gui;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.achievement.object.Achievement;
import usa.devrocoding.synergy.spigot.gui.Gui;
import usa.devrocoding.synergy.spigot.gui.GuiElement;
import usa.devrocoding.synergy.spigot.gui.object.GuiSize;
import usa.devrocoding.synergy.spigot.objectives.object.Objective;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.ItemBuilder;

public class ObjectivesGUI extends Gui {

    private final SynergyUser user;

    public ObjectivesGUI(SynergyUser user, Core plugin){
        super(plugin, "Available Objectives", GuiSize.SIX_ROWS, false);

        this.user = user;
        setup();
    }

    @Override
    public void setup() {
        surroundWith(new GuiElement() {
            @Override
            public ItemStack getIcon(SynergyUser synergyUser) {
                return new ItemBuilder(Material.STAINED_GLASS_PANE)
                        .setName(" ")
                        .build();
            }
            @Override
            public void click(SynergyUser synergyUser, ClickType clickType, Gui gui) {}
        });

        List<Objective> objectives = new ArrayList<Objective>(){{
            addAll(Core.getPlugin().getObjectiveManager().getAvailableObjectives());
        }};

        for(Objective objective : objectives){
            addElement(Core.getPlugin().getObjectiveManager().getElement(this.user, objective));
        }
    }
}
