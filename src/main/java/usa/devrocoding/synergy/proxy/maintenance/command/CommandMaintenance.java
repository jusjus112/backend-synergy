package usa.devrocoding.synergy.proxy.maintenance.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import usa.devrocoding.synergy.includes.Synergy;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.maintenance.MaintenanceManager;

public class CommandMaintenance extends Command {

    public CommandMaintenance(){
        super("maintenance", "synergy.maintenance.command", "main");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0){

            sender.sendMessage(new TextComponent(Synergy.SynergyColor.getLineWithName()));
            sender.sendMessage(new TextComponent(ChatColor.YELLOW+"/synergyreload maintenance"+ChatColor.GRAY+" - Reloads the 'maintenance.yml'"));
            sender.sendMessage(new TextComponent(ChatColor.YELLOW+"/maintenance <on/off>"+ChatColor.GRAY+" - Turns on/off the maintenance for the proxy"));
            sender.sendMessage(new TextComponent(ChatColor.YELLOW+"/maintenance <on/off> <server>"+ChatColor.GRAY+" - Turns on/off the maintenance for a server"));
        }else if (args.length < 3){
            MaintenanceManager maintenanceManager = Core.getCore().getMaintenanceManager();

            if (args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("off")) {
                String server = maintenanceManager.getProxyServer();
                if (args.length > 1) {
                    if (!Core.getCore().getProxy().getServers().containsKey(args[1])) {
                        sender.sendMessage(new TextComponent(Synergy.SynergyColor.ERROR.getColor() + args[1] + " is not a registered server!"));
                        return;
                    }else{
                        server = args[1];
                    }
                }

                switch (args[0]) {
                    case "on":
                        maintenanceManager.enableMaintenance(server);
                        sender.sendMessage(new TextComponent(
                                Synergy.SynergyColor.INFO + "Turned " + Synergy.SynergyColor.CHAT_HIGHLIGHT + args[0] + Synergy.SynergyColor.INFO + " maintenance for server " + server)
                        );
                        break;
                    case "off":
                        maintenanceManager.disableMaintenance(server);
                        sender.sendMessage(new TextComponent(
                                Synergy.SynergyColor.INFO + "Turned " + Synergy.SynergyColor.CHAT_HIGHLIGHT + args[0] + Synergy.SynergyColor.INFO + " maintenance for server " + server)
                        );
                        break;
                }
            }
        }
    }

}
