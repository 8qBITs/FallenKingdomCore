package me.qbit.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.yaml.snakeyaml.Yaml;

import me.qbit.core.ChunkLoader.ChunkHolder;
import me.qbit.core.ChunkLoader.MysqlMethods;
import me.qbit.core.ChunkLoader.ThreadKeepChunksLoaded;
import me.qbit.core.commands.*;
import me.qbit.core.commands.home.*;
import me.qbit.core.events.inventoryMoveItem;
import me.qbit.core.events.playerDeath;
import me.qbit.core.events.playerJoin;
import me.qbit.core.utils.PlayerList;
import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class Main extends JavaPlugin {
	//TODO: all the commands in plugins.yml
	messenger m = new messenger();
	util u = new util();
	static List<Player> vanished_pl = new ArrayList<Player>();
	public ArrayList<ChunkHolder> myChunkHolders = new ArrayList<ChunkHolder>();
	ThreadKeepChunksLoaded threadKeepChunksLoaded;
	static Main me;
	File mainConfigFile;
	File homesStorageFile;
	File backStorageFile;
	YamlConfiguration mainConfig;
	YamlConfiguration homesStorage;
	YamlConfiguration backStorage;
	
	@Override
	public void onEnable() {
		
		me = this;
		getDataFolder().mkdir();
		mainConfigFile = new File(getDataFolder(), "config.yml");
		homesStorageFile = new File(getDataFolder(), "homes.yml");
		backStorageFile = new File(getDataFolder(), "backs.yml");
		try {
			if(!mainConfigFile.exists())
				mainConfigFile.createNewFile();
			if(!homesStorageFile.exists())
				homesStorageFile.createNewFile();
			if(!backStorageFile.exists())
				backStorageFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mainConfig = YamlConfiguration.loadConfiguration(mainConfigFile);
		homesStorage = YamlConfiguration.loadConfiguration(homesStorageFile);
		backStorage = YamlConfiguration.loadConfiguration(backStorageFile);
		
		registerCommands();
		registerEvents();
		
        BukkitScheduler scheduler3 = getServer().getScheduler();
        scheduler3.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
            	m.broadcastNull();
                m.broadcast("&eThank you for playing on &6&lF&e&lallen &6&lK&e&lingdom&e, make sure to join our discord server: &fhttps://discord.gg/zhrW4zC");
                m.broadcastNull();
            }
        }, 0L, 12000L);
        /*BukkitScheduler scheduler4 = getServer().getScheduler();
        scheduler4.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
            	
            }
        }, 0L, 1L);*/
	}
	
	public static void UpdateVanishList(Player pl, boolean state) {
		if(state && !vanished_pl.contains(pl)) {
			vanished_pl.add(pl);
			for(Player opl : Bukkit.getOnlinePlayers()) {
				if(opl.equals(pl))
					continue;
				PlayerList.getPlayerList(opl).removePlayer(pl);
				opl.hidePlayer(pl);
			}
		} else if(!state && vanished_pl.contains(pl)) {
			vanished_pl.remove(pl);
			for(Player opl : Bukkit.getOnlinePlayers()) {
				PlayerList.getPlayerList(opl).addPlayer(pl);
				if(opl.equals(pl))
					continue;
				opl.showPlayer(pl);
			}
		}
	}
	
	public static List<Player> getVanished() {
		List<Player> copy = new ArrayList<Player>(vanished_pl.size());
		for(Player p : vanished_pl) {
			copy.add(Bukkit.getPlayer(p.getUniqueId())); // deep copy
		}
		return copy;
	}
	
	public static YamlConfiguration GetMainConfig() {
		return Main.getMain().mainConfig;
	}
	
	public static YamlConfiguration GetHomeStorage() {
		return Main.getMain().homesStorage;
	}
	
	public static YamlConfiguration GetBackStorage() {
		return Main.getMain().backStorage;
	}
	
	public static void SaveMainConfig() {
		Main main = Main.getMain();
		try {
			main.mainConfig.save(main.mainConfigFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void SaveHomeConfig() {
		Main main = Main.getMain();
		try {
			main.homesStorage.save(main.homesStorageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void SaveBackStorage() {
		Main main = Main.getMain();
		try {
			main.backStorage.save(main.backStorageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void registerCommands() {
		getCommand("fly").setExecutor(new flight());
		getCommand("gamemode").setExecutor(new gamemode());
		getCommand("tppos").setExecutor(new tppos());
		getCommand("vanish").setExecutor(new vanish());
		getCommand("rtp").setExecutor(new rtp());
		getCommand("feed").setExecutor(new feed());
		getCommand("heal").setExecutor(new heal());
		getCommand("teleport").setExecutor(new teleport());
		getCommand("discord").setExecutor(new discord());
		getCommand("home").setExecutor(new home());
		getCommand("sethome").setExecutor(new sethome());
		getCommand("delhome").setExecutor(new delhome());
		getCommand("homes").setExecutor(new homes());
		getCommand("kickall").setExecutor(new kickall());
		getCommand("enderchest").setExecutor(new enderchest());
		getCommand("invsee").setExecutor(new invsee());
		getCommand("back").setExecutor(new back());
	}
	
	private void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new playerJoin(), this);
		pm.registerEvents(new playerDeath(), this);
		pm.registerEvents(new inventoryMoveItem(), this);
	}
	
	public void loadFromMysql() {
	    myChunkHolders = MysqlMethods.GetAllChunks();
	  }
	  
	  public ChunkHolder isPartOfChunkLoaderCollection(Chunk chunk) {
	    for (ChunkHolder chunkHolder : myChunkHolders) {
	      if (chunkHolder.isSameChunk(chunk))
	        return chunkHolder; 
	    } 
	    return null;
	  }
	
	  public void reloadChunkDatabase() {
		threadKeepChunksLoaded.run = false;
	    loadFromMysql();
	    this.threadKeepChunksLoaded = new ThreadKeepChunksLoaded();
	    System.out.println("Database reloaded!");
	  }
	
	public static Main getMain() {
		return me;
	}
	 	
}
