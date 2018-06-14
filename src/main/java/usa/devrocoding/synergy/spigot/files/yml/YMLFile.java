package usa.devrocoding.synergy.spigot.files.yml;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

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
			e.printStackTrace();
		}
	}

	public void reload() {
		file = new File(dataFolder, name+".yml");
		data = YamlConfiguration.loadConfiguration(file);
	}

	public void setup(Object... things) {
		if (!(things.length <=0)||things!=null){
			for (int i=0;i<things.length;i+=2) {
				if (!get().contains(things[i].toString())) {
					get().set(things[i].toString(), things[i + 1]);
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
