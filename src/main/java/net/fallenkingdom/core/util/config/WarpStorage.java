package net.fallenkingdom.core.util.config;

import java.io.File;
import java.io.IOException;

import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.google.common.reflect.TypeToken;

import net.fallenkingdom.core.Main;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class WarpStorage {

	private static File configFile;
	private static ConfigurationLoader<CommentedConfigurationNode> configManager;
	private static CommentedConfigurationNode config;

	public static void init(File rootDir)
	{
		configFile = new File(rootDir, "warps.conf");
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
	public static Location<World> getLocation(String warp){
		try {
			return config.getNode(warp).getValue(TypeToken.of(Location.class));
		} catch (ObjectMappingException e) {
			System.out.println("Bad bad with getting warp config!");
			e.printStackTrace();
			return null;
		}
	}
	
	public static void saveLocation(String warp, Location<World> loc) {
		try {
			if(loc==null) {
				config.removeChild(warp);
			} else {
				config.getNode(warp).setValue(TypeToken.of(Location.class), loc);
			}
			save();
		} catch (ObjectMappingException e) {
			e.printStackTrace();
			System.out.println("Bad bad with setting warp config!");
		}
	}
	
}
