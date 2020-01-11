package me.qbit.core.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import me.qbit.core.Main;

public class configuration {

	File configFile;
	YamlConfiguration config;
	
	public configuration(String name) {
		configFile = new File(Main.getMain().getDataFolder(), name);
		try {
			if(!configFile.exists())
				configFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		config = YamlConfiguration.loadConfiguration(configFile);
	}
	
	public YamlConfiguration getConfig() {
		return config;
	}
	
	public void saveConfig() {
		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
