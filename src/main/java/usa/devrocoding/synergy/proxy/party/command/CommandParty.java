package usa.devrocoding.synergy.proxy.party.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.party.object.Party;

public class CommandParty extends Command {

    public CommandParty(){
        super("party", "synergy.party.command", "p");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer){
            if (args.length == 0){
                sender.sendMessage(new TextComponent(Synergy.SynergyColor.getLineWithName()));
                sender.sendMessage(new TextComponent(ChatColor.YELLOW+"/p invite <player>"+ChatColor.GRAY+" - Reloads the 'maintenance.yml'"));
                sender.sendMessage(new TextComponent(ChatColor.YELLOW+"/p leave"+ChatColor.GRAY+" - Turns on/off the maintenance for the proxy"));
                sender.sendMessage(new TextComponent(ChatColor.YELLOW+"/p list"+ChatColor.GRAY+" - Turns on/off the maintenance for the proxy"));
                sender.sendMessage(new TextComponent(ChatColor.YELLOW+"/p accept <player>"+ChatColor.GRAY+" - Turns on/off the maintenance for a server"));
            }else if (args.length < 3){
                if (args[0].equalsIgnoreCase("invite")){
                    try{
                        ProxiedPlayer target = Core.getCore().getProxy().getPlayer(args[1]);
                        if (target != null){
                            TextComponent accept = new TextComponent("[ACCEPT] ");
                            accept.setColor(ChatColor.GREEN);
                            accept.setBold(true);
                            accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/asd"));

                            TextComponent deny = new TextComponent(" [DENY]");
                            deny.setColor(ChatColor.RED);
                            deny.setBold(true);
                            deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/asd"));

                            accept.addExtra(deny);

                            target.sendMessage(new TextComponent(Synergy.SynergyColor.getLineWithName(sender.getName())));
                            target.sendMessage(new TextComponent("You have been invited for a party!"));
                            target.sendMessage(new TextComponent(" "));
                            target.sendMessage(accept);
                            target.sendMessage(new TextComponent(ChatColor.YELLOW+"Click or type /p accept "+sender.getName()));
                            target.sendMessage(new TextComponent(Synergy.SynergyColor.getLine()));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else if(args[0].equalsIgnoreCase("accept")){
                    try {
                        ProxiedPlayer target = Core.getCore().getProxy().getPlayer(args[1]);
                        if (target != null) {
                            try{
                                Party party = Core.getCore().getPartyManager().getParty(target);

                            }catch (NullPointerException e){
                                sender.sendMessage(new TextComponent("This user has never invited you!"));
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
