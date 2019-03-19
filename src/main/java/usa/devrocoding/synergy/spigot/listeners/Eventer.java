package usa.devrocoding.synergy.spigot.listeners;

public interface Eventer<T> {
    public void run(T e);
}
