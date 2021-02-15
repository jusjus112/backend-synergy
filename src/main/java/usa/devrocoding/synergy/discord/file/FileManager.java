package usa.devrocoding.synergy.discord.file;

import com.google.gson.JsonObject;
import net.dv8tion.jda.api.JDA;
import usa.devrocoding.synergy.discord.DiscordModule;

public class FileManager extends DiscordModule {

    public FileManager(){
        super("File Manager");
    }

    @Override
    public void init(JDA jda) {

    }

    public void setup(){
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("hostname", "127.0.0.1");
        jsonObject.addProperty("port", 3306);
        jsonObject.addProperty("database", "synergy_discord");
        jsonObject.addProperty("username", "root");
        jsonObject.addProperty("password", "password");

        new JSONFile("settings").write(
                jsonObject
        ).finish();
    }

}
