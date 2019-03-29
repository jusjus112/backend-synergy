package usa.devrocoding.synergy.spigot.user.object;

import lombok.Getter;

public enum UserExperience {

    NOOB("Noob"),
    OKE("Alright"),
    INTERMEDIATE("Intermediate"),
    PRO("Pro");

    @Getter
    private String name;

    UserExperience(String name){
        
    }

}
