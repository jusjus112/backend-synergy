package usa.devrocoding.synergy.assets.cache;

import lombok.Getter;
import usa.devrocoding.synergy.assets.cache.sql.SQLCache;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;

public class CacheManager extends Module {

    @Getter
    private SQLCache sqlCache;

    public CacheManager(Core plugin){
        super(plugin,"Cache Manager");

        // Load cache modules
        this.sqlCache = new SQLCache();
    }

}
