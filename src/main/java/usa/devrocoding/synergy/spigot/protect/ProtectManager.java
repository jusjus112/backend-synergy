package usa.devrocoding.synergy.spigot.protect;

import com.google.common.collect.Lists;
import java.util.List;
import lombok.Getter;
import usa.devrocoding.synergy.includes.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.files.json.JSONFile;

@Getter
public class ProtectManager extends Module {

  private List<String> offensiveWords;

  public ProtectManager(Core plugin){
    super(plugin, "Protection Manager", true);

    this.offensiveWords = this.getOffensiveWords();
  }

  @Override
  public void onReload(String response) {
    this.offensiveWords = this.getOffensiveWords();
  }

  public List<String> getOffensiveWords(){
    List<String> words = Lists.newArrayList();

    try{
      JSONFile jsonFile = getPlugin().getPluginManager().getFileStructure().getJSONFile("offensive_words");

      jsonFile.getAs("words").getAsJsonArray().forEach(jsonElement ->
          words.add(jsonElement.getAsString()));
    }catch (Exception e){
      Synergy.error("Error while loading Offensive Words");
      Synergy.error(e.getMessage());
    }

    return words;
  }


}
