package usa.devrocoding.synergy.proxy.suggest;

import usa.devrocoding.synergy.discord.Discord;
import usa.devrocoding.synergy.discord.suggest.SuggestManager.SuggestType;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.ProxyModule;
import usa.devrocoding.synergy.proxy.suggest.command.CommandSuggest;

public class SuggestManager extends ProxyModule {

  public SuggestManager(Core plugin){
    super(plugin, "Suggestions Manager", false);

    registerCommands(
        new CommandSuggest(this)
    );
  }

  @Override
  public void reload() {

  }

  @Override
  public void deinit() {

  }

  public void suggest(String userName, SuggestType suggestType, String suggestion) throws Exception{
    Discord.getSuggestManager().suggest(userName, suggestType, suggestion);
  }
}
