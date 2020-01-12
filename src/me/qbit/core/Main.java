package me.qbit.core;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import me.qbit.core.ChunkLoader.ChunkHolder;
import me.qbit.core.ChunkLoader.MysqlMethods;
import me.qbit.core.ChunkLoader.ThreadKeepChunksLoaded;
import me.qbit.core.commands.*;
import me.qbit.core.commands.home.*;
import me.qbit.core.commands.mute.*;
import me.qbit.core.commands.tpa.tpa;
import me.qbit.core.commands.tpa.tpaccept;
import me.qbit.core.commands.tpa.tpdeny;
import me.qbit.core.events.asyncPlayerChat;
import me.qbit.core.events.inventoryMoveItem;
import me.qbit.core.events.playerDeath;
import me.qbit.core.events.playerJoin;
import me.qbit.core.events.vehicleExit;
import me.qbit.core.utils.PlayerList;
import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class Main extends JavaPlugin {
	
	messenger m = new messenger();
	util u = new util();
	static List<Player> vanished_pl = new ArrayList<Player>();
	public ArrayList<ChunkHolder> myChunkHolders = new ArrayList<ChunkHolder>();
	public HashMap<Player, Player> tpa = new HashMap<>();
	public ArrayList<Player> tpaSent = new ArrayList<>();
	ThreadKeepChunksLoaded threadKeepChunksLoaded;
	static Main me;
	File mainConfigFile;
	File homesStorageFile;
	File backStorageFile;
	File delayStorageFile;
	File kitStorageFile;
	YamlConfiguration mainConfig;
	YamlConfiguration homesStorage;
	YamlConfiguration backStorage;
	YamlConfiguration delayStorage;
	YamlConfiguration kitStorage;
	ConfigurationSection muteStorage;
	ConfigurationSection kitDelayStorage;
	
	int timer = 600;
	
	@Override
	public void onEnable() {
		
		me = this;
		getDataFolder().mkdir();
		mainConfigFile = new File(getDataFolder(), "config.yml");
		homesStorageFile = new File(getDataFolder(), "homes.yml");
		backStorageFile = new File(getDataFolder(), "backs.yml");
		delayStorageFile = new File(getDataFolder(), "delays.yml");
		kitStorageFile = new File(getDataFolder(), "kits.yml");
		try {
			if(!mainConfigFile.exists())
				mainConfigFile.createNewFile();
			if(!homesStorageFile.exists())
				homesStorageFile.createNewFile();
			if(!backStorageFile.exists())
				backStorageFile.createNewFile();
			if(!delayStorageFile.exists())
				delayStorageFile.createNewFile();
			if(!kitStorageFile.exists())
				kitStorageFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mainConfig = YamlConfiguration.loadConfiguration(mainConfigFile);
		homesStorage = YamlConfiguration.loadConfiguration(homesStorageFile);
		backStorage = YamlConfiguration.loadConfiguration(backStorageFile);
		delayStorage = YamlConfiguration.loadConfiguration(delayStorageFile);
		kitStorage = YamlConfiguration.loadConfiguration(kitStorageFile);
		
		if(!delayStorage.contains("mute"))
			delayStorage.createSection("mute");
		if(!delayStorage.contains("kits"))
			delayStorage.createSection("kits");
		if(!mainConfig.contains("ServerName"))
			mainConfig.set("ServerName", "&eUnknown Server");
		
		muteStorage = delayStorage.getConfigurationSection("mute");
		kitDelayStorage = delayStorage.getConfigurationSection("kits");
		
		registerCommands();
		registerEvents();
		registerDefaultConfig();
		initSchedulers();

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
	
	public static ConfigurationSection GetMuteStorage() {
		return Main.getMain().muteStorage;
	}
	
	public static YamlConfiguration GetKitStorage() {
		return Main.getMain().kitStorage;
	}
	
	public static ConfigurationSection GetKitDelayStorage( ) {
		return Main.getMain().kitDelayStorage;
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
	
	public static void SaveDelayStorage() {
		Main main = Main.getMain();
		try {
			main.delayStorage.save(main.delayStorageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void SaveMuteStorage() {
		SaveDelayStorage();
	}
	
	public static void SaveKitStorage() {
		Main main = Main.getMain();
		try {
			main.kitStorage.save(main.kitStorageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void SaveKitDelayStorage() {
		SaveDelayStorage();
	}
	
	private void AutoRestart() {
		BukkitScheduler sch = getServer().getScheduler();
		sch.scheduleSyncRepeatingTask(this, new Runnable(){
			int timer = 601;
			final int[] times = new int[] {600,300,60,30,10};
			@Override
			public void run() {
				timer--;
				if(!Arrays.stream(times).anyMatch(i -> i == timer))
					return;
				int minutes = (int)Math.floor(timer/60), seconds = timer%60;
				m.broadcastNull();
				m.broadcast(String.format(" &4&lWARNING! &ethis server is scheduled to restart in %d minute%s %d second%s!", minutes, minutes>1 ? "s" : "", seconds, seconds>1 ? "s" : ""));
				m.broadcastNull();
				Bukkit.shutdown();
				sch.cancelAllTasks();
			}
		}, 0L, 20L);
		
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
		getCommand("mute").setExecutor(new mute());
		getCommand("unmute").setExecutor(new unmute());
		getCommand("tpa").setExecutor(new tpa());
		getCommand("tpaccept").setExecutor(new tpaccept());
		getCommand("tpdeny").setExecutor(new tpdeny());
		getCommand("kit").setExecutor(new kit());
	}
	
	private void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new playerJoin(), this);
		pm.registerEvents(new playerDeath(), this);
		pm.registerEvents(new inventoryMoveItem(), this);
		pm.registerEvents(new vehicleExit(), this);
		pm.registerEvents(new asyncPlayerChat(), this);
	}
	
	private void registerDefaultConfig() {
		if(GetMainConfig().getString("ServerName") == null) {
			GetMainConfig().set("ServerName", "&eUnknown server.");
			SaveMainConfig();
		}
	}
	
	private void initSchedulers() {
		BukkitScheduler scheduler = getServer().getScheduler();
		
		// Auto restarter
		
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
            	DateFormat dateformat = new SimpleDateFormat("HH:mm");
	            Date date = new Date();
	            String first = "8:00";
	            String second = "16:00";
	            String third = "24:00";
	            if (dateformat.format(date).equals(first) || dateformat.format(date).equals(second) || 
	              dateformat.format(date).equals(third))
	            	AutoRestart(); 
            }
        }, 0L, 1200L);
		
		// Advertiser
		
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
            	m.broadcastNull();
                m.broadcast("&eThank you for playing on &6&lF&e&lallen &6&lK&e&lingdom&e, make sure to join our discord server: &fhttps://discord.gg/zhrW4zC");
                m.broadcastNull();
            }
        }, 0L, 12000L);
        
        // mute bullshittery
        
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
        	@Override
        	public void run() {
        		long curtime = System.currentTimeMillis();
        		for(String uuid : muteStorage.getKeys(false)) {
        			long time = muteStorage.getLong(uuid);
        			if(time<=curtime && time!=-1) {
        				muteStorage.set(uuid, null);
        				SaveMuteStorage();
        				m.message(Bukkit.getPlayer(UUID.fromString(uuid)), "&fYour mute has expired");
        			}
        		}
        	}
        }, 0L, 20L);
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