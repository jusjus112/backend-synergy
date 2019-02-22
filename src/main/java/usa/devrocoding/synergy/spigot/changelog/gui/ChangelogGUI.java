package usa.devrocoding.synergy.spigot.changelog.gui;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.changelog.object.Changelog;
import usa.devrocoding.synergy.spigot.changelog.object.ChangelogType;
import usa.devrocoding.synergy.spigot.gui.Gui;
import usa.devrocoding.synergy.spigot.gui.GuiElement;
import usa.devrocoding.synergy.spigot.gui.object.GuiSize;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.ItemBuilder;

import java.util.ArrayList;
import java.util.List;

public class ChangelogGUI extends Gui {

    @Getter
    private ChangelogType type;

    public ChangelogGUI(Core plugin, ChangelogType changelogType){
        super(plugin, "Changelog GUI", GuiSize.THREE_ROWS);
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

        for(Changelog changelog : getPlugin().getChangelogManager().getChangelogs()){
            int place = getCenterInput().get(getPlugin().getChangelogManager().getChangelogs().indexOf(changelog));
            addElement(place, new GuiElement() {
                @Override
                public ItemStack getIcon(SynergyUser synergyUser) {
                    List<String> lore = new ArrayList<>();
                    lore.add(" ");

                    for(String line : changelog.getLines()){
                        lore.add(C.colorize(line));
                    }

                    return new ItemBuilder(Material.EMPTY_MAP)
                            .setName(C.colorize(changelog.getTitle()))
                            .setLore(lore)
                            .build();
                }

                @Override
                public void click(SynergyUser synergyUser, ClickType clickType) {

                }
            });
        }
    }
}
