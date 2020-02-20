package usa.devrocoding.synergy.spigot.utilities;

import org.bukkit.Sound;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class UtilSound {

    private SynergyUser synergyUser;

    public UtilSound(SynergyUser synergyUser){
        this.synergyUser = synergyUser;
    }

    public void pling(){
        synergyUser.getPlayer().playSound(synergyUser.getPlayer().getLocation(), Sound.BLOCK_NOTE_PLING, 2, 2);
    }

    public void deepPling(){
        synergyUser.getPlayer().playSound(synergyUser.getPlayer().getLocation(), Sound.BLOCK_NOTE_BASS, 2, 2);
    }

    public void play(Sound sound){
        synergyUser.getPlayer().playSound(synergyUser.getPlayer().getLocation(), sound, 2, 2);
    }
}
