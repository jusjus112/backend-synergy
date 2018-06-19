package usa.devrocoding.synergy.spigot.files.yml;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.assets.Pair;

/**
 * @Author JusJusOneOneTwo
 * @Website devrocoding.com
 * @Created 06-02-2017
 */

public class YMLFile {
	
	private File file;
	private FileConfiguration data;
	private String dataFolder;
	private String name;

	public YMLFile(String dataFolder, String fileName) {
		this.dataFolder = dataFolder;
		this.name = fileName;
		
		file = new File(dataFolder, fileName+".yml");
		data = YamlConfiguration.loadConfiguration(file);
	}

	public FileConfiguration get() {
		return data;
	}

	public void save() {
		try {
			data.save(file);
		} catch (IOException e) {
			Synergy.error(e.getMessage());
		}
	}

	public void reload() {
		file = new File(dataFolder, name+".yml");
		data = YamlConfiguration.loadConfiguration(file);
	}

	public void setup(HashMap<String, Object> data) {
		if (!data.isEmpty()){
			for(String key : data.keySet()){
				if (!get().contains(key)){
					get().set(key, data.get(key));
				}
			}
		}
		save();
	}

	public boolean exists() {
		if (file.exists())
			return true;
		return false;
	}

}
