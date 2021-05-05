package usa.devrocoding.synergy.discord;

import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class DiscordBot {

  public static void main(String[] args) {
    try{
      // Initialize the builder
      JDABuilder builder = JDABuilder.createDefault(YOUR_TOKEN_HERE);
      // Replace YOUR_TOKEN_HERE with your discord token.

      // Enable GatewayIntents for which ones you need
      builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
      builder.enableIntents(GatewayIntent.GUILD_MESSAGE_REACTIONS);
      builder.enableIntents(GatewayIntent.GUILD_EMOJIS);
      // Enabling caching if wanted
      builder.enableCache(CacheFlag.MEMBER_OVERRIDES);
      // Filter function for member chunking of guilds. ALL or NONE
      builder.setChunkingFilter(ChunkingFilter.ALL);

      // Enable auto reconnect if you want to let it auto reconnect when idle
      builder.setAutoReconnect(true);
      // Set your own activity, like help messages.
      builder.setActivity(Activity.watching("!help for help"));

      // Build the JDA Object
      JDA jda = builder.build().awaitReady();
    }catch (LoginException | InterruptedException e){
      e.printStackTrace();
    }
  }

}