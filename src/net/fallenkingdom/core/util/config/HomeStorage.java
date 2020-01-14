package net.fallenkingdom.core.util.config;

import java.io.File;
import java.io.IOException;

import net.fallenkingdom.core.Main;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class HomeStorage {

	private static File configFile;
	private static ConfigurationLoader<CommentedConfigurationNode> configManager;
	private static CommentedConfigurationNode config;

	public static void init(File rootDir)
	{
		configFile = new File(rootDir, "homes.conf");
		configManager = HoconConfigurationLoader.builder().setPath(configFile.toPath()).build();
	}

	public static void load()
	{
		try
		{
			if (!configFile.exists())
			{
				configFile.getParentFile().mkdirs();
				configFile.createNewFile();
				config = configManager.load();
				configManager.save(config);
			}
			config = configManager.load();
		}
		catch (IOException e)
		{
			Main.getMain().getLogger().info("An IO Exception accured.");
			e.printStackTrace();
			return;
		}
		
	}

	public static void save()
	{
		try
		{
			configManager.save(config);
		}
		catch (IOException e)
		{
			Main.getMain().getLogger().info("An IO Exception accured.");
		}
	}
	
	public static CommentedConfigurationNode getConfig() {
		return config;
	}
	
}
