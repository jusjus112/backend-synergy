package usa.devrocoding.synergy.discord.protection;

import net.dv8tion.jda.api.JDA;
import usa.devrocoding.synergy.discord.DiscordModule;
import usa.devrocoding.synergy.discord.protection.listener.GuildMessageReceiveListener;

public class ProtectionManager extends DiscordModule {

  public ProtectionManager(){
    super("Protection Manager");

    registerEventListeners(
        new GuildMessageReceiveListener()
    );
  }

  @Override
  public void init(JDA jda) {

  }
}
