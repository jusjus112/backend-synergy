package usa.devrocoding.synergy.proxy.user;

import com.google.common.io.ByteStreams;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import lombok.Getter;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.ProxyModule;
import usa.devrocoding.synergy.proxy.user.listener.UserJoinProxyListener;
import usa.devrocoding.synergy.proxy.user.listener.UserQuitProxyListener;
import usa.devrocoding.synergy.proxy.user.object.ProxyUser;

import java.util.*;

public class UserManager extends ProxyModule {

    @Getter
    private final Map<UUID, ProxyUser> proxyUsers = new HashMap<>();

    public UserManager(Core plugin){
        super(plugin, "User Manager", false);

        registerListeners(
            new UserJoinProxyListener(),
            new UserQuitProxyListener()
        );
    }

    @Override
    public void reload() {

    }

    @Override
    public void deinit() {

    }

    public ProxyUser getUser(UUID uuid){
        return this.proxyUsers.get(uuid);
    }

    public ProxyUser getUser(String name){
        for (ProxyUser proxyUser : this.proxyUsers.values()) {
            if (proxyUser.getProxiedPlayer().getName().equalsIgnoreCase(name)){
                return proxyUser;
            }
        }
        return null;
    }

    public InputStream convertUniqueId(UUID uuid) {
        byte[] bytes = new byte[16];
        ByteBuffer.wrap(bytes)
            .putLong(uuid.getMostSignificantBits())
            .putLong(uuid.getLeastSignificantBits());
        return new ByteArrayInputStream(bytes);
    }

    public UUID convertBinaryStream(InputStream stream) {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        try {
            buffer.put(ByteStreams.toByteArray(stream));
            buffer.flip();
            return new UUID(buffer.getLong(), buffer.getLong());
        } catch (IOException e) {
            // Handle the exception
        }
        return null;
    }
}
