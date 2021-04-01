package usa.devrocoding.synergy.proxy.maintenance;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.ProxyModule;
import usa.devrocoding.synergy.proxy.files.ProxyJSONFile;
import usa.devrocoding.synergy.proxy.files.ProxyYMLFile;
import usa.devrocoding.synergy.proxy.maintenance.command.CommandMaintenance;
import usa.devrocoding.synergy.proxy.maintenance.listener.LoginListener;
import usa.devrocoding.synergy.proxy.maintenance.listener.ProxyPingListener;
import usa.devrocoding.synergy.proxy.maintenance.listener.ServerConnectListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MaintenanceManager extends ProxyModule {

    // TODO: When disabling maintenance, kick everyone with a cooldown

    @Getter
    private List<String> serverOnMaintenance = new ArrayList<>();
    @Getter
    private Map<String, Object> motd = new HashMap<>();
    private ProxyJSONFile cacheFile;
    private ProxyYMLFile file;

    public MaintenanceManager(Core plugin){
        super(plugin, "Maintenance Manager", true);

        this.cacheFile = new ProxyJSONFile("cache","cache");
        this.file = new ProxyYMLFile(getPlugin(), getPlugin().getDataFolder(), "maintenance");

        this.file.set(new HashMap<String, Object>(){{
            put("maintenance.motd.first_line", "&cSorry but &e&lSERVER_NAME");
            put("maintenance.motd.second_line", "&cIs on &lMAINTENANCE. &cWe'll be back ASAP");
            put("maintenance.version", "MAINTENANCE");
            put("maintenance.kick_message", "&cSorry but the server your connecting to is in maintenance!");

            put("motd.first_line", "&6&lRUNNING ON SYNERGY");
            put("motd.second_line", "&bThe MOTD is centered automatically!");
            put("motd.fakePlayerCount", 0);
        }});

        registerListeners(
                new ProxyPingListener(),
                new ServerConnectListener(),
                new LoginListener()
        );

        registerCommands(
                new CommandMaintenance()
        );
    }

    public boolean isServerOnMaintenance(String server){
        return this.serverOnMaintenance.contains(server);
    }

    public String getProxyServer(){
        return "proxy";
    }

    @Override
    public void reload() {
        loadData();
    }

    @Override
    public void deinit() {
        JsonArray jsonArray = new JsonArray();
        for(String s : this.serverOnMaintenance){
            jsonArray.add(s);
        }
        this.cacheFile.write("maintenance", jsonArray, true).finish();
    }

    public void loadData(){
        if (this.cacheFile.exists("maintenance")) {
            this.cacheFile.get().getAsJsonArray("maintenance").iterator().forEachRemaining(
                    jsonElement -> this.serverOnMaintenance.add(jsonElement.getAsString())
            );
        }
        if (this.file.getConfiguration().contains("maintenance")){
            this.motd.clear();
            this.motd.put("version", this.file.getConfiguration().getString("maintenance.version"));
            this.motd.put("motd.first_line", this.file.getConfiguration().getString("maintenance.motd.first_line"));
            this.motd.put("motd.second_line", this.file.getConfiguration().getString("maintenance.motd.second_line"));
            this.motd.put("kick_message", this.file.getConfiguration().getString("maintenance.kick_message"));

            this.motd.put("motd_normal.first_line", this.file.getConfiguration().getString("motd.first_line"));
            this.motd.put("motd_normal.second_line", this.file.getConfiguration().getString("motd.second_line"));
            this.motd.put("motd.fakePlayerCount", this.file.getConfiguration().getInt("motd.fakePlayerCount"));
        }
    }

    public void enableMaintenance(){
        enableMaintenance(getProxyServer());
    }

    public TextComponent goodbyeMessage(){
        return new TextComponent(Synergy.SynergyColor.ERROR + "Maintenance mode has been enabled.");
    }

    public void enableMaintenance(String server){
        if (server == null){
            enableMaintenance();
            return;
        }

        if (!isServerOnMaintenance(server)) {
            this.serverOnMaintenance.add(server);
        }

        getPlugin().getProxy().broadcast(new TextComponent(
                Synergy.SynergyColor.ERROR + "" +ChatColor.BOLD + "MAINTENANCE HAS BEEN ENABLED\n" +
                Synergy.SynergyColor.ERROR + "This server is now in maintenance.\n" +
                Synergy.SynergyColor.ERROR + "You will be kicked automatically in "+ Synergy.SynergyColor.INFO +"30 seconds."
        ));

        if (server.equalsIgnoreCase(getProxyServer())) {
            getPlugin().getProxy().getScheduler().schedule(getPlugin(), () ->
                getPlugin().getProxy().getPlayers().forEach(proxiedPlayer ->
                    proxiedPlayer.disconnect(goodbyeMessage())), 30, TimeUnit.SECONDS);
        }
    }

    public void disableMaintenance(){
        disableMaintenance(getProxyServer());
    }

    public void disableMaintenance(String server){
        if (server == null){
            disableMaintenance();
        }

        if (isServerOnMaintenance(server)){
            this.serverOnMaintenance.remove(server);
        }
    }
}
