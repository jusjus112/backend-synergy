package usa.devrocoding.synergy.spigot.events;

public interface Eventer<T> {
    public void run(T e);
}
