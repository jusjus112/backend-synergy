package usa.devrocoding.synergy.spigot.assets;

import lombok.Getter;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;

import java.util.HashMap;
import java.util.Map;

public class CacheManager extends Module {

    @Getter
    //          table       player      key     data
    private static Map<String, Map<Object, Map<String, Object>>> sqlCache = new HashMap<>();

    public CacheManager(Core plugin){
        super(plugin,"Cache Manager");
    }

}
