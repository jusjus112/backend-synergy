package usa.devrocoding.synergy.spigot.assets;

import lombok.Getter;
import usa.devrocoding.synergy.includes.Cache;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.assets.exceptions.cache.CacheAlreadyExistsException;
import usa.devrocoding.synergy.spigot.assets.exceptions.cache.CacheNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class CacheManager extends Module {

    @Getter
    private List<Cache<Object, Object>> cache = new ArrayList<>();

    public CacheManager(Core plugin){
        super(plugin,"Cache Manager", false);
    }

    public void loadCache(){
        getPlugin().getUserManager().cacheExisitingPlayers();
        getPlugin().getChangelogManager().cacheChangelogs();
    }

    public void saveCache(){

    }

    public Object getCachedItem(Object key) throws CacheNotFoundException {
        for(Cache<Object, Object> c : getCache()){
            if (c.getLeft().equals(key)){
                return c.getRight();
            }
        }
        throw new CacheNotFoundException("Couldn't find object with given key");
    }

    public void addToCache(Object key, Object value) throws CacheAlreadyExistsException{
        for(Cache<Object, Object> c : getCache()){
            if (c.getLeft().equals(key)){
                throw new CacheAlreadyExistsException("Cannot add cache with given key, already exists");
            }
        }
        this.cache.add(new Cache<Object, Object>(){{
            setLeft(key);
            setRight(value);
        }});
    }

    @Override
    public void onReload(String response) {

    }
}
