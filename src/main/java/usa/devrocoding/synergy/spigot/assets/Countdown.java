package usa.devrocoding.synergy.spigot.assets;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.scheduler.BukkitRunnable;
import usa.devrocoding.synergy.assets.object.SynergyPeriod;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.user.object.MessageModification;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

@Getter
public abstract class Countdown {

  private final int intialTime;
  private final SynergyUser synergyUser;
  private int timeLeft;

  public Countdown(int intialTime, SynergyUser synergyUser){
    this(intialTime, synergyUser, false);
  }

  public Countdown(int intialTime, SynergyUser synergyUser, boolean sound){
    this.intialTime = intialTime;
    this.synergyUser = synergyUser;
    this.timeLeft = intialTime;

    new BukkitRunnable(){
      @Override
      public void run() {
        String message = message(timeLeft);
        if (synergyUser.isOnline() && (message != null && !message.equals(" "))) {
          synergyUser.sendModifactionMessage(MessageModification.RAW, message);
        }
        if (sound){
          synergyUser.getSoundControl().pling();
        }
        timeLeft--;

        if (timeLeft <= 0){
          cancel();
          onCountdownEnd();
        }
      }
    }.runTaskTimer(Core.getPlugin(), 1L, SynergyPeriod.SECOND.getPeriod());
  }

  public abstract void onCountdownEnd();
  public abstract String message(int timeLeft);

}
