package usa.devrocoding.synergy.discord;

import lombok.Getter;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.assets.object.LinuxColorCodes;
import usa.devrocoding.synergy.discord.assets.DiscordManager;
import usa.devrocoding.synergy.discord.command.CommandManager;
import usa.devrocoding.synergy.discord.file.FileManager;
import usa.devrocoding.synergy.discord.terminal.playercount.PlayercountManager;
import usa.devrocoding.synergy.spigot.assets.C;

import java.util.Arrays;

public class Discord {

    @Getter
    public static JDA jda;

    @Getter
    public static CommandManager commandManager;
    @Getter
    public static DiscordManager discordManager;

    public static void main(String[] args) {
        try{
            Arrays.stream(Synergy.getLogos().logo_colossal).forEach(s -> System.out.println(LinuxColorCodes.ANSI_YELLOW +s));
            System.out.println(" "+LinuxColorCodes.ANSI_RESET);
            Synergy.discord("Loading JDA Api....");

            jda = new JDABuilder(AccountType.BOT).setToken("NTQzMDYwMjM1OTU5NjY0Njgx.Dz3ELQ.dV8_8Datmbn0X-OzAqCFVoNpB1c").build();

            Synergy.discord("Discord Bot Token installed. Loading settings..");
            new FileManager().setup();

            // Bot properties
            getJda().setAutoReconnect(true);

            Synergy.discord("Loading Modules");
            commandManager = new CommandManager();
            discordManager = new DiscordManager();

//            getJda().addEventListener(
//                    discordManager.getListeners()
//            );
            Synergy.discord("Synergy loaded..");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void initTerminal(){
        Synergy.discord("Loading JDA Api....");

        try{
            jda = new JDABuilder(AccountType.BOT).setToken("NTQ0NjAxNTA2ODcyMDMzMjkx.D0NfOA.J4fn34LTsrHe9Ms7OI2pX4oj5bw").build();
            Synergy.discord("Bot Token installed.");
            // Bot properties
            getJda().setAutoReconnect(true);
            getJda().getPresence().setGame(Game.playing("arcadewars.net"));

            Synergy.discord("Loading Modules");
            commandManager = new CommandManager();
            discordManager = new DiscordManager();

            new PlayercountManager();
            Synergy.discord("Bot loaded..");
        }catch (Exception e){
            Synergy.error("Discord failed to load!");
        }
    }

}
