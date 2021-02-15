package usa.devrocoding.synergy.spigot.botsam.object;

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
            "My system tells me something went wrong."
    ),
    NO_PERMISSIONS(
            "Sorry, but you don't have these permissions.",
            "You are not allowed to use this.",
            "It would be nice if you had these permissions right?",
            "No permissions!",
            "Cannot let you do that. No permissions!",
            "Sorry, I can't allow you to use this.",
            "Try again, but with permissions please!",
            "You knew this was restricted -_- (No Permissions)"
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
