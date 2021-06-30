package usa.devrocoding.synergy.spigot.objectives.object;

import java.util.ArrayList;
import java.util.Arrays;
import lombok.Getter;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import usa.devrocoding.synergy.includes.Cache;
import usa.devrocoding.synergy.includes.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.objectives.event.ObjectiveFinishedEvent;
import usa.devrocoding.synergy.spigot.objectives.event.ObjectiveEvent;
import usa.devrocoding.synergy.spigot.user.object.MessageModification;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.UtilMath;

public abstract class Objective {

    @Getter
    private @Nullable
    Objective next = null;
    @Getter
    private @Nullable Objective unlockFirst = null;

    public void register(){
        this.mechanics();

        Synergy.debug("LOADING OBJ UNLOCKS 1");
        Synergy.debug(name() + " = LOADING OBJ UNLOCKS 1");
        Synergy.debug(unlockFirst() + " = LOADING OBJ UNLOCKS 1");
        if (this.unlockFirst() != null) {
            this.unlockFirst = Core.getPlugin().getObjectiveManager()
                .getObjectiveFromClass(this.unlockFirst());
        }
        Synergy.debug("LOADING OBJ UNLOCKS 2");
        Synergy.debug(next() + " = LOADING OBJ UNLOCKS 2");
        if (this.next() != null) {
            Synergy.debug("Loaded THE NEXT OBJECTIVE");
            this.next = Core.getPlugin().getObjectiveManager()
                .getObjectiveFromClass(this.next());
        }
    }

    public abstract String name();
    public abstract String[] description();
    public abstract String[] rewards();
    public abstract Cache<Integer, Integer> percentage(SynergyUser synergyUser);
    public abstract void mechanics();
    public void onStart(SynergyUser synergyUser){}
    public abstract void onFinish(SynergyUser synergyUser);
    public abstract Class<? extends Objective> unlockFirst();
    public abstract Class<? extends Objective> next();

    public void sendFinishMessage(SynergyUser synergyUser){
        synergyUser.getSoundControl().play(Sound.ENTITY_PLAYER_LEVELUP);
        synergyUser.sendModifactionMessage(
            MessageModification.CENTERED,
            new ArrayList<String>(){{
                add(" ");
                add("§9§lFinished Objective");
                add("§e\""+Objective.this.name()+"\"");
                if (Objective.this.next != null){
                    add(" ");
                    add("§5§lNext Objective:");
                    Arrays.stream(Objective.this.next.description()).forEach(s -> {
                        add("  §e" + s);
                    });
                }
                add(" ");
            }}
        );

        synergyUser.getDisplay().sendTitleAndSubTitle(
            "§5§lNext Objective:",
            "§e\""+Objective.this.next.name()+"\"",
            20,50,20
        );
    }

    public int getPercentage(SynergyUser synergyUser){
        Cache<Integer, Integer> percentageCache = this.percentage(synergyUser);
        return UtilMath.getPercentage(percentageCache.getLeft(), percentageCache.getRight());
    }

    public boolean hasNext(){
        return this.next != null;
    }

    public String nextName(){
        if (this.next== null){
            return "";
        }
        return this.next.name();
    }

    public boolean isAbletoStart(SynergyUser synergyUser){
        Synergy.debug("ABLE TO START 1");
        if (!synergyUser.hasObjective(this)) {
            Synergy.debug("ABLE TO START 2");
            if (this.unlockFirst != null){
                Synergy.debug("ABLE TO START 3");
                return synergyUser.hasObjective(unlockFirst());
            }
            return true;
        }
        return false;
    }

    public void setNextActive(SynergyUser synergyUser){
        if (this.next == null){
            synergyUser.setCurrentObjective(null);
            return;
        }
        Synergy.debug("HAS NEXT OBJECTIVE 1");
        if (!synergyUser.hasObjective(this.next)) {
            Synergy.debug("HAS NEXT OBJECTIVE READY 2");
            synergyUser.setCurrentObjective(this.next);
            this.next.onStart(synergyUser);
        }else{
            Synergy.debug("HAS NEXT OBJECTIVE 3");
            synergyUser.setCurrentObjective(null);
        }
    }

    public boolean finish(SynergyUser synergyUser){
        if (isAbletoStart(synergyUser) && !synergyUser.hasObjective(this)) {
            Synergy.debug("Finish 2");
            try{
                Synergy.debug("Finish 3");
                synergyUser.unlockObjective(this);
                setNextActive(synergyUser);
                Core.getPlugin().getServer().getPluginManager().callEvent(new ObjectiveFinishedEvent(synergyUser, this));
                Synergy.debug("Finish 3");
                this.sendFinishMessage(synergyUser);
                onFinish(synergyUser);
            }catch (Exception e){
                Synergy.error("Error while finishing objective for user "+synergyUser.getName());
                Synergy.error(e.getMessage());
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public void addListener(Class<? extends Event> eventClass, ObjectiveEvent<? extends Event> listener){
        Core.getPlugin().getServer().getPluginManager().registerEvent(
            eventClass, listener, EventPriority.HIGHEST, listener, Core.getPlugin());
    }

}
