package usa.devrocoding.synergy.spigot.api;

public abstract class SynergyPlugin {

    public abstract void init();
    public abstract boolean deinit();
    public abstract void onReload();

}
