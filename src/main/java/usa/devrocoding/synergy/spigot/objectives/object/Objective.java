package usa.devrocoding.synergy.spigot.objectives.object;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.listeners.EventListener;
import usa.devrocoding.synergy.spigot.listeners.Listeners;
import usa.devrocoding.synergy.spigot.objectives.event.ObjectiveFinishedEvent;
import usa.devrocoding.synergy.spigot.user.object.MessageModification;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public abstract class Objective {

    public Objective(){
        this.mechanics();
    }

    public abstract String name();
    public abstract String[] description();
    public abstract void mechanics();
    public abstract Objective unlockFirst();
    public abstract Objective next();

    public void onFinish(SynergyUser synergyUser){
        synergyUser.sendModifactionMessage(
            MessageModification.CENTERED,
            new ArrayList<String>(){{
                add(C.getLine());
                add("§9§lFinished Objective");
                add("§e\""+Objective.this.name()+"\"");
                add(" ");
                if (Objective.this.next() != null){
                    add("§5§lNext Objective:");
                    add("§e\""+Objective.this.next().name()+"\"");
                }
                add(C.getLine());
            }}
        );
    }

    public boolean isAbletoStart(SynergyUser synergyUser){
        if (unlockFirst() == null){
            return true;
        }else{
            return synergyUser.hasObjective(unlockFirst());
        }
    }

    public void setNextActive(SynergyUser synergyUser){
        if (next() == null){
            return;
        }
        if (!synergyUser.hasObjective(next())) {
            synergyUser.setCurrentObjective(next());
        }
    }

    public boolean finish(SynergyUser synergyUser){
        Synergy.debug("Finish 1");
        if (isAbletoStart(synergyUser) && !synergyUser.hasObjective(this)) {
            Synergy.debug("Finish 2");
            try{
                Synergy.debug("Finish 3");
                synergyUser.unlockObjective(this);
                setNextActive(synergyUser);
                Core.getPlugin().getServer().getPluginManager().callEvent(new ObjectiveFinishedEvent(synergyUser, this));
                Synergy.debug("Finish 3");
                this.onFinish(synergyUser);
            }catch (Exception e){
                Synergy.error("Error while finishing objective for user "+synergyUser.getName());
                Synergy.error(e.getMessage());
            }
            return true;
        }
        return false;
    }

    public void addListener(EventListener<?> listener) {
        Listeners.addListener(listener);
    }

}
