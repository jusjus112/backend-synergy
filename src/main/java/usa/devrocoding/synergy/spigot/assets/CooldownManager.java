package usa.devrocoding.synergy.spigot.assets;

import org.bukkit.scheduler.BukkitRunnable;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @Author JusJusOneOneTwo
 * @Website devrocoding.com
 * @Created 06-02-2017
 */
public class CooldownManager extends Module {

    public CooldownManager(Core plugin){
        super(plugin, "Cooldown Manager", false);

        run();
    }

    @Override
    public void reload(String response) {

    }

    private final static HashMap<Object, HashMap<Long, Boolean>> cd = new HashMap<>();

    public void run() {
        new BukkitRunnable(){
            @Override
            public void run() {
                Iterator<Object> iter = cd.keySet().iterator();
                while (iter.hasNext()){
                    Object ob = iter.next();
                    if (cd.containsKey(ob)) {
                        HashMap<Long, Boolean> map = cd.get(ob);
                        if (map.containsValue(Boolean.TRUE)){
                            long i = 0;
                            for(long k : map.keySet())
                                i=k;
                            i--;
                            map.clear();
                            if (i<=0){
                                iter.remove();
                                cd.remove(ob);
                            }
                            else map.put(i, true);
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(getPlugin(), 100, 1);
    }
  
    public void addCooldown(Object ob, long ticks){
        if (cd.containsKey(ob))return;
        HashMap<Long, Boolean> map = new HashMap<>();
        map.put(ticks, true);
        cd.put(ob, map);
    }
  
    public boolean isOnCooldown(Object ob){
        HashMap<Long, Boolean> map = cd.get(ob);
        boolean value = false;
        if (map == null || map.isEmpty())
            return false;
        for (boolean i : map.values())
            value=i;
        return value;
    }
  
    public Long getLastMiliSeconds(Object ob){
        if (!cd.containsKey(ob))
            return 0L;
        HashMap<Long, Boolean> map = cd.get(ob);
        long key = 0;
        for (long i : map.keySet())
            key=i;
        return key;
    }

}
