package usa.devrocoding.synergy.proxy.assets.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.ProxyModule;

public class CommandSynergyProxyReload extends Command {

    public CommandSynergyProxyReload(){
        super("synergyproxyreload", "synergy.onReload.modules", "synrl", "synreload", "synergyreload");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)){
            if (args.length > 0 && args.length < 2){
                for(ProxyModule proxyModule : Core.getCore().getProxyModules()){
                    if (proxyModule.getShortName().toLowerCase().equalsIgnoreCase(args[0].toLowerCase())) {
                        if (proxyModule.isReloadable()) {
                            proxyModule.reload();
                            sender.sendMessage(new TextComponent(ChatColor.YELLOW+"Module '"+proxyModule.getName()+"' reloaded.."));
                            return;
                        } else {
                            sender.sendMessage(new TextComponent(ChatColor.RED + "The module '" + proxyModule.getName() + "' cannot be reloaded!"));
                            return;
                        }
                    }
                }
                sender.sendMessage(new TextComponent(ChatColor.RED+"Cannot onReload the module "+args[0]));
            }else{
                sender.sendMessage(new TextComponent(ChatColor.RED+"Usage: /synergyproxyreload <module>"));
            }
        }
    }
}
