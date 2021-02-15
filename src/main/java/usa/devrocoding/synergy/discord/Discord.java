package usa.devrocoding.synergy.discord;

import lombok.Getter;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.assets.object.LinuxColorCodes;
import usa.devrocoding.synergy.discord.assets.DiscordManager;
import usa.devrocoding.synergy.discord.command.CommandManager;
import usa.devrocoding.synergy.discord.file.FileManager;
import usa.devrocoding.synergy.discord.protection.ProtectionManager;
import usa.devrocoding.synergy.discord.server.MessageManager;
import usa.devrocoding.synergy.discord.suggest.SuggestManager;
import usa.devrocoding.synergy.discord.terminal.TerminalManager;

import java.util.Arrays;
import usa.devrocoding.synergy.discord.ticket.TicketsManager;

public class Discord {

    @Getter
    public static JDA jda;

    @Getter
    public static CommandManager commandManager;
    @Getter
    public static DiscordManager discordManager;
    @Getter
    private static MessageManager messageManager;
    @Getter
    private static SuggestManager suggestManager;
    @Getter
    private static TicketsManager ticketManager;
    @Getter
    private static ProtectionManager protectionManager;

    public static void main(String[] args) {
//        try{
//            Arrays.stream(Synergy.getLogos().logo_colossal).forEach(s -> System.out.println(LinuxColorCodes.ANSI_YELLOW +s));
//            System.out.println(" "+LinuxColorCodes.ANSI_RESET);
//            Synergy.discord("Loading JDA Api....");
//
//            jda = new JDABuilder(AccountType.BOT).setToken("NTQzMDYwMjM1OTU5NjY0Njgx.Dz3ELQ.dV8_8Datmbn0X-OzAqCFVoNpB1c").build();
//
//            Synergy.discord("Discord Bot Token installed. Loading settings..");
//            new FileManager().setup();
//
//            // Bot properties
//            getJda().setAutoReconnect(true);
//
//            Synergy.discord("Loading Modules");
//            commandManager = new CommandManager();
//            discordManager = new DiscordManager();
//            messageManager = new MessageManager();
//
////            getJda().addEventListener(
////                    discordManager.getListeners()
////            );
//            Synergy.discord("Synergy loaded..");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }

    public static void initTerminal(){
        Synergy.discord("Loading JDA Api....");

        try{
            JDABuilder builder = JDABuilder.createDefault("NTQ0NjAxNTA2ODcyMDMzMjkx.D0NfOA.J4fn34LTsrHe9Ms7OI2pX4oj5bw");
            Synergy.discord("Loading the Discord Bot....");
            // Bot properties
            builder.setAutoReconnect(true);
            builder.setActivity(Activity.playing("play.mirageprisons.net"));
            builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
            builder.enableIntents(GatewayIntent.GUILD_MESSAGE_REACTIONS);
            builder.enableIntents(GatewayIntent.GUILD_EMOJIS);
            builder.enableCache(CacheFlag.MEMBER_OVERRIDES);
            builder.setChunkingFilter(ChunkingFilter.ALL);
            builder.setMemberCachePolicy(MemberCachePolicy.ALL);

            Synergy.discord("Loading Bot Modules...");
            commandManager = new CommandManager();
            discordManager = new DiscordManager();
            messageManager = new MessageManager();
            suggestManager = new SuggestManager();
            ticketManager = new TicketsManager();
            protectionManager = new ProtectionManager();

            new TerminalManager();
            Synergy.discord("All Bot Modules loaded!");

            DiscordModule.getModules().forEach(discordModule -> {
                if (discordModule.getListeners() != null) {
                    builder.addEventListeners(discordModule.getListeners());
                }
            });

            jda = builder.build().awaitReady();

            DiscordModule.getModules().forEach(discordModule -> {
                discordModule.init(jda);
            });

            Synergy.discord("Discord Bot loaded!");
        }catch (Exception e){
            Synergy.error("Discord failed to load!");
            e.printStackTrace();
        }
    }

}
