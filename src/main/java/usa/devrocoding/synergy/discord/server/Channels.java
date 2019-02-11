package usa.devrocoding.synergy.discord.server;

import lombok.Getter;

public enum Channels {

    CONSOLE_CHAT("487921064203124736");

    @Getter
    private String id;

    Channels(String id){
        this.id = id;
    }

}
