package net.fallenkingdom.core.util.config;

import java.io.File;
import java.io.IOException;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.google.common.reflect.TypeToken;

import net.fallenkingdom.core.Main;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

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
	
	@SuppressWarnings("unchecked")
	public static Location<World> getLocation(String uuid, String home) {
		try {
			return config.getNode(uuid,home).getValue(TypeToken.of(Location.class));
		} catch (ObjectMappingException e) {
			System.out.println("Bad bad with getting home config!");
			e.printStackTrace();
			return null;
		}
	}
	
	public static void saveLocation(String uuid, String home, Location<World> loc) {
		try {
			if(loc==null) {
				config.getNode(uuid).removeChild(home);
			} else {
				config.getNode(uuid,home).setValue(TypeToken.of(Location.class), loc);
			}
			save();
		} catch (ObjectMappingException e) {
			e.printStackTrace();
			System.out.println("Bad bad with setting home config!");
		}
	}
	
	public static int getCountHomes(String uuid) {
		// fucking list broken piece of fuck
		return config.getNode(uuid).getChildrenMap().size();
	}
	
}
