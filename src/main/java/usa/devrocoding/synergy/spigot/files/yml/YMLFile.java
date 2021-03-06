package usa.devrocoding.synergy.spigot.files.yml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.assets.C;

/**
 * @Author JusJusOneOneTwo
 * @Website devrocoding.com
 * @Created 06-02-2017
 */

public class YMLFile {

	@Getter
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

	public YMLFile setHeader(String header){
		get().options().header(header);
		return this;
	}

	public void set(HashMap<String, Object> data) {
		if (!data.isEmpty()){
			for(String key : data.keySet()){
				if (key.startsWith("#")){
					continue;
				}
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
