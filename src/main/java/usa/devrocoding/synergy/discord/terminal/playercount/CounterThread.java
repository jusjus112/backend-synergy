package usa.devrocoding.synergy.discord.terminal.playercount;

import net.dv8tion.jda.core.entities.VoiceChannel;
import net.md_5.bungee.api.config.ServerInfo;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.discord.Discord;
import usa.devrocoding.synergy.proxy.Core;

import java.util.Map;

public class CounterThread implements Runnable{

    @Override
    public void run() {
        Map<String, String> channels = Core.getCore().getAssetManager().getDiscordChannels();

        for(String server : Core.getCore().getProxy().getServers().keySet()){
            try{
                ServerInfo serverInfo = Core.getCore().getProxy().getServers().get(server);
                String channel_id = channels.get(server);
                VoiceChannel voiceChannel = Discord.getJda().getGuilds().get(0)
                        .getVoiceChannelById(channel_id);

                if (voiceChannel != null){
                    voiceChannel.getManager().setName(
                            (server.substring(0, 1).toUpperCase() + server.substring(1))+
                                    " Players: "+
                                    serverInfo.getPlayers().size()
                    ).queue();
                }
            }catch (IllegalArgumentException e){}
        }
    }
}
