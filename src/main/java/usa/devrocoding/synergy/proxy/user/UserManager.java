package usa.devrocoding.synergy.proxy.user;

import lombok.Getter;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.ProxyModule;
import usa.devrocoding.synergy.proxy.user.listener.UserJoinProxyListener;
import usa.devrocoding.synergy.proxy.user.object.ProxyUser;

import java.util.*;

public class UserManager extends ProxyModule {

    @Getter
    private Map<UUID, ProxyUser> proxyUsers = new HashMap<>();

    public UserManager(Core plugin){
        super(plugin, "User Manager", false);

        registerListeners(
                new UserJoinProxyListener()
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
}
