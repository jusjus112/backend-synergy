package usa.devrocoding.synergy.spigot.bot_sam.object;

import lombok.Getter;

import java.util.Random;

public enum SamMessage {

    CANNOT_DO_THAT(
            "There are reasons why I am not doing that...",
            "Can't do that. Always look twice before executing this command.",
            "Oops. I'm sorry, I cannot perform your request."
    ),
    ERROR(
            "OH no... Something went wrong",
            "My code just broke, good job player!",
            "My code tells me something went wrong."
    ),
    NO_PERMISSIONS(
            "Sorry, but I see that you don't have permissions..",
            "I'm not letting you use this...",
            "Annnndddd.. No permissions for you!",
            "Cannot let you through",
            "Sorry, I can't allow you to use this",
            "Try again, but with permissions please!",
            "You knew this command was restricted...",
            "Hahahahaha.. Nice try, no permissions"
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
