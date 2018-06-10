package usa.devrocoding.synergy.services.redis;

import com.google.common.collect.Lists;
import redis.clients.jedis.Jedis;
import usa.devrocoding.synergy.services.RedisService;

import java.util.List;

public abstract class MultiChannelRedisModule extends RedisModule {

    private final RedisService service;
    private final List<String> channels = Lists.newArrayList();

    public MultiChannelRedisModule(RedisService service) {
        super(service, null);
        this.service = service;
    }

    @Override
    public void subscribe() {
        if (this.channels.isEmpty()) {
            return;
        }

        new Thread(() -> {
            Jedis jedis = service.getRepository().getMasterConnection();
            jedis.subscribe(this, this.channels.toArray(new String[this.channels.size()]));
            jedis.close();
        }).start();
    }

    public void registerChannel(String... channels) {
        for (String s : channels) {
            if (this.channels.contains(s)) {
                return;
            }

            this.channels.add(s);
            System.out.println("Registering channels " + s);
        }
    }

    public void publish(String channel, String message) {
        new Thread(() -> {
            Jedis jedis = service.getRepository().getMasterConnection();
            jedis.publish(channel, message);
            jedis.close();
        }).start();
    }
}