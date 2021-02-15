package usa.devrocoding.synergy.spigot.assets.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class CommandHeal extends SynergyCommand {

    public CommandHeal(Core plugin) {
        super(plugin, "command.heal", "Synergy's Heal Command", false,"heal");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(potionEffect.getType());
        }

        synergyUser.info("You have been healed.");
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {

    }
}
