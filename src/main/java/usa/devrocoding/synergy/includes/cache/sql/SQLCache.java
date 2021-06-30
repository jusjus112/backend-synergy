package usa.devrocoding.synergy.includes.cache.sql;

import usa.devrocoding.synergy.includes.Pair;

import java.util.HashMap;
import java.util.Map;

public class SQLCache {

    //                        key
    //          table       player      key     data
    private Map<String, Map<Object, Pair<String, Object>>> sqlCache = new HashMap<>();

    public void add(String table, Object primaryKey, String key, Object  data){
        Map<Object, Pair<String, Object>> keyCache;
        if (this.sqlCache.containsKey(table)){
            keyCache = this.sqlCache.get(table);

            keyCache.put(primaryKey, new Pair<String, Object>(){{
                setLeft(key);
                setRight(data);
            }});

        }else{
            // Table exists NOT

            keyCache = new HashMap<>();
        }
    }

}
