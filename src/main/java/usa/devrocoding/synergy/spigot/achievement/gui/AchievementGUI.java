package usa.devrocoding.synergy.spigot.achievement.gui;

import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.achievement.object.Achievement;
import usa.devrocoding.synergy.spigot.gui.Gui;
import usa.devrocoding.synergy.spigot.gui.GuiElement;
import usa.devrocoding.synergy.spigot.gui.object.GuiSize;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.ItemBuilder;

import java.util.ArrayList;
import java.util.List;

public class AchievementGUI extends Gui {

    private SynergyUser user;

    public AchievementGUI(SynergyUser user, Core plugin){
        super(plugin, "Your Achievements", GuiSize.SIX_ROWS, false);

        this.user = user;
        setup();
    }

    @Override
    public void setup() {
        surroundWith(new GuiElement() {
            @Override
            public ItemStack getIcon(SynergyUser synergyUser) {
                return new ItemBuilder(Material.STAINED_GLASS_PANE)
                        .setDamage((short) 15)
                        .setName(" ")
                        .build();
            }
            @Override
            public void click(SynergyUser synergyUser, ClickType clickType) {}
        });

        List<Achievement> achievements = new ArrayList<Achievement>(){{
            addAll(Core.getPlugin().getAchievementManager().getAvailableAchievements());
        }};

        for(Achievement achievement : achievements){
            addElement(Core.getPlugin().getAchievementManager().getElement(this.user, achievement));
        }
    }
}
