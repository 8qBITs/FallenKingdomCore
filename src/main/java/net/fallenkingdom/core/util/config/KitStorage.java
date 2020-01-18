package net.fallenkingdom.core.util.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import com.google.common.reflect.TypeToken;

import net.fallenkingdom.core.Main;
import net.fallenkingdom.core.util.Messenger;
import net.fallenkingdom.core.util.Utils;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

public class KitStorage {

	private static File configFile;
	private static CommentedConfigurationNode delays;
	private static ConfigurationLoader<CommentedConfigurationNode> configManager;
	private static CommentedConfigurationNode config;

	public static void init(File rootDir)
	{
		configFile = new File(rootDir, "kits.conf");
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
			delays = config.getNode("delays");
		}
		catch (IOException e)
		{
			Main.getMain().getLogger().info("An IO Exception occured.");
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
			Main.getMain().getLogger().info("An IO Exception occured.");
		}
	}
	
	public static CommentedConfigurationNode getConfig() {
		return config;
	}
	
	public static CommentedConfigurationNode getDelays() {
		return delays;
	}
	
	public static Kit getKit(String kit) {
		try {
			return KitStorage.getConfig().getNode(kit).getValue(TypeToken.of(Kit.class));
		} catch (ObjectMappingException e) {
			System.out.println("Bad bad with getting kit");
			e.printStackTrace();
			return null;
		}
	}
	
	public static void saveKit(String kit, List<ItemStack> items, long delay, boolean perm) {
		try {
			config.getNode(kit).setValue(TypeToken.of(Kit.class),new Kit(kit, items, delay, perm));
			save();
		} catch (ObjectMappingException e) {
			System.out.println("Bad bad with saving kit");
			e.printStackTrace();
		}
	}
	public static void saveKit(String kit, List<ItemStack> items, long delay) {
		saveKit(kit, items, delay, false);
	}
	public static void saveKit(String kit, List<ItemStack> items) {
		saveKit(kit, items, -1, false);
	}
	
	public static class Kit {
		String name;
		public List<ItemStack> items;
		public long delay = -1;
		public boolean perm = false;
		
		public Kit(String name, List<ItemStack> items, long delay, boolean permission) {
			this.name = name;
			this.items = items;
			this.delay = delay;
			this.perm = permission;
		}
		
		public Kit(String name, List<ItemStack> items, boolean permission) {
			this.name = name;
			this.items = items;
			this.perm = permission;
		}
		
		public void Collect(Player p) {
			if(KitStorage.getDelays().getNode(p.getIdentifier(), this.name)!=null && KitStorage.getDelays().getNode(p.getIdentifier(), this.name).getLong()>System.currentTimeMillis() && this.delay!=-1) {
				new Messenger(p).sendSubTitle("&cYou still need to wait");
				p.sendMessage(Messenger.iCanHasColor("&8[&cCORE&8] &fYou still need to wait &c"+Utils.parseTimeFormat((KitStorage.getDelays().getNode(p.getIdentifier(), this.name).getLong()-System.currentTimeMillis())/1000)));
			} else {			
				for(ItemStack is : this.items) {
					p.getInventory().offer(is);
				}
				KitStorage.getDelays().getNode(p.getIdentifier(), this.name).setValue(System.currentTimeMillis()+(this.delay*1000));
				p.sendMessage(Messenger.iCanHasColor("&8[&cCORE&8] &fRedeemed kit &6"+this.name));
			}
		}
	}
	@SuppressWarnings("serial")
	public static class KitSerializer implements TypeSerializer<Kit> {
		@Override
		public Kit deserialize(TypeToken<?> arg0, ConfigurationNode arg1) throws ObjectMappingException {
			return new Kit(arg1.getKey().toString(), arg1.getNode("items").getValue(new TypeToken<List<ItemStack>>() {}), arg1.getNode("delay").getLong(), arg1.getNode("perm").getBoolean());
		}
		@Override
		public void serialize(TypeToken<?> arg0, Kit arg1, ConfigurationNode arg2) throws ObjectMappingException {
			arg2.getNode("delay").setValue(arg1.delay);
			arg2.getNode("perm").setValue(arg1.perm);
			arg2.getNode("items").setValue(new TypeToken<List<ItemStack>>() {}, arg1.items);
		}
	}
}
