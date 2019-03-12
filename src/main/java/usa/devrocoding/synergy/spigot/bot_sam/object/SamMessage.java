package usa.devrocoding.synergy.spigot.bot_sam.object;

import lombok.Getter;

import java.util.Random;

public enum SamMessage {

    CANNOT_DO_THAT(
            "There are reasons why I am not doing that...",
            "Can't do that. Always look twice before executing this command.",
            "Cannot do that "
    ),
    NO_PERMISSIONS(
            "Sorry, but I see that you don't have permissions..",
            "I'm not letting you use this...",
            "Annnndddd.. No permissions for you!"
    );

    @Getter
    private String[] messages;

    SamMessage(String... messages){
        this.messages = messages;
    }

    public String getRandom(){
        Random random = new Random();
        int rInt = random.nextInt(this.messages.length);
        return messages[rInt];
    }

}
