package usa.devrocoding.synergy.proxy.maintenance;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Getter;
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

public class MaintenanceManager extends ProxyModule {

    @Getter
    private List<String> serverOnMaintenance = new ArrayList<>();
    @Getter
    private Map<String, String> motd = new HashMap<>();
    private ProxyJSONFile cacheFile;
    private ProxyYMLFile file;

    public MaintenanceManager(Core plugin){
        super(plugin, "Maintenance Manager", true);

        this.cacheFile = new ProxyJSONFile("cache","cache");
        this.file = new ProxyYMLFile(getPlugin(), getPlugin().getDataFolder(), "maintenance");

        this.file.set(new HashMap<String, Object>(){{
            put("maintenance.motd.first_line", "&6&lRUNNING ON SYNERGY");
            put("maintenance.motd.second_line", "&bThe MOTD is centered automatically!");
            put("maintenance.version", "MAINTENANCE");
            put("maintenance.kick_message", "&cSorry but this server is in maintenance!");
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
        }
    }

    public void enableMaintenance(){
        enableMaintenance(getProxyServer());
    }

    public void enableMaintenance(String server){
        if (server == null){
            enableMaintenance();
        }

        if (!isServerOnMaintenance(server)) {
            this.serverOnMaintenance.add(server);
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
