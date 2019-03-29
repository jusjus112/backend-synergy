package usa.devrocoding.synergy.spigot.assets.depends;

import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;

import java.util.Arrays;

public class PlaceHolderAPI extends Module {

    public PlaceHolderAPI(Core plugin){
        super(plugin, "PlaceHolderAPI Manager", false);
    }

    @Override
    public void reload(String response) {

    }

    public void registerPlaceholder(me.clip.placeholderapi.expansion.PlaceholderExpansion... expansions){
        Arrays.stream(expansions).forEach(
                expansion -> expansion.register()
        );
    }

}
