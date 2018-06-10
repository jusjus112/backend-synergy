package usa.devrocoding.synergy.services;

import usa.devrocoding.synergy.services.redis.RedisModule;
import usa.devrocoding.synergy.services.redis.RedisRepository;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class RedisService {

    private final Set<RedisModule> redisModules = new HashSet<>();
    private final RedisRepository repository;


    public RedisService(File dataFolder) {
        this.repository = new RedisRepository(new File(dataFolder + File.separator + "redis.json"));
    }

    public void registerRedisModule(RedisModule module) {
        this.redisModules.add(module);
    }

    public RedisRepository getRepository() {
        return this.repository;
    }

}
