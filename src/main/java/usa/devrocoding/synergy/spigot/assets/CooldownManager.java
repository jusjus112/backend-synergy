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

    private final static HashMap<Object, HashMap<Integer, Boolean>> cd = new HashMap<Object, HashMap<Integer, Boolean>>();

    public void run() {
        new BukkitRunnable(){
            @Override
            public void run() {
                Iterator<Object> iter = cd.keySet().iterator();
                while (iter.hasNext()){
                    Object ob = iter.next();
                    if (cd.containsKey(ob)) {
                        HashMap<Integer, Boolean> map = cd.get(ob);
                        if (map.values().contains(Boolean.TRUE)){
                            int i = 0;
                            for(int k : map.keySet())
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
        }.runTaskTimerAsynchronously(getPlugin(), 100, 20);
    }
  
    public void addCooldown(Object ob, int seconds){
        if (cd.containsKey(ob))return;
        HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();
        map.put(seconds, true);
        cd.put(ob, map);
    }
  
    public boolean isOnCooldown(Object ob){
        HashMap<Integer, Boolean> map = cd.get(ob);
        boolean value = false;
        if (map == null || map.isEmpty())
            return false;
        for (boolean i : map.values())
            value=i;
        return value;
    }
  
    public Integer getLastSeconds(Object ob){
        if (!cd.containsKey(ob))
            return 0;
        HashMap<Integer, Boolean> map = cd.get(ob);
        int key = 0;
        for (int i : map.keySet())
            key=i;
        return key;
    }

}
