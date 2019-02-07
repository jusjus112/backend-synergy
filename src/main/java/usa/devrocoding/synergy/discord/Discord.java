package usa.devrocoding.synergy.discord;

import lombok.Getter;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import usa.devrocoding.synergy.discord.command.CommandManager;

public class Discord {

    @Getter
    public static JDA jda;

    @Getter
    public static CommandManager commandManager;

    public static void main(String[] args) {
        try{
            jda = new JDABuilder(AccountType.BOT).setToken("NTQzMDYwMjM1OTU5NjY0Njgx.Dz3ELQ.dV8_8Datmbn0X-OzAqCFVoNpB1c").build();
            // Bot properties
            getJda().setAutoReconnect(true);

            commandManager = new CommandManager();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
