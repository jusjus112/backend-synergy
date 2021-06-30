package usa.devrocoding.synergy.spigot;

import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.java.JavaPlugin;

@RequiredArgsConstructor
public abstract class SynergySpigot extends JavaPlugin {

  public String name() {return "Synergy";}
  public void preInit() {}
  public void preDeInit() {}

  public abstract void init();
  public abstract void deinit();

}
