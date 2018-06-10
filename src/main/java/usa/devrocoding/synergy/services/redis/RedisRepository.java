package usa.devrocoding.synergy.services.redis;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RedisRepository {

    private final Map<String, JedisPool> pools = Maps.newConcurrentMap();
    private final Random random = ThreadLocalRandom.current();
    private final JedisPool writePool;
    private final JedisPool readPool;
    private ConnectionData masterRedis;
    private List<ConnectionData> slaveRedis = Lists.newArrayList();

    private String host;
    private int port;

    public RedisRepository(File config) {
        this.readConfig(config);

        if (this.slaveRedis.isEmpty()) {
            this.slaveRedis.add(this.masterRedis);
        }

        this.writePool = this.generatePool(this.masterRedis);
        this.readPool = this.generatePool(this.slaveRedis.get(this.random.nextInt(this.slaveRedis.size())));
    }

    public Jedis getMasterConnection() {
        return this.writePool.getResource();
    }

    public Jedis getSlaveConnection() {
        return this.readPool.getResource();
    }

    public Jedis getSlaveConnection(int i) {
        return this.generatePool(this.slaveRedis.get(i)).getResource();
    }

    public JedisPool generatePool(ConnectionData data) {
        String key = this.getConnectionKey(data);
        JedisPool pool = this.pools.get(key);

        if (pool == null) {
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxWaitMillis(1000L);
            jedisPoolConfig.setMinIdle(5);
            jedisPoolConfig.setMaxTotal(20);
            jedisPoolConfig.setBlockWhenExhausted(true);
            pool = new JedisPool(jedisPoolConfig, data.getHost(), data.getPort(), 2000);

            this.pools.put(key, pool);
        }

        return pool;
    }

    private String getConnectionKey(ConnectionData data) {
        return data.getHost() + ":" + data.getPort();
    }

    public void readConfig(File config) {
        try {
            if (config == null || !config.exists()) {
                return;
            }

            JsonParser e = new JsonParser();
            FileReader reader = new FileReader(config);
            JsonObject parent = e.parse(reader).getAsJsonObject();
            JsonArray entities = parent.getAsJsonArray("data");

            entities.getAsJsonArray().forEach((connection) -> {
                JsonObject entity = connection.getAsJsonObject();

                this.host = entity.get("host").getAsString();
                this.port = entity.get("port").getAsInt();

                ConnectionData data = new ConnectionData(entity.get("host").getAsString(), entity.get("port").getAsInt(), entity.get("master").getAsBoolean());
                if (data.isMaster()) {
                    this.masterRedis = data;
                } else {
                    this.slaveRedis.add(data);
                }

            });
        } catch (JsonSyntaxException | IOException arg5) {
            arg5.printStackTrace();
        }

    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}