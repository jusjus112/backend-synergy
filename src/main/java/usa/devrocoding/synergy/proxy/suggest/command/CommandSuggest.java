package usa.devrocoding.synergy.proxy.suggest.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import usa.devrocoding.synergy.discord.suggest.SuggestManager.SuggestType;
import usa.devrocoding.synergy.proxy.suggest.SuggestManager;

public class CommandSuggest extends Command {

  private final SuggestManager suggestManager;

  public CommandSuggest(SuggestManager suggestManager){
    super("suggestions", "synergy.suggestions.command", "suggest");

    this.suggestManager = suggestManager;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (sender instanceof ProxiedPlayer){
      if (args.length == 0){
        sender.sendMessage(new TextComponent("Usage /suggest <suggestion>"));
      }else{
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < args.length; i++) {
          if (i>0){
            builder.append(" ");
          }
          builder.append(args[i]);
        }
        try{
          this.suggestManager.suggest(((ProxiedPlayer) sender).getDisplayName(), SuggestType.SERVER, builder.toString());
          sender.sendMessage(new TextComponent(ChatColor.GREEN + "Thank you! Your suggestion has been sent!"));
        }catch(Exception e){
          sender.sendMessage(new TextComponent(ChatColor.RED + "Something went wrong sending your suggestion!"));
          sender.sendMessage(new TextComponent(ChatColor.DARK_RED + "ERROR: " + ChatColor.RED + e.getMessage()));
          e.printStackTrace();
        }
      }
    }
  }

}
