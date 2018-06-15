package usa.devrocoding.synergy.spigot.api;

public abstract class SynergyPlugin {

    public abstract void init();
    public abstract void deinit();
    public abstract void onPreReload();
    public abstract void onReload();

}
