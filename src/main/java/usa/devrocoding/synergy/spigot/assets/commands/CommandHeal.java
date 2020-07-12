package usa.devrocoding.synergy.spigot.assets.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.hologram.Hologram;
import usa.devrocoding.synergy.spigot.hologram.HologramProt;
import usa.devrocoding.synergy.spigot.hologram.object.HologramLine;
import usa.devrocoding.synergy.spigot.user.object.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

import java.util.ArrayList;

public class CommandHeal extends SynergyCommand {

    public CommandHeal(Core plugin) {
        super(plugin, "command.heal", "Synergy's Heal Command", false,"heal");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
//        getPlugin().getHologramManager().createHologram(
//                player.getLocation(),
//                player1 ->
//                    player1.getInventory().getItemInMainHand().getType() == Material.BARRIER
//                ,
//                new HologramLine() {
//                    @Override
//                    public String getMessage(SynergyUser synergyUser) {
//                        return ChatColor.GREEN +""+ ChatColor.BOLD + "HELLO "+ChatColor.YELLOW+""+ChatColor.BOLD+synergyUser.getPlayer().getName();
//                    }
//                },
//                new HologramLine() {
//                    @Override
//                    public String getMessage(SynergyUser synergyUser) {
//                        return "§cYour Coins: §e§l"+synergyUser.getNetworkXP();
//                    }
//                }
//        );
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        for (PotionEffect potionEffect : player.getActivePotionEffects()){
            player.removePotionEffect(potionEffect.getType());
        }
        synergyUser.info("There ya go...");
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {

    }
}
