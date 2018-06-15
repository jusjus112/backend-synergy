package usa.devrocoding.synergy.services.redis;

import com.google.common.base.Supplier;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import usa.devrocoding.synergy.services.RedisService;

public abstract class RedisModule extends JedisPubSub {

    private final RedisService redisService;
    private final String channel;
    private final RedisRepository repository;

    public RedisModule(RedisService redisService, String channel) {
        this.redisService = redisService;
        this.channel = channel;
        this.repository = redisService.getRepository();

        redisService.registerRedisModule(this);
    }

    public void subscribe() {
        new Thread(() -> {
            Jedis jedis = this.repository.getMasterConnection();
            jedis.subscribe(this, new String[]{this.channel});
            jedis.close();
        }).start();
    }

    public void publish(String string) {
        new Thread(() -> {
            Jedis jedis = this.repository.getMasterConnection();
            jedis.publish(this.channel, string);
            jedis.close();
        }).start();
    }

    public void setData(String key, String value) {
        new Thread(() -> {
            Jedis jedis = repository.getMasterConnection();

            jedis.hset("synergy", key, value);
            jedis.close();
        }).start();
    }

    public Supplier<String> getData(String key) {
        Jedis jedis = this.repository.getMasterConnection();

        Supplier<String> supplier = () -> jedis.hget("synergy", key) == null ? null : new String(jedis.hget("synergy", key));

        jedis.close();

        return supplier;
    }

    public abstract void recieve(String arg0, String arg1);

    public void onMessage(String channel, String message) {
        this.recieve(channel, message);
    }

}
