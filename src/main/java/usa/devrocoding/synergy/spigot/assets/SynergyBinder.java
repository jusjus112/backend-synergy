package usa.devrocoding.synergy.spigot.assets;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import usa.devrocoding.synergy.spigot.Core;

public class SynergyBinder extends AbstractModule {

    private final Core plugin;

    public SynergyBinder(Core plugin) {
        this.plugin = plugin;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Override
    protected void configure() {
        this.bind(Core.class).toInstance(this.plugin);
    }
}
